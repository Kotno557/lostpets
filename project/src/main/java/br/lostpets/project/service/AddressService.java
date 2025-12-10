package br.lostpets.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.lostpets.project.model.Endereco;
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
     * @return Endereco object with coordinates, or null if lookup fails
     */
    public Endereco getCoordinatesFromCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            logger.warn("Attempted to get coordinates for null or empty CEP");
            return null;
        }

        try {
            Endereco endereco = viaCep.getLatitudeLongitude(cep);
            logger.info("Successfully retrieved coordinates for CEP: {}", cep);
            return endereco;
        } catch (Exception e) {
            logger.error("Error retrieving coordinates for CEP: {}", cep, e);
            return null;
        }
    }

    /**
     * Retrieves geographic coordinates using PostalCode value object.
     * 
     * @param postalCode PostalCode value object
     * @return Endereco object with coordinates, or null if lookup fails
     */
    public Endereco getCoordinatesFromPostalCode(PostalCode postalCode) {
        if (postalCode == null) {
            logger.warn("Attempted to get coordinates for null PostalCode");
            return null;
        }
        return getCoordinatesFromCep(postalCode.getValue());
    }

    /**
     * Updates an Endereco object with coordinates based on its CEP.
     * 
     * @param endereco Endereco object with CEP set
     * @return true if coordinates were successfully updated, false otherwise
     */
    public boolean updateCoordinates(Endereco endereco) {
        if (endereco == null || endereco.getCep() == null) {
            logger.warn("Cannot update coordinates for null endereco or null CEP");
            return false;
        }

        Endereco coordinates = getCoordinatesFromCep(endereco.getCep());
        if (coordinates != null) {
            endereco.setLatitude(coordinates.getLatitude());
            endereco.setLongitude(coordinates.getLongitude());
            return true;
        }
        return false;
    }

    /**
     * Creates an Endereco object with coordinates from a CEP string.
     * 
     * @param cep Postal code string
     * @return Endereco with coordinates, or empty Endereco if lookup fails
     */
    public Endereco createEnderecoWithCoordinates(String cep) {
        Endereco endereco = getCoordinatesFromCep(cep);
        if (endereco == null) {
            endereco = new Endereco();
            endereco.setCep(cep);
            logger.warn("Created empty Endereco for CEP: {} (coordinate lookup failed)", cep);
        } else {
            endereco.setCep(cep);
        }
        return endereco;
    }
}
