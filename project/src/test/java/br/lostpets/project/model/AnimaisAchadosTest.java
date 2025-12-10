package br.lostpets.project.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

/**
 * Unit tests for AnimaisAchados entity
 */
public class AnimaisAchadosTest {

    private Usuario usuario;
    private PetPerdido petPerdido;
    private AnimaisAchados animaisAchados;

    @Before
    public void setUp() {
        usuario = new Usuario("Carlos", "carlos@email.com", "11988887777", null);
        petPerdido = new PetPerdido(
            usuario,
            "Totó",
            "2023-12-01",
            "Cachorro marrom",
            "Cachorro",
            "path",
            "12345-678",
            -23.5505,
            -46.6333
        );
    }

    @Test
    public void testAnimaisAchadosCreation() {
        Date dataEncontrado = new Date();
        animaisAchados = new AnimaisAchados(
            usuario,
            petPerdido,
            dataEncontrado,
            10,
            "-23.5505",
            "-46.6333"
        );

        assertEquals(usuario, animaisAchados.getUsuarioAchou());
        assertEquals(petPerdido, animaisAchados.getPetPerdido());
        assertEquals(dataEncontrado, animaisAchados.getDataEncontrado());
        assertEquals(10, animaisAchados.getPontos());
        assertEquals("-23.5505", animaisAchados.getLatitude());
        assertEquals("-46.6333", animaisAchados.getLongitude());
        assertEquals("A", animaisAchados.getStatus());
    }

    @Test
    public void testAnimaisAchadosDefaultStatus() {
        animaisAchados = new AnimaisAchados(
            usuario,
            petPerdido,
            new Date(),
            5,
            "-23.5505",
            "-46.6333"
        );

        assertEquals("A", animaisAchados.getStatus());
    }

    @Test
    public void testAnimaisAchadosSetters() {
        animaisAchados = new AnimaisAchados();
        
        animaisAchados.setUsuarioAchou(usuario);
        animaisAchados.setPetPerdido(petPerdido);
        animaisAchados.setPontos(15);
        animaisAchados.setStatus("W");
        animaisAchados.setLatitude("-22.9068");
        animaisAchados.setLongitude("-43.1729");

        assertEquals(usuario, animaisAchados.getUsuarioAchou());
        assertEquals(petPerdido, animaisAchados.getPetPerdido());
        assertEquals(15, animaisAchados.getPontos());
        assertEquals("W", animaisAchados.getStatus());
        assertEquals("-22.9068", animaisAchados.getLatitude());
        assertEquals("-43.1729", animaisAchados.getLongitude());
    }

    @Test
    public void testToString() {
        animaisAchados = new AnimaisAchados(
            usuario,
            petPerdido,
            new Date(),
            10,
            "-23.5505",
            "-46.6333"
        );

        String toString = animaisAchados.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("AnimaisAchados"));
    }
}
