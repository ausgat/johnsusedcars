package bo;

import java.time.LocalDate;
import java.util.Vector;

/**
 * Class representing the Sale relation in the database
 *
 */
public class Sale {
    
    private int id;
    private int price;
    private LocalDate date;
    private int spid;
    private int vin;

    public Sale(int id, LocalDate date, int price, int spid, int vin) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.spid = spid;
        this.vin = vin;
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
        vec.add(this.id);
        vec.add(this.date);
        vec.add(this.price);
        vec.add(this.spid);
        vec.add(this.vin);
        return vec;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }
}
