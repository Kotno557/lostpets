package br.lostpets.project.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.lostpets.project.model.Address;

/**
 * Service for retrieving geographic coordinates from Brazilian postal codes (CEP).
 * 
 * This service integrates with the HERE Geocoding API to convert CEP addresses
 * into latitude/longitude coordinates.
 * 
 * External API: HERE Geocoding API
 * - Base URL: https://geocoder.api.here.com/6.2/geocode.json
 * - Authentication: app_id and app_code (embedded in URL)
 * - Response Format: JSON
 * 
 * Example Response Structure:
 * {
 *   "Response": {
 *     "View": [{
 *       "Result": [{
 *         "Location": {
 *           "DisplayPosition": {
 *             "Latitude": -23.5505,
 *             "Longitude": -46.6333
 *           }
 *         }
 *       }]
 *     }]
 *   }
 * }
 * 
 * Note: The API credentials (app_id and app_code) should ideally be moved
 * to application.properties for better security and configurability.
 */
public class ViaCep {

	private static final Logger logger = LoggerFactory.getLogger(ViaCep.class);
	
	// TODO: Move these to application.properties
	private static final String APP_ID = "YxULymX19IjsS2pE7KGo";
	private static final String APP_CODE = "6isWeBIxu4YmK1hfYF6s1w";
	
	private Address address;
	private Client client;
 	private WebTarget webTarget;
// 	private final static String URL = "http://viacep.com.br/ws/";
// 	private final static String FIMURL = "/json/";

 	public ViaCep() {
 		this.client = ClientBuilder.newClient();
 		logger.info("ViaCep service initialized");
 	}
 	
 	/**
 	 * Retrieves latitude and longitude coordinates for a given Brazilian CEP.
 	 * 
 	 * @param cep The postal code (can include hyphen: XXXXX-XXX or without: XXXXXXXX)
 	 * @return Address object containing latitude and longitude, or null if lookup fails
 	 */
 	public Address getLatitudeLongitude(String cep) {
 		if (cep == null || cep.trim().isEmpty()) {
 			logger.warn("Attempted to lookup coordinates for null or empty CEP");
 			return null;
 		}
 		
 		try {
 			// Normalize CEP: remove hyphen
 			String[] cepV = cep.split("-");
 			String normalizedCep = cepV[0].concat(cepV.length > 1 ? cepV[1] : "");
 		
 			// Build API URL with search parameters
 			String URL = String.format(
 				"https://geocoder.api.here.com/6.2/geocode.json?searchtext=%s&app_id=%s&app_code=%s&gen=9",
 				normalizedCep, APP_ID, APP_CODE
 			);
 		
 			this.webTarget = this.client.target(URL).path(""); 
			Invocation.Builder invocationBuilder = this.webTarget.request("application/json;charset=UTF-8"); 
			Response response = invocationBuilder.get();
		
			if (response.getStatus() != 200) {
				logger.error("HERE API returned status {}: for CEP {}", response.getStatus(), cep);
				return null;
			}
		
			String responseBody = response.readEntity(String.class);
			JSONObject data = new JSONObject(responseBody);
			
			// Navigate JSON structure to extract coordinates
			JSONObject displayPosition = data
				.getJSONObject("Response")
				.getJSONArray("View")
				.getJSONObject(0)
				.getJSONArray("Result")
				.getJSONObject(0)
				.getJSONObject("Location")
				.getJSONObject("DisplayPosition");
		
			address = new Address();
			address.setLatitude(displayPosition.getDouble("Latitude"));
			address.setLongitude(displayPosition.getDouble("Longitude"));
			
			logger.info("Successfully retrieved coordinates for CEP {}: ({}, {})", 
				cep, address.getLatitude(), address.getLongitude());
		
			return address;
			
		} catch (Exception e) {
			logger.error("Error retrieving coordinates for CEP: {}", cep, e);
			return null;
		}
 	}
 	
}
