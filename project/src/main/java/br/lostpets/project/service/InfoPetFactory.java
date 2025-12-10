package br.lostpets.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.lostpets.project.model.InfoPet;
import br.lostpets.project.model.PetPerdido;

/**
 * Factory for creating InfoPet DTOs.
 * Addresses the Feature Envy code smell by centralizing InfoPet creation logic.
 * 
 * Design Pattern: Factory Pattern - Encapsulates object creation logic
 */
public class InfoPetFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(InfoPetFactory.class);

    /**
     * Creates an InfoPet DTO from a PetPerdido entity.
     * This method encapsulates the complex logic of extracting data from
     * PetPerdido and its associated Usuario.
     * 
     * @param pet The PetPerdido entity
     * @return InfoPet DTO, or null if pet is null
     */
    public static InfoPet createFromPetPerdido(PetPerdido pet) {
        if (pet == null) {
            logger.warn("Attempted to create InfoPet from null PetPerdido");
            return null;
        }

        try {
            return new InfoPet(pet);
        } catch (Exception e) {
            logger.error("Error creating InfoPet from PetPerdido (ID: {})", pet.getIdAnimal(), e);
            return null;
        }
    }

    /**
     * Creates an InfoPet with additional validation and logging.
     * 
     * @param pet The PetPerdido entity
     * @return InfoPet DTO with validated data
     */
    public static InfoPet createValidatedInfoPet(PetPerdido pet) {
        if (pet == null) {
            logger.warn("Validation failed: PetPerdido is null");
            return null;
        }

        if (pet.getUsuario() == null) {
            logger.warn("Validation failed: Usuario is null for PetPerdido ID: {}", pet.getIdAnimal());
            return null;
        }

        InfoPet infoPet = createFromPetPerdido(pet);
        
        if (infoPet != null) {
            logger.info("Successfully created InfoPet for pet: {} (ID: {})", 
                pet.getNomeAnimal(), pet.getIdAnimal());
        }

        return infoPet;
    }
}
