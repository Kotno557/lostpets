package br.lostpets.project.model.valueobject;

import org.junit.Test;
import static org.junit.Assert.*;

public class PetStatusTest {

    @Test
    public void testPendingStatus() {
        assertEquals("P", PetStatus.PENDING.getCode());
        assertEquals("Pendente", PetStatus.PENDING.getDescription());
    }

    @Test
    public void testFoundStatus() {
        assertEquals("A", PetStatus.FOUND.getCode());
        assertEquals("Achado", PetStatus.FOUND.getDescription());
    }

    @Test
    public void testWaitingStatus() {
        assertEquals("W", PetStatus.WAITING.getCode());
        assertEquals("Aguardando", PetStatus.WAITING.getDescription());
    }

    @Test
    public void testFromCode() {
        assertEquals(PetStatus.PENDING, PetStatus.fromCode("P"));
        assertEquals(PetStatus.FOUND, PetStatus.fromCode("A"));
        assertEquals(PetStatus.WAITING, PetStatus.fromCode("W"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromCodeInvalid() {
        PetStatus.fromCode("X");
    }
}
