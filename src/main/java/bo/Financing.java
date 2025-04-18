package bo;

import java.util.Vector;

/**
 * Class representing the Financing relation in the database
 *
 */
public class Financing {
    
    private int cid;
    private int sid;
    private double monthlyPayment;
    private double interestRate;

    public Financing(int cid, int sid, double monthlyPayment, double interestRate) {
        this.cid = cid;
        this.sid = sid;
        this.monthlyPayment = monthlyPayment;
        this.interestRate = interestRate;
    }
    
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

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
        vec.add(this.sid);
        vec.add(this.interestRate);
        vec.add(this.monthlyPayment);
        return vec;
    }
}
