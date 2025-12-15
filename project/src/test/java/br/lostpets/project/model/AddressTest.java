package br.lostpets.project.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Address value object
 */
public class AddressTest {

    @Test
    public void testAddressCreation() {
        Address address = new Address();
        address.setCep("12345-678");
        address.setBairro("Centro");
        address.setLocalidade("São Paulo");
        address.setUf("SP");
        address.setLatitude(-23.5505);
        address.setLongitude(-46.6333);

        assertEquals("12345-678", address.getCep());
        assertEquals("Centro", address.getBairro());
        assertEquals("São Paulo", address.getLocalidade());
        assertEquals("SP", address.getUf());
        assertEquals(-23.5505, address.getLatitude(), 0.0001);
        assertEquals(-46.6333, address.getLongitude(), 0.0001);
    }

    @Test
    public void testAddressWithAllFields() {
        Address address = new Address();
        address.setCep("01310-100");
        address.setLogradouro("Avenida Paulista");
        address.setComplemento("Apto 101");
        address.setBairro("Bela Vista");
        address.setLocalidade("São Paulo");
        address.setUf("SP");
        address.setLatitude(-23.5613);
        address.setLongitude(-46.6563);

        assertNotNull(address.getCep());
        assertNotNull(address.getLogradouro());
        assertNotNull(address.getComplemento());
        assertNotNull(address.getBairro());
        assertNotNull(address.getLocalidade());
        assertNotNull(address.getUf());
    }
}
