package br.lostpets.project.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Usuario.Builder
 * Tests the Builder Pattern implementation to address the Long Parameter List code smell.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioBuilderTest {
	
	@Test
	public void testBuilderWithAllFields() {
		// Arrange & Act
		Usuario usuario = Usuario.builder()
			.nome("João Silva")
			.email("joao@email.com")
			.senha("senha123")
			.telefoneFixo("1133334444")
			.telefoneCelular("11987654321")
			.idImagem("img123")
			.cep("01310-100")
			.rua("Av. Paulista")
			.bairro("Bela Vista")
			.cidade("São Paulo")
			.uf("SP")
			.latitude(-23.5505)
			.longitude(-46.6333)
			.build();
		
		// Assert
		assertNotNull(usuario);
		assertEquals("João Silva", usuario.getNome());
		assertEquals("joao@email.com", usuario.getEmail());
		assertEquals("senha123", usuario.getSenha());
		assertEquals("1133334444", usuario.getTelefoneFixo());
		assertEquals("11987654321", usuario.getTelefoneCelular());
		assertEquals("img123", usuario.getIdImagem());
		assertEquals("01310-100", usuario.getCep());
		assertEquals("Av. Paulista", usuario.getRua());
		assertEquals("Bela Vista", usuario.getBairro());
		assertEquals("São Paulo", usuario.getCidade());
		assertEquals("SP", usuario.getUf());
		assertEquals(-23.5505, usuario.getLatitude(), 0.0001);
		assertEquals(-46.6333, usuario.getLongitude(), 0.0001);
		assertNotNull(usuario.getAddCadastro());
	}
	
	@Test
	public void testBuilderWithMinimalFields() {
		// Arrange & Act
		Usuario usuario = Usuario.builder()
			.nome("Maria Santos")
			.email("maria@email.com")
			.build();
		
		// Assert
		assertNotNull(usuario);
		assertEquals("Maria Santos", usuario.getNome());
		assertEquals("maria@email.com", usuario.getEmail());
		assertNull(usuario.getSenha());
		assertNull(usuario.getTelefoneFixo());
		assertNull(usuario.getTelefoneCelular());
		assertNotNull(usuario.getAddCadastro());
	}
	
	@Test
	public void testBuilderWithAddressObject() {
		// Arrange
		Address address = new Address();
		address.setCep("01310-100");
		address.setLogradouro("Av. Paulista");
		address.setBairro("Bela Vista");
		address.setLocalidade("São Paulo");
		address.setUf("SP");
		address.setLatitude(-23.5505);
		address.setLongitude(-46.6333);
		
		// Act
		Usuario usuario = Usuario.builder()
			.nome("Carlos Pereira")
			.email("carlos@email.com")
			.address(address)
			.build();
		
		// Assert
		assertNotNull(usuario);
		assertEquals("Carlos Pereira", usuario.getNome());
		assertEquals("carlos@email.com", usuario.getEmail());
		assertEquals("01310-100", usuario.getCep());
		assertEquals("Av. Paulista", usuario.getRua());
		assertEquals("Bela Vista", usuario.getBairro());
		assertEquals("São Paulo", usuario.getCidade());
		assertEquals("SP", usuario.getUf());
		assertEquals(-23.5505, usuario.getLatitude(), 0.0001);
		assertEquals(-46.6333, usuario.getLongitude(), 0.0001);
	}
	
	@Test
	public void testBuilderWithNullAddress() {
		// Arrange & Act
		Usuario usuario = Usuario.builder()
			.nome("Ana Costa")
			.email("ana@email.com")
			.address(null)
			.build();
		
		// Assert
		assertNotNull(usuario);
		assertEquals("Ana Costa", usuario.getNome());
		assertNull(usuario.getCep());
		assertNull(usuario.getRua());
	}
	
	@Test
	public void testBuilderMethodChaining() {
		// Arrange & Act
		Usuario.Builder builder = Usuario.builder();
		Usuario.Builder resultBuilder = builder.nome("Test");
		
		// Assert - Verify method chaining returns same builder
		assertSame(builder, resultBuilder);
	}
	
	@Test
	public void testBuilderComparison_BuilderVsConstructor() {
		// Arrange
		String nome = "Pedro Alves";
		String telefoneFixo = "1144445555";
		String telefoneCelular = "11999998888";
		String email = "pedro@email.com";
		String senha = "senha456";
		String idImagem = "img456";
		String cep = "04567-890";
		String rua = "Rua Teste";
		String bairro = "Bairro Teste";
		String cidade = "São Paulo";
		String uf = "SP";
		double latitude = -23.5;
		double longitude = -46.6;
		
		// Act - Create using builder
		Usuario usuarioBuilder = Usuario.builder()
			.nome(nome)
			.telefoneFixo(telefoneFixo)
			.telefoneCelular(telefoneCelular)
			.email(email)
			.senha(senha)
			.idImagem(idImagem)
			.cep(cep)
			.rua(rua)
			.bairro(bairro)
			.cidade(cidade)
			.uf(uf)
			.latitude(latitude)
			.longitude(longitude)
			.build();
		
		// Act - Create using constructor
		Usuario usuarioConstructor = new Usuario(nome, telefoneFixo, telefoneCelular, email, senha,
			idImagem, cep, rua, bairro, cidade, uf, latitude, longitude);
		
		// Assert - Both should have same values
		assertEquals(usuarioConstructor.getNome(), usuarioBuilder.getNome());
		assertEquals(usuarioConstructor.getEmail(), usuarioBuilder.getEmail());
		assertEquals(usuarioConstructor.getCep(), usuarioBuilder.getCep());
		assertEquals(usuarioConstructor.getLatitude(), usuarioBuilder.getLatitude(), 0.0001);
	}
	
	@Test
	public void testGetAddressAfterBuilderWithAddress() {
		// Arrange
		Address originalAddress = new Address();
		originalAddress.setCep("01310-100");
		originalAddress.setLogradouro("Av. Paulista");
		originalAddress.setBairro("Bela Vista");
		originalAddress.setLocalidade("São Paulo");
		originalAddress.setUf("SP");
		originalAddress.setLatitude(-23.5505);
		originalAddress.setLongitude(-46.6333);
		
		// Act
		Usuario usuario = Usuario.builder()
			.nome("Test User")
			.email("test@email.com")
			.address(originalAddress)
			.build();
		
		Address retrievedAddress = usuario.getAddress();
		
		// Assert
		assertNotNull(retrievedAddress);
		assertEquals(originalAddress.getCep(), retrievedAddress.getCep());
		assertEquals(originalAddress.getLogradouro(), retrievedAddress.getLogradouro());
		assertEquals(originalAddress.getBairro(), retrievedAddress.getBairro());
		assertEquals(originalAddress.getLocalidade(), retrievedAddress.getLocalidade());
		assertEquals(originalAddress.getUf(), retrievedAddress.getUf());
		assertEquals(originalAddress.getLatitude(), retrievedAddress.getLatitude(), 0.0001);
		assertEquals(originalAddress.getLongitude(), retrievedAddress.getLongitude(), 0.0001);
	}
}
