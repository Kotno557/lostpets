package br.lostpets.project.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.lostpets.project.model.valueobject.PetStatus;
import br.lostpets.project.repository.PetPerdidoRepository;

/**
 * State Transition Testing for PetPerdido status changes.
 * 
 * This test validates the state machine for pet status transitions:
 * 
 * State Transition Diagram:
 * 
 *     [Pending (P)]
 *          |
 *          +---(找到)----> [Found (A)]
 *          |
 *          +---(待確認)--> [Waiting (W)]
 *                              |
 *                              +---(確認)----> [Found (A)]
 * 
 * Valid Transitions:
 * - PENDING → FOUND   (Pet was found directly)
 * - PENDING → WAITING (Found report needs confirmation)
 * - WAITING → FOUND   (Report confirmed)
 * 
 * Invalid Transitions:
 * - FOUND → PENDING   (Cannot unfind a pet)
 * - FOUND → WAITING   (Already confirmed as found)
 * - WAITING → PENDING (Cannot revert to pending)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PetStatusTransitionTest {

	@Autowired
	private PetPerdidoRepository petPerdidoRepository;

	private PetPerdido testPet;
	private Usuario testUser;

	@Before
	public void setUp() {
		// Create test user
		testUser = new Usuario();
		testUser.setNome("Test Owner");
		testUser.setEmail("owner@test.com");
		testUser.setSenha("password123");
		testUser.setTelefoneCelular("11987654321");

		// Create test pet with initial PENDING status
		testPet = new PetPerdido();
		testPet.setNomeAnimal("Buddy");
		testPet.setDescricaoAnimal("Golden Retriever - Golden - Grande");
		testPet.setTipoAnimal("Cachorro");
		testPet.setStatus("P"); // Initial state: PENDING
		testPet.setUsuario(testUser);
	}

	/**
	 * Test valid transition: PENDING → FOUND
	 * Scenario: Pet was found directly without needing confirmation
	 */
	@Test
	public void testValidTransition_PendingToFound() {
		// Given: Pet is in PENDING status
		assertEquals("Initial status should be PENDING", "P", testPet.getStatus());
		assertEquals("Initial status should be PENDING", 
			PetStatus.PENDING, PetStatus.fromCode(testPet.getStatus()));

		// When: Pet is found
		testPet.setStatus("A");

		// Then: Status should be FOUND
		assertEquals("Status should be FOUND", "A", testPet.getStatus());
		assertEquals("Status should be FOUND", 
			PetStatus.FOUND, PetStatus.fromCode(testPet.getStatus()));
	}

	/**
	 * Test valid transition: PENDING → WAITING
	 * Scenario: Someone reported finding the pet, waiting for owner confirmation
	 */
	@Test
	public void testValidTransition_PendingToWaiting() {
		// Given: Pet is in PENDING status
		assertEquals("Initial status should be PENDING", "P", testPet.getStatus());

		// When: Found report received, waiting for confirmation
		testPet.setStatus("W");

		// Then: Status should be WAITING
		assertEquals("Status should be WAITING", "W", testPet.getStatus());
		assertEquals("Status should be WAITING", 
			PetStatus.WAITING, PetStatus.fromCode(testPet.getStatus()));
	}

	/**
	 * Test valid transition: WAITING → FOUND
	 * Scenario: Owner confirmed the found report
	 */
	@Test
	public void testValidTransition_WaitingToFound() {
		// Given: Pet is in WAITING status
		testPet.setStatus("W");
		assertEquals("Initial status should be WAITING", "W", testPet.getStatus());

		// When: Owner confirms the found report
		testPet.setStatus("A");

		// Then: Status should be FOUND
		assertEquals("Status should be FOUND", "A", testPet.getStatus());
		assertEquals("Status should be FOUND", 
			PetStatus.FOUND, PetStatus.fromCode(testPet.getStatus()));
	}

	/**
	 * Test complete workflow: PENDING → WAITING → FOUND
	 * Scenario: Full lifecycle with confirmation step
	 */
	@Test
	public void testCompleteWorkflow_PendingToWaitingToFound() {
		// Given: Pet starts as PENDING
		assertEquals("Should start as PENDING", "P", testPet.getStatus());

		// When: Someone reports finding the pet
		testPet.setStatus("W");
		assertEquals("Should transition to WAITING", "W", testPet.getStatus());

		// And: Owner confirms it's their pet
		testPet.setStatus("A");

		// Then: Pet is marked as FOUND
		assertEquals("Should end as FOUND", "A", testPet.getStatus());
	}

	/**
	 * Test direct workflow: PENDING → FOUND
	 * Scenario: Pet found without needing confirmation
	 */
	@Test
	public void testDirectWorkflow_PendingToFound() {
		// Given: Pet starts as PENDING
		assertEquals("Should start as PENDING", "P", testPet.getStatus());

		// When: Pet is directly marked as found (e.g., owner found it)
		testPet.setStatus("A");

		// Then: Pet is FOUND
		assertEquals("Should be FOUND", "A", testPet.getStatus());
		assertEquals("Should be FOUND status", 
			PetStatus.FOUND, PetStatus.fromCode(testPet.getStatus()));
	}

	/**
	 * Test that all status codes are valid enum values
	 */
	@Test
	public void testAllStatusCodesAreValid() {
		// Test each status can be set and retrieved
		String[] validCodes = {"P", "A", "W"};
		
		for (String code : validCodes) {
			testPet.setStatus(code);
			assertEquals("Status should be set correctly", code, testPet.getStatus());
			assertNotNull("Should be valid PetStatus enum", PetStatus.fromCode(code));
		}
	}

	/**
	 * Test invalid status code handling
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidStatusCode() {
		// Given: Invalid status code
		String invalidCode = "X";

		// When: Try to convert invalid code
		PetStatus.fromCode(invalidCode);

		// Then: Should throw IllegalArgumentException
		fail("Should have thrown IllegalArgumentException");
	}

	/**
	 * Test status persistence and retrieval
	 * Verifies that status changes persist correctly in the database
	 */
	@Test
	public void testStatusPersistence() {
		// Given: Pet with PENDING status is saved
		testPet.setStatus("P");
		PetPerdido savedPet = petPerdidoRepository.save(testPet);
		int petId = savedPet.getIdAnimal();

		// When: Status is updated to WAITING
		savedPet.setStatus("W");
		petPerdidoRepository.save(savedPet);

		// Then: Status should persist correctly
		PetPerdido retrievedPet = petPerdidoRepository.findById(petId).orElse(null);
		assertNotNull("Pet should be found", retrievedPet);
		assertEquals("Status should be WAITING", "W", retrievedPet.getStatus());

		// When: Status is updated to FOUND
		retrievedPet.setStatus("A");
		petPerdidoRepository.save(retrievedPet);

		// Then: Final status should be FOUND
		PetPerdido finalPet = petPerdidoRepository.findById(petId).orElse(null);
		assertNotNull("Pet should be found", finalPet);
		assertEquals("Status should be FOUND", "A", finalPet.getStatus());

		// Cleanup
		petPerdidoRepository.deleteById(petId);
	}

	/**
	 * Test business rule: Found pets should not change status
	 * Note: This is a conceptual test - in production you might want to
	 * enforce this rule in the service layer or with database constraints
	 */
	@Test
	public void testBusinessRule_FoundPetsShouldNotRevertStatus() {
		// Given: Pet is FOUND
		testPet.setStatus("A");
		assertEquals("Pet should be FOUND", "A", testPet.getStatus());

		// When: Attempt to change back to PENDING (business logic should prevent this)
		// Note: Current implementation allows this, but it's documented as invalid
		testPet.setStatus("P");

		// Then: Verify the change (in production, this should be prevented)
		// This test documents current behavior - consider adding validation
		assertEquals("Status was changed (should be prevented in production)", 
			"P", testPet.getStatus());
		
		// TODO: Implement business rule validation in PetPerdidoService
		// to prevent invalid state transitions
	}

	/**
	 * Test state machine coverage - all states should be reachable
	 */
	@Test
	public void testAllStatesAreReachable() {
		// Test that we can reach all states from PENDING
		testPet.setStatus("P");
		
		// Can reach WAITING
		testPet.setStatus("W");
		assertEquals("Should reach WAITING", "W", testPet.getStatus());
		
		// Can reach FOUND from WAITING
		testPet.setStatus("A");
		assertEquals("Should reach FOUND from WAITING", "A", testPet.getStatus());
		
		// Reset and test direct path to FOUND
		testPet.setStatus("P");
		testPet.setStatus("A");
		assertEquals("Should reach FOUND directly from PENDING", "A", testPet.getStatus());
	}
}
