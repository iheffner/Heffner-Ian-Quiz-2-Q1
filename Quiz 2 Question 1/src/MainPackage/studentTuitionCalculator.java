package MainPackage;

/*@author Ian Heffner
 * Quiz 2 Q1
 * 
 * This program calculates the student loan size and monthly loan payment for a student
 * attending four years of college.
 * 
 * The studentTuitionCalculator class has the following private attributes:
 * The first four are input by the user:
 *  initialTuition - the tuition price of the first year of college
 *  paymentTerm - the number of years over which the student pays their loan back
 *  repaymentAPR - the fixed APR for the loan
 *  tuitionIncrease - the percent by which tuition increases each year
 * The next two are calculated with the class's instance methods:
 *  totalTuition - the price of all four years of college
 *  monthlyPayment - the monthly payment for repaying the student loan in n years
 * 
 * Every attribute has a getter and a setter, except for totalTuition and monthlyPayment.
 * These two attributes have only one getter, and a separate method that calculates their
 * values. These calculation methods essentially function as setters, but are less direct.
 * 
 * The main method handles all of the other methods.
 */

//Import the Scanner and FinanceLib
import java.util.Scanner;
import org.apache.poi.ss.formula.functions.FinanceLib;

public class studentTuitionCalculator {
	// Establish variables to extract from input
	private double initialTuition;
	private int paymentTerm;
	private double repaymentAPR;
	private double tuitionIncrease;

	// Establish variables to be calculated
	private double totalTuition;
	private double monthlyPayment;

	public static void main(String[] args) {
		//The main method handles all of the other methods
		//Initializes a new object of the studentTuitionCalculator class
		studentTuitionCalculator stc = new studentTuitionCalculator();
		//Prompts the user for the four attributes and sets the values to the input
		stc.setAll();
		//Runs the two calculation methods
		stc.calculateTotalTuition();
		stc.calculateMonthlyPayment();
		//Prints the results out to the user
		System.out.printf("\nThe cost of four years of tuition will be $%,.2f", stc.getTotalTuition());
		System.out.printf(
				".\nFor a loan of this size, the monthly payment will be $%,.2f over your specified number of years.",
				stc.getMonthlyPayment());
	}

	public studentTuitionCalculator() {
		// No-args constructor
	}

	public studentTuitionCalculator(double initialTuition, int paymentTerm, double repaymentAPR,
			double tuitionIncrease) {
		// Constructor with arguments
		// Only really used for the JUnit test cases, given that it skips the user input step
		// Does not initialize values for the variables that need to be calculated
		this.initialTuition = initialTuition;
		this.paymentTerm = paymentTerm;
		this.repaymentAPR = repaymentAPR;
		this.tuitionIncrease = tuitionIncrease;
	}

	//Getter and setter for initialTuition
	public double getInitialTuition() {
		return initialTuition;
	}

	public void setInitialTuition(double initialTuition) {
		this.initialTuition = initialTuition;
	}

	//Getter and setter for paymentTerm
	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	//Getter and setter for repaymentAPR
	public double getRepaymentAPR() {
		return repaymentAPR;
	}

	public void setRepaymentAPR(double repaymentAPR) {
		this.repaymentAPR = repaymentAPR;
	}

	//Getter and setter for tuitionIncrease
	public double getTuitionIncrease() {
		return tuitionIncrease;
	}

	public void setTuitionIncrease(double tuitionIncrease) {
		this.tuitionIncrease = tuitionIncrease;
	}

	//Getter for totalTuition
	public double getTotalTuition() {
		return totalTuition;
	}

	//Getter for monthlyPayment
	public double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setAll() {
		//This method runs all of the setters according to user input
		
		//Creates a new Scanner
		Scanner input = new Scanner(System.in);
		
		//Prompts the user for the various attributes, and runs respective setters
		System.out.print("What is the initial tuition freshman year?: ");
		double initialTuition = input.nextDouble();
		setInitialTuition(initialTuition);

		System.out.print("What is the loan repayment term, in years?: ");
		int paymentTerm = input.nextInt();
		setPaymentTerm(paymentTerm);

		System.out.print("What is the fixed annual APR on repayment? Format as a decimal 0.0 to 1.0: ");
		double repaymentAPR = input.nextDouble();
		setRepaymentAPR(repaymentAPR);

		System.out.print("By what percent does tuition increase each year? Format as a decimal 0.0 to 1.0: ");
		double tuitionIncrease = input.nextDouble();
		setTuitionIncrease(tuitionIncrease);
		
		//Closes the Scanner
		input.close();
	}

	public void calculateTotalTuition() {
		//This method calculates total tuition
		
		//We are assuming four years of college, so we can find the totalTuition without a loop easily
		//Each years tuition is the last year's tuition plus the increase
		double firstYearTuition = this.getInitialTuition();
		double secondYearTuition = firstYearTuition + firstYearTuition * this.getTuitionIncrease();
		double thirdYearTuition = secondYearTuition + secondYearTuition * this.getTuitionIncrease();
		double fourthYearTuition = thirdYearTuition + thirdYearTuition * this.getTuitionIncrease();

		//Sets the value of totalTuition
		this.totalTuition = firstYearTuition + secondYearTuition + thirdYearTuition + fourthYearTuition;
	}

	public void calculateMonthlyPayment() {
		/*
		 * Using FinanceLib function pmt pmt parameters: 
		 * double r - rate 
		 * double n - number of payments double p - present value 
		 * double f - future value 
		 * boolean t - type (true=pmt at end of period, false=pmt at begining of period) pmt returns the monthly payment required to reach
		 * the future value in n payments
		 */

		//Divides APR by 12 to have a rate per month
		double r = this.getRepaymentAPR() / 12;
		//Multiples paymentTerm by 12 to have a number of months, not years
		double n = this.getPaymentTerm() * 12;
		//No present value because the initial loan has not been paid off at all
		double p = 0;
		//Future value is the totalTuition
		double f = this.getTotalTuition();
		//Type is false because payments are due at the end of each month
		boolean t = false;

		//The pmt method returns a negative value, because a payment is like a 
		//subtraction from the user's funds. Multiplied by -1 for convenience.
		this.monthlyPayment = -1 * FinanceLib.pmt(r, n, p, f, t);
	}
}
