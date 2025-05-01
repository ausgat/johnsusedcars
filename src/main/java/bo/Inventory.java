package bo;

import java.util.Vector;

/**
 * Class representing the Inventory relation in the database
 *
 */
public class Inventory {
    
    private boolean stockStatus;
    private String parkingSpot;
    private String lotId;

    public Inventory(boolean stockStatus, String parkingSpot, String lotId) {
        this.stockStatus = stockStatus;
        this.parkingSpot = parkingSpot;
        this.lotId = lotId;
    }

    public Vector getRow() {
        Vector vec = new Vector();
        vec.add(this.stockStatus);
        vec.add(this.parkingSpot);
        vec.add(this.lotId);
        return vec;
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

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }
}
