package bo;

import java.util.Vector;

/**
 * Class representing the Inventory relation in the database
 *
 */
public class Inventory {
    
    private int id;
    private String parkingLot;

    public Inventory(int id, String parkingLot) {
        this.id = id;
        this.parkingLot = parkingLot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }
    
    public Vector getRow() {
        Vector vec = new Vector();
        vec.add(this.id);
        vec.add(this.parkingLot);
        return vec;
    }

    @Override
    public String toString() {
        return this.parkingLot;
    }
}
