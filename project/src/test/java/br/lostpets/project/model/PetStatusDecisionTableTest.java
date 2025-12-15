package br.lostpets.project.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.lostpets.project.model.valueobject.PetStatus;

/**
 * Decision Table Testing for PetStatus.
 * 
 * This test class implements decision table testing technique to validate
 * all possible combinations of PetStatus states and their corresponding behaviors.
 * 
 * Decision Table:
 * +------+----------+---------+----------+------------------+
 * | Rule | isPending| isFound | isWaiting| Expected Action  |
 * +------+----------+---------+----------+------------------+
 * |  1   |    V     |    X    |    X     | Show "尋找中"    |
 * |  2   |    X     |    V    |    X     | Show "已找到"    |
 * |  3   |    X     |    X    |    V     | Show "待確認"    |
 * |  4   |    X     |    X    |    X     | Invalid State    |
 * +------+----------+---------+----------+------------------+
 * 
 * V = True, X = False
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PetStatusDecisionTableTest {

	/**
	 * Rule 1: Pet is in Pending status (尋找中)
	 * - isPending = true
	 * - isFound = false
	 * - isWaiting = false
	 * - Expected: Status code "P", Description "Pendente"
	 */
	@Test
	public void testRule1_PendingStatus() {
		// Given: Pet status is Pending
		PetStatus status = PetStatus.PENDING;
		
		// When: Check status properties
		boolean isPending = (status == PetStatus.PENDING);
		boolean isFound = (status == PetStatus.FOUND);
		boolean isWaiting = (status == PetStatus.WAITING);
		
		// Then: Verify decision table rule 1
		assertTrue("Should be Pending", isPending);
		assertFalse("Should not be Found", isFound);
		assertFalse("Should not be Waiting", isWaiting);
		assertEquals("Status code should be P", "P", status.getCode());
		assertEquals("Description should be Pendente", "Pendente", status.getDescription());
	}

	/**
	 * Rule 2: Pet is in Found status (已找到)
	 * - isPending = false
	 * - isFound = true
	 * - isWaiting = false
	 * - Expected: Status code "A", Description "Achado"
	 */
	@Test
	public void testRule2_FoundStatus() {
		// Given: Pet status is Found
		PetStatus status = PetStatus.FOUND;
		
		// When: Check status properties
		boolean isPending = (status == PetStatus.PENDING);
		boolean isFound = (status == PetStatus.FOUND);
		boolean isWaiting = (status == PetStatus.WAITING);
		
		// Then: Verify decision table rule 2
		assertFalse("Should not be Pending", isPending);
		assertTrue("Should be Found", isFound);
		assertFalse("Should not be Waiting", isWaiting);
		assertEquals("Status code should be A", "A", status.getCode());
		assertEquals("Description should be Achado", "Achado", status.getDescription());
	}

	/**
	 * Rule 3: Pet is in Waiting status (待確認)
	 * - isPending = false
	 * - isFound = false
	 * - isWaiting = true
	 * - Expected: Status code "W", Description "Aguardando"
	 */
	@Test
	public void testRule3_WaitingStatus() {
		// Given: Pet status is Waiting
		PetStatus status = PetStatus.WAITING;
		
		// When: Check status properties
		boolean isPending = (status == PetStatus.PENDING);
		boolean isFound = (status == PetStatus.FOUND);
		boolean isWaiting = (status == PetStatus.WAITING);
		
		// Then: Verify decision table rule 3
		assertFalse("Should not be Pending", isPending);
		assertFalse("Should not be Found", isFound);
		assertTrue("Should be Waiting", isWaiting);
		assertEquals("Status code should be W", "W", status.getCode());
		assertEquals("Description should be Aguardando", "Aguardando", status.getDescription());
	}

	/**
	 * Rule 4: Invalid status code should throw exception
	 * - Tests that invalid codes are properly rejected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRule4_InvalidStatusCode() {
		// Given: Invalid status code
		String invalidCode = "X";
		
		// When: Try to create status from invalid code
		PetStatus.fromCode(invalidCode);
		
		// Then: Should throw IllegalArgumentException
		fail("Should have thrown IllegalArgumentException for invalid code");
	}

	/**
	 * Test all valid status codes can be converted correctly
	 */
	@Test
	public void testAllValidStatusCodes() {
		// Test Pending
		assertEquals("P should map to PENDING", 
			PetStatus.PENDING, PetStatus.fromCode("P"));
		
		// Test Found
		assertEquals("A should map to FOUND", 
			PetStatus.FOUND, PetStatus.fromCode("A"));
		
		// Test Waiting
		assertEquals("W should map to WAITING", 
			PetStatus.WAITING, PetStatus.fromCode("W"));
	}

	/**
	 * Test status code uniqueness - no two statuses should share the same code
	 */
	@Test
	public void testStatusCodeUniqueness() {
		PetStatus[] allStatuses = PetStatus.values();
		
		for (int i = 0; i < allStatuses.length; i++) {
			for (int j = i + 1; j < allStatuses.length; j++) {
				assertNotEquals("Status codes should be unique",
					allStatuses[i].getCode(), 
					allStatuses[j].getCode());
			}
		}
	}

	/**
	 * Test status description is not null or empty
	 */
	@Test
	public void testStatusDescriptionNotEmpty() {
		for (PetStatus status : PetStatus.values()) {
			assertNotNull("Description should not be null", status.getDescription());
			assertFalse("Description should not be empty", 
				status.getDescription().trim().isEmpty());
		}
	}
}
