import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * 
 * Unit test for setter and getters
 * @author ryan.kelly5
 *
 */
class RyanFlightSearchTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link RyanFlightSearch#getPassangerFirstName()}.
	 */
	@Test
	void testGetPassangerFirstName() {
		String firstName = RyanFlightSearch.getPassangerFirstName();
		assertNull(firstName, "Name is not null");
		
	}

	/**
	 * Test method for {@link RyanFlightSearch#setPassangerFirstName(java.lang.String)}.
	 */
	@Test
	void testSetPassangerFirstName() {
		RyanFlightSearch.setPassangerFirstName("Ryan");
		String firstName = RyanFlightSearch.getPassangerFirstName();
		assertTrue(firstName.contains("Ryan"), "Name is not Ryan");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getPassagnerLastName()}.
	 */
	@Test
	void testGetPassagnerLastName() {
		String last = RyanFlightSearch.getPassagnerLastName();
		assertNull(last, "Name is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#setPassagnerLastName(java.lang.String)}.
	 */
	@Test
	void testSetPassagnerLastName() {
		RyanFlightSearch.setPassangerFirstName("Kelly");
		String last = RyanFlightSearch.getPassangerFirstName();
		assertTrue(last.contains("Kelly"), "Name is not Kelly");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getFlightNumber()}.
	 */
	@Test
	void testGetFlightNumber() {
		String test = RyanFlightSearch.getFlightNumber();
		assertNull(test, "number is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#setFlightNumber(java.lang.String)}.
	 */
	@Test
	void testSetFlightNumber() {
		RyanFlightSearch.setPassangerFirstName("123");
		String test = RyanFlightSearch.getPassangerFirstName();
		assertTrue(test.contains("123"), "number is not 123");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getFlightSource()}.
	 */
	@Test
	void testGetFlightSource() {
		String test = RyanFlightSearch.getFlightSource();
		assertNull(test, "source is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getFlightDest()}.
	 */
	@Test
	void testGetFlightDest() {
		String test = RyanFlightSearch.getFlightDest();
		assertNull(test, "source is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getFlightTime()}.
	 */
	@Test
	void testGetFlightTime() {
		String test = RyanFlightSearch.getFlightTime();
		assertNull(test, "source is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#setFlightTime(java.lang.String)}.
	 */
	@Test
	void testSetFlightTime() {
		RyanFlightSearch.setFlightTime("123");
		String test = RyanFlightSearch.getFlightTime();
		assertTrue(test.contains("123"), "number is not 123");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getNumberOfSeatsAviable()}.
	 */
	@Test
	void testGetNumberOfSeatsAviable() {
		String test = RyanFlightSearch.getNumberOfSeatsAviable();
		assertNull(test, "source is not null");
	}

	/**
	 * Test method for {@link RyanFlightSearch#getNumberofSeatsRequested()}.
	 */
	@Test
	void testGetNumberofSeatsRequested() {
		String test = RyanFlightSearch.getNumberofSeatsRequested();
		assertNull(test, "source is not null");
	}

}
