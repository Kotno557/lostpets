package br.lostpets.project.model.valueobject;

import org.junit.Test;
import static org.junit.Assert.*;

public class PostalCodeTest {

    @Test
    public void testValidCepWithHyphen() {
        PostalCode cep = new PostalCode("12345-678");
        assertEquals("12345-678", cep.getValue());
        assertEquals("12345678", cep.getValueWithoutHyphen());
    }

    @Test
    public void testValidCepWithoutHyphen() {
        PostalCode cep = new PostalCode("12345678");
        assertEquals("12345-678", cep.getValue());
        assertEquals("12345678", cep.getValueWithoutHyphen());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCepFormat() {
        new PostalCode("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCep() {
        new PostalCode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyCep() {
        new PostalCode("");
    }

    @Test
    public void testCepEquality() {
        PostalCode cep1 = new PostalCode("12345-678");
        PostalCode cep2 = new PostalCode("12345678");
        assertEquals(cep1, cep2);
    }

    @Test
    public void testCepToString() {
        PostalCode cep = new PostalCode("12345-678");
        assertEquals("12345-678", cep.toString());
    }
}
