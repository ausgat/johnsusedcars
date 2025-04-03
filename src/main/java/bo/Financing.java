package bo;

/**
 * Class representing the Financing relation in the database
 *
 */
public class Financing {
    
    private double monthlyPayment;
    private double interestRate;

    public Financing(double monthlyPayment, double interestRate) {
        this.monthlyPayment = monthlyPayment;
        this.interestRate = interestRate;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
