package br.lostpets.project.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for PetPerdido.Builder
 * Tests the Builder Pattern implementation to address the Long Parameter List code smell.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PetPerdidoBuilderTest {
	
	private Usuario testUsuario;
	
	@Before
	public void setUp() {
		testUsuario = Usuario.builder()
			.nome("Test Owner")
			.email("owner@email.com")
			.telefoneCelular("11999999999")
			.build();
	}
	
	@Test
	public void testBuilderWithAllFields() {
		// Arrange & Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Rex")
			.dataPerdido("2024-01-15")
			.status("P")
			.descricao("Cachorro perdido perto do parque")
			.descricaoAnimal("Golden Retriever - Golden - Grande")
			.tipoAnimal("Cachorro")
			.pathImg("https://example.com/img.jpg")
			.cep("01310-100")
			.rua("Av. Paulista")
			.bairro("Bela Vista")
			.cidade("São Paulo")
			.uf("SP")
			.latitude(-23.5505)
			.longitude(-46.6333)
			.build();
		
		// Assert
		assertNotNull(pet);
		assertEquals(testUsuario, pet.getUsuario());
		assertEquals("Rex", pet.getNomeAnimal());
		assertEquals("2024-01-15", pet.getDataPerdido());
		assertEquals("P", pet.getStatus());
		assertEquals("Cachorro perdido perto do parque", pet.getDescricao());
		assertEquals("Golden Retriever - Golden - Grande", pet.getDescricaoAnimal());
		assertEquals("Cachorro", pet.getTipoAnimal());
		assertEquals("https://example.com/img.jpg", pet.getPathImg());
		assertEquals("01310-100", pet.getCep());
		assertEquals("Av. Paulista", pet.getRua());
		assertEquals("Bela Vista", pet.getBairro());
		assertEquals("São Paulo", pet.getCidade());
		assertEquals("SP", pet.getUf());
		assertEquals(-23.5505, pet.getLatitude(), 0.0001);
		assertEquals(-46.6333, pet.getLongitude(), 0.0001);
		assertNotNull(pet.getAddData());
	}
	
	@Test
	public void testBuilderWithMinimalFields() {
		// Arrange & Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Mimi")
			.tipoAnimal("Gato")
			.build();
		
		// Assert
		assertNotNull(pet);
		assertEquals(testUsuario, pet.getUsuario());
		assertEquals("Mimi", pet.getNomeAnimal());
		assertEquals("Gato", pet.getTipoAnimal());
		assertEquals("P", pet.getStatus());  // Default status
		assertNull(pet.getDataPerdido());
		assertNull(pet.getDescricao());
		assertNotNull(pet.getAddData());
	}
	
	@Test
	public void testBuilderWithEnderecoObject() {
		// Arrange
		Endereco endereco = new Endereco();
		endereco.setCep("04567-890");
		endereco.setLogradouro("Rua das Flores");
		endereco.setBairro("Jardim Paulista");
		endereco.setLocalidade("São Paulo");
		endereco.setUf("SP");
		endereco.setLatitude(-23.5600);
		endereco.setLongitude(-46.6500);
		
		// Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Bobby")
			.tipoAnimal("Cachorro")
			.endereco(endereco)
			.build();
		
		// Assert
		assertNotNull(pet);
		assertEquals("Bobby", pet.getNomeAnimal());
		assertEquals("04567-890", pet.getCep());
		assertEquals("Rua das Flores", pet.getRua());
		assertEquals("Jardim Paulista", pet.getBairro());
		assertEquals("São Paulo", pet.getCidade());
		assertEquals("SP", pet.getUf());
		assertEquals(-23.5600, pet.getLatitude(), 0.0001);
		assertEquals(-46.6500, pet.getLongitude(), 0.0001);
	}
	
	@Test
	public void testBuilderWithNullEndereco() {
		// Arrange & Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Luna")
			.tipoAnimal("Gato")
			.endereco(null)
			.build();
		
		// Assert
		assertNotNull(pet);
		assertEquals("Luna", pet.getNomeAnimal());
		assertNull(pet.getCep());
		assertNull(pet.getRua());
		assertEquals(0.0, pet.getLatitude(), 0.0001);
	}
	
	@Test
	public void testBuilderDefaultStatus() {
		// Arrange & Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Max")
			.tipoAnimal("Cachorro")
			.build();
		
		// Assert
		assertEquals("P", pet.getStatus());  // Should default to "P" (Pending)
	}
	
	@Test
	public void testBuilderCustomStatus() {
		// Arrange & Act
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Bella")
			.tipoAnimal("Cachorro")
			.status("A")
			.build();
		
		// Assert
		assertEquals("A", pet.getStatus());  // Should use provided status
	}
	
	@Test
	public void testBuilderMethodChaining() {
		// Arrange & Act
		PetPerdido.Builder builder = PetPerdido.builder();
		PetPerdido.Builder resultBuilder = builder.nomeAnimal("Test");
		
		// Assert - Verify method chaining returns same builder
		assertSame(builder, resultBuilder);
	}
	
	@Test
	public void testBuilderComparison_BuilderVsConstructor() {
		// Arrange
		String nomeAnimal = "Totó";
		String dataPerdido = "2024-02-20";
		String descricao = "Perdido na praça";
		String tipoAnimal = "Cachorro";
		String pathImg = "https://example.com/toto.jpg";
		String cep = "12345-678";
		double latitude = -23.5;
		double longitude = -46.6;
		
		// Act - Create using builder
		PetPerdido petBuilder = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal(nomeAnimal)
			.dataPerdido(dataPerdido)
			.descricao(descricao)
			.tipoAnimal(tipoAnimal)
			.pathImg(pathImg)
			.cep(cep)
			.latitude(latitude)
			.longitude(longitude)
			.build();
		
		// Act - Create using constructor
		PetPerdido petConstructor = new PetPerdido(testUsuario, nomeAnimal, dataPerdido, descricao,
			tipoAnimal, pathImg, cep, latitude, longitude);
		
		// Assert - Both should have same values
		assertEquals(petConstructor.getNomeAnimal(), petBuilder.getNomeAnimal());
		assertEquals(petConstructor.getDataPerdido(), petBuilder.getDataPerdido());
		assertEquals(petConstructor.getDescricao(), petBuilder.getDescricao());
		assertEquals(petConstructor.getTipoAnimal(), petBuilder.getTipoAnimal());
		assertEquals(petConstructor.getCep(), petBuilder.getCep());
		assertEquals(petConstructor.getStatus(), petBuilder.getStatus());
		assertEquals(petConstructor.getLatitude(), petBuilder.getLatitude(), 0.0001);
		assertEquals(petConstructor.getLongitude(), petBuilder.getLongitude(), 0.0001);
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
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Spike")
			.tipoAnimal("Cachorro")
			.endereco(originalEndereco)
			.build();
		
		Endereco retrievedEndereco = pet.getEndereco();
		
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
	
	@Test
	public void testSetEnderecoMethod() {
		// Arrange
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Fido")
			.tipoAnimal("Cachorro")
			.build();
		
		Endereco endereco = new Endereco();
		endereco.setCep("11111-222");
		endereco.setLogradouro("Rua Teste");
		endereco.setBairro("Bairro Teste");
		endereco.setLocalidade("Cidade Teste");
		endereco.setUf("TT");
		endereco.setLatitude(-10.0);
		endereco.setLongitude(-20.0);
		
		// Act
		pet.setEndereco(endereco);
		
		// Assert
		assertEquals("11111-222", pet.getCep());
		assertEquals("Rua Teste", pet.getRua());
		assertEquals("Bairro Teste", pet.getBairro());
		assertEquals("Cidade Teste", pet.getCidade());
		assertEquals("TT", pet.getUf());
		assertEquals(-10.0, pet.getLatitude(), 0.0001);
		assertEquals(-20.0, pet.getLongitude(), 0.0001);
	}
	
	@Test
	public void testSetEnderecoWithNull() {
		// Arrange
		PetPerdido pet = PetPerdido.builder()
			.usuario(testUsuario)
			.nomeAnimal("Buddy")
			.tipoAnimal("Cachorro")
			.cep("12345-678")
			.build();
		
		String originalCep = pet.getCep();
		
		// Act
		pet.setEndereco(null);
		
		// Assert - CEP should remain unchanged when null is passed
		assertEquals(originalCep, pet.getCep());
	}
}
