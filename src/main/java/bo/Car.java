package bo;

import java.util.Vector;

/**
 * Class representing the Car relation in the database
 *
 */
public class Car {
    
    private String vin;
    private String make;
    private String model;
    private int year;
    private int msrp;
    private boolean stockStatus;
    private String parkingSpot;
    private int iid;
    private int cid;

    public Car(String vin, String make, String model, int year, int msrp,
            boolean stockStatus, String parkingSpot, int iid, int cid) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.msrp = msrp;
        this.stockStatus = stockStatus;
        this.parkingSpot = parkingSpot;
        this.iid = iid;
        this.cid = cid;
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
        vec.add(this.vin);
        vec.add(this.make);
        vec.add(this.model);
        vec.add(this.year);
        vec.add(this.msrp);
        vec.add(this.stockStatus);
        vec.add(this.parkingSpot);
        return vec;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMsrp() {
        return msrp;
    }

    public void setMsrp(int msrp) {
        this.msrp = msrp;
    }

    public boolean isStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }
    
    /**
     * Useful function when you need to represent a Car as a human-readable
     * string (e.g. in a list or combo box)
     * @return A human-readable string representation of a Car
     */
    @Override
    public String toString() {
        return String.format("%s %s %s (%s)", year, make, model, vin);
    }
}
