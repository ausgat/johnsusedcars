package bo;

/**
 * Class representing the Inventory relation in the database
 *
 */
public class Inventory {
    
    private String vin;
    private boolean stockStatus;
    private String parkingSpot;
    private String parkingLot;

    public Inventory(String vin, boolean stockStatus, String parkingSpot, String parkingLot) {
        this.vin = vin;
        this.stockStatus = stockStatus;
        this.parkingSpot = parkingSpot;
        this.parkingLot = parkingLot;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }
}
