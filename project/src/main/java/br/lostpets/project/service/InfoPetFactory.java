package br.lostpets.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.lostpets.project.model.InfoPet;
import br.lostpets.project.model.LostPet;

/**
 * Factory for creating InfoPet DTOs.
 * Addresses the Feature Envy code smell by centralizing InfoPet creation logic.
 * 
 * Design Pattern: Factory Pattern - Encapsulates object creation logic
 */
public class InfoPetFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(InfoPetFactory.class);

    /**
     * Creates an InfoPet DTO from a LostPet entity.
     * This method encapsulates the complex logic of extracting data from
     * LostPet and its associated Usuario.
     * 
     * @param pet The LostPet entity
     * @return InfoPet DTO, or null if pet is null
     */
    public static InfoPet createFromPetPerdido(LostPet pet) {
        if (pet == null) {
            logger.warn("Attempted to create InfoPet from null PetPerdido");
            return null;
        }

        try {
            return new InfoPet(pet);
        } catch (Exception e) {
            logger.error("Error creating InfoPet from LostPet (ID: {})", pet.getIdAnimal(), e);
            return null;
        }
    }

    /**
     * Creates an InfoPet with additional validation and logging.
     * 
     * @param pet The LostPet entity
     * @return InfoPet DTO with validated data
     */
    public static InfoPet createValidatedInfoPet(LostPet pet) {
        if (pet == null) {
            logger.warn("Validation failed: LostPet is null");
            return null;
        }

        if (pet.getUsuario() == null) {
            logger.warn("Validation failed: User is null for LostPet ID: {}", pet.getIdAnimal());
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
