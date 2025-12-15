package br.lostpets.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.Address;
import br.lostpets.project.model.valueobject.PostalCode;

/**
 * Service for handling address-related operations.
 * Centralizes address lookup and coordinate retrieval logic.
 * 
 * Design Pattern: Facade Pattern - Simplifies interaction with ViaCep service
 */
@Service
public class AddressService {
    
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final ViaCep viaCep;

    public AddressService() {
        this.viaCep = new ViaCep();
    }

    /**
     * Retrieves geographic coordinates (latitude/longitude) for a given CEP.
     * 
     * @param cep Postal code (CEP) as string
     * @return Address object with coordinates, or null if lookup fails
     */
    public Address getCoordinatesFromCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            logger.warn("Attempted to get coordinates for null or empty CEP");
            return null;
        }

        try {
            Address address = viaCep.getLatitudeLongitude(cep);
            logger.info("Successfully retrieved coordinates for CEP: {}", cep);
            return address;
        } catch (Exception e) {
            logger.error("Error retrieving coordinates for CEP: {}", cep, e);
            return null;
        }
    }

    /**
     * Retrieves geographic coordinates using PostalCode value object.
     * 
     * @param postalCode PostalCode value object
     * @return Address object with coordinates, or null if lookup fails
     */
    public Address getCoordinatesFromPostalCode(PostalCode postalCode) {
        if (postalCode == null) {
            logger.warn("Attempted to get coordinates for null PostalCode");
            return null;
        }
        return getCoordinatesFromCep(postalCode.getValue());
    }

    /**
     * Updates an Address object with coordinates based on its CEP.
     * 
     * @param address Address object with CEP set
     * @return true if coordinates were successfully updated, false otherwise
     */
    public boolean updateCoordinates(Address address) {
        if (address == null || address.getCep() == null) {
            logger.warn("Cannot update coordinates for null address or null CEP");
            return false;
        }

        Address coordinates = getCoordinatesFromCep(address.getCep());
        if (coordinates != null) {
            address.setLatitude(coordinates.getLatitude());
            address.setLongitude(coordinates.getLongitude());
            return true;
        }
        return false;
    }

    /**
     * Creates an Address object with coordinates from a CEP string.
     * 
     * @param cep Brazilian postal code
     * @return Address with coordinates, or empty Address if lookup fails
     */
    public Address createAddressWithCoordinates(String cep) {
        Address address = getCoordinatesFromCep(cep);
        if (address == null) {
            address = new Address();
            address.setCep(cep);
            logger.warn("Created empty Address for CEP: {} (coordinate lookup failed)", cep);
        } else {
            address.setCep(cep);
        }
        return address;
    }
}
