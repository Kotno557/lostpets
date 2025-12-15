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
	public void testBuilderWithEnderecoObject() {
		// Arrange
		Endereco endereco = new Endereco();
		endereco.setCep("01310-100");
		endereco.setLogradouro("Av. Paulista");
		endereco.setBairro("Bela Vista");
		endereco.setLocalidade("São Paulo");
		endereco.setUf("SP");
		endereco.setLatitude(-23.5505);
		endereco.setLongitude(-46.6333);
		
		// Act
		Usuario usuario = Usuario.builder()
			.nome("Carlos Pereira")
			.email("carlos@email.com")
			.endereco(endereco)
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
	public void testBuilderWithNullEndereco() {
		// Arrange & Act
		Usuario usuario = Usuario.builder()
			.nome("Ana Costa")
			.email("ana@email.com")
			.endereco(null)
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
	public void testGetEnderecoAfterBuilderWithEndereco() {
		// Arrange
		Endereco originalEndereco = new Endereco();
		originalEndereco.setCep("01310-100");
		originalEndereco.setLogradouro("Av. Paulista");
		originalEndereco.setBairro("Bela Vista");
		originalEndereco.setLocalidade("São Paulo");
		originalEndereco.setUf("SP");
		originalEndereco.setLatitude(-23.5505);
		originalEndereco.setLongitude(-46.6333);
		
		// Act
		Usuario usuario = Usuario.builder()
			.nome("Test User")
			.email("test@email.com")
			.endereco(originalEndereco)
			.build();
		
		Endereco retrievedEndereco = usuario.getEndereco();
		
		// Assert
		assertNotNull(retrievedEndereco);
		assertEquals(originalEndereco.getCep(), retrievedEndereco.getCep());
		assertEquals(originalEndereco.getLogradouro(), retrievedEndereco.getLogradouro());
		assertEquals(originalEndereco.getBairro(), retrievedEndereco.getBairro());
		assertEquals(originalEndereco.getLocalidade(), retrievedEndereco.getLocalidade());
		assertEquals(originalEndereco.getUf(), retrievedEndereco.getUf());
		assertEquals(originalEndereco.getLatitude(), retrievedEndereco.getLatitude(), 0.0001);
		assertEquals(originalEndereco.getLongitude(), retrievedEndereco.getLongitude(), 0.0001);
	}
}
