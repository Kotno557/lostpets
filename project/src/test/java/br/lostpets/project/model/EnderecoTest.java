package br.lostpets.project.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Endereco value object
 */
public class EnderecoTest {

    @Test
    public void testEnderecoCreation() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setBairro("Centro");
        endereco.setLocalidade("São Paulo");
        endereco.setUf("SP");
        endereco.setLatitude(-23.5505);
        endereco.setLongitude(-46.6333);

        assertEquals("12345-678", endereco.getCep());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("São Paulo", endereco.getLocalidade());
        assertEquals("SP", endereco.getUf());
        assertEquals(-23.5505, endereco.getLatitude(), 0.0001);
        assertEquals(-46.6333, endereco.getLongitude(), 0.0001);
    }

    @Test
    public void testEnderecoWithAllFields() {
        Endereco endereco = new Endereco();
        endereco.setCep("01310-100");
        endereco.setLogradouro("Avenida Paulista");
        endereco.setComplemento("Apto 101");
        endereco.setBairro("Bela Vista");
        endereco.setLocalidade("São Paulo");
        endereco.setUf("SP");
        endereco.setLatitude(-23.5613);
        endereco.setLongitude(-46.6563);

        assertNotNull(endereco.getCep());
        assertNotNull(endereco.getLogradouro());
        assertNotNull(endereco.getComplemento());
        assertNotNull(endereco.getBairro());
        assertNotNull(endereco.getLocalidade());
        assertNotNull(endereco.getUf());
    }
}
