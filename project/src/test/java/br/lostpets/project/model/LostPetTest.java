package br.lostpets.project.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for LostPet entity
 */
public class LostPetTest {

    private User usuario;
    private LostPet petPerdido;

    @Before
    public void setUp() {
        usuario = new User("João Silva", "joao@email.com", "11987654321", "1133334444");
    }

    @Test
    public void testPetPerdidoCreation() {
        petPerdido = new LostPet(
            usuario,
            "Rex",
            "2023-12-01",
            "Perdido perto do parque",
            "Cachorro",
            "http://image.url",
            "12345-678",
            -23.5505,
            -46.6333
        );

        assertEquals("Rex", petPerdido.getNomeAnimal());
        assertEquals("2023-12-01", petPerdido.getDataPerdido());
        assertEquals("P", petPerdido.getStatus());
        assertEquals("Perdido perto do parque", petPerdido.getDescricao());
        assertEquals("Cachorro", petPerdido.getTipoAnimal());
        assertEquals("http://image.url", petPerdido.getPathImg());
        assertEquals("12345-678", petPerdido.getCep());
        assertEquals(-23.5505, petPerdido.getLatitude(), 0.0001);
        assertEquals(-46.6333, petPerdido.getLongitude(), 0.0001);
        assertEquals(usuario, petPerdido.getUsuario());
        assertNotNull(petPerdido.getAddData());
    }

    @Test
    public void testPetPerdidoDefaultStatus() {
        petPerdido = new LostPet(
            usuario,
            "Miau",
            "2023-12-05",
            "Gato perdido",
            "Gato",
            null,
            "12345-678",
            -23.5505,
            -46.6333
        );

        assertEquals("P", petPerdido.getStatus());
    }

    @Test
    public void testPetPerdidoSettersAndGetters() {
        petPerdido = new LostPet();
        
        petPerdido.setNomeAnimal("Bolinha");
        petPerdido.setDescricao("Cachorro pequeno");
        petPerdido.setTipoAnimal("Cachorro");
        petPerdido.setStatus("A");
        petPerdido.setCep("54321-876");
        petPerdido.setLatitude(-22.9068);
        petPerdido.setLongitude(-43.1729);

        assertEquals("Bolinha", petPerdido.getNomeAnimal());
        assertEquals("Cachorro pequeno", petPerdido.getDescricao());
        assertEquals("Cachorro", petPerdido.getTipoAnimal());
        assertEquals("A", petPerdido.getStatus());
        assertEquals("54321-876", petPerdido.getCep());
        assertEquals(-22.9068, petPerdido.getLatitude(), 0.0001);
        assertEquals(-43.1729, petPerdido.getLongitude(), 0.0001);
    }

    @Test
    public void testCopyConstructor() {
        LostPet original = new LostPet(
            usuario,
            "Max",
            "2023-12-10",
            "Descrição",
            "Cachorro",
            "path",
            "12345-678",
            -23.5505,
            -46.6333
        );

        User newUsuario = new User("Maria", "maria@email.com", "11999999999", null);
        LostPet copy = new LostPet(newUsuario, original);

        assertEquals(original.getNomeAnimal(), copy.getNomeAnimal());
        assertEquals(original.getDataPerdido(), copy.getDataPerdido());
        assertEquals(original.getCep(), copy.getCep());
        assertEquals("P", copy.getStatus());
        assertEquals(newUsuario, copy.getUsuario());
    }
}
