package bo;

import java.util.Vector;

/**
 * Class representing the Financing relation in the database
 *
 */
public class Financing {
    
    private int cid;
    private int monthlyPayment;
    private int interestRate;

    public Financing(int cid, int interestRate, int monthlyPayment) {
        this.cid = cid;
        this.monthlyPayment = monthlyPayment;
        this.interestRate = interestRate;
    }
    
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Financing(int monthlyPayment, int interestRate) {
        this.monthlyPayment = monthlyPayment;
        this.interestRate = interestRate;
    }

    public int getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(int monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Get all the attributes of the relation (mainly for use with
     * DefaultTableModel)
     * 
     * @return A vector list containing the rows, in order
     * @see javax.swing.table.DefaultTableModel
     */
    public Vector getRow() {
        Vector vec = new Vector();
        vec.add(this.cid);
        vec.add(this.interestRate);
        vec.add(this.monthlyPayment);
        return vec;
    }
}
