package MainPackage;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class tuitionCalculatorTest {
	// Four test cases are established
	studentTuitionCalculator stc1 = new studentTuitionCalculator(10000, 12, .05, .07);
	studentTuitionCalculator stc2 = new studentTuitionCalculator(15000, 4, .08, .02);
	studentTuitionCalculator stc3 = new studentTuitionCalculator(6000, 20, .03, .15);
	studentTuitionCalculator stc4 = new studentTuitionCalculator(30000, 15, .10, .10);

	@Before
	public void setUp() {
		//All the actual calculations take place before the tests
		//The assertTrue's are only using the getters for the calculated values
		stc1.calculateTotalTuition();
		stc2.calculateTotalTuition();
		stc3.calculateTotalTuition();
		stc4.calculateTotalTuition();

		stc1.calculateMonthlyPayment();
		stc2.calculateMonthlyPayment();
		stc3.calculateMonthlyPayment();
		stc4.calculateMonthlyPayment();
	}

	@Test
	public void testCalculateTotalTuition() {
		// These test cases were checked with my own manual calculations
		assertTrue(stc1.getTotalTuition() == 44399.43);
		assertTrue(stc2.getTotalTuition() == 61824.12);
		assertTrue(stc3.getTotalTuition() == 29960.25);
		assertTrue(stc4.getTotalTuition() == 139230);
	}

	@Test
	public void testCalculateMonthlyPayment() {
		/*
		 * These test cases were checked with Excel's PMT function. Because of a
		 * loss of precision, I'm checking not for identical values, but for
		 * whether the difference between my method's value and Excel's value is
		 * less than .01 (1 cent)
		 */
		assertTrue(Math.abs(stc1.getMonthlyPayment() - 225.65) < .01);
		assertTrue(Math.abs(stc2.getMonthlyPayment() - 1097.15) < .01);
		assertTrue(Math.abs(stc3.getMonthlyPayment() - 91.26) < .01);
		assertTrue(Math.abs(stc4.getMonthlyPayment() - 335.92) < .01);
	}

}
