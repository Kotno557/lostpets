package br.lostpets.project.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for InfoPet DTO
 */
public class InfoPetTest {

    @Test
    public void testInfoPetFromPetPerdido() {
        User usuario = new User("Ana", "ana@email.com", "11977776666", null);
        LostPet pet = new LostPet(
            usuario,
            "Luna",
            "2023-12-15",
            "Gata perdida na rua",
            "Gato",
            "http://image.url",
            "12345-678",
            -23.5505,
            -46.6333
        );
        pet.setDescricaoAnimal("Gata branca com manchas");

        InfoPet infoPet = new InfoPet(pet);

        assertEquals(pet.getIdAnimal(), infoPet.getAnimalID());
        assertEquals("Luna", infoPet.getAnimalName());
        assertEquals("Gata branca com manchas", infoPet.getAnimalInfos());
        assertEquals("Gato", infoPet.getAnimalType());
        assertEquals("http://image.url", infoPet.getAnimalImgPath());
        assertEquals("ana@email.com", infoPet.getOwnerEmail());
        assertEquals("Ana", infoPet.getOwnerName());
        assertEquals("11977776666", infoPet.getOwnerNumber());
        assertEquals("Gata perdida na rua", infoPet.getHowWasLost());
        assertEquals("12345-678", infoPet.getCepLost());
        assertNotNull(infoPet.getLostDate());
    }

    @Test
    public void testInfoPetEmptyConstructor() {
        InfoPet infoPet = new InfoPet();
        assertNotNull(infoPet);
    }

    @Test
    public void testInfoPetSettersAndGetters() {
        InfoPet infoPet = new InfoPet();
        
        infoPet.setAnimalID(123);
        infoPet.setAnimalName("Spike");
        infoPet.setAnimalType("Cachorro");
        infoPet.setOwnerName("Pedro");
        infoPet.setOwnerEmail("pedro@email.com");
        infoPet.setOwnerNumber("11966665555");

        assertEquals(123, infoPet.getAnimalID());
        assertEquals("Spike", infoPet.getAnimalName());
        assertEquals("Cachorro", infoPet.getAnimalType());
        assertEquals("Pedro", infoPet.getOwnerName());
        assertEquals("pedro@email.com", infoPet.getOwnerEmail());
        assertEquals("11966665555", infoPet.getOwnerNumber());
    }
}
