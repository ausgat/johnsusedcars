package bo;

import java.util.Vector;

/**
 * Inventory stock representing number of cars in an Inventory lot
 */
public class InventoryStock {
    
    private int carCount;
    private String parkingLot;
    
    public InventoryStock(int carCount, String parkingLot) {
        this.carCount = carCount;
        this.parkingLot = parkingLot;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
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
        vec.add(this.carCount);
        vec.add(this.parkingLot);
        return vec;
    }
}
