package dao;

import bo.Car;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 * Handles interactions between the Java code and the database relations for Car
 *
 */
public class CarHandler {
    private SQLUtil sqlUtil;
    
    public CarHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    
    /**
     * Create a new Car relation in the database
     * 
     * @param vin   The car's VIN number (primary key)
     * @param make  The car's make
     * @param model The car's model
     * @param year  The car's year
     * @param msrp  The car's MSRP
     * @return Number of rows affected
     */
    public int addCar(int vin, String make, String model, int year, int msrp) {
        // We're creating a pretty long SQL query with lots of data, so let's
        // use a string template to make things a little easier
        String cmdTemplate = "INSERT INTO Car VALUES(%d, '%s', '%s', %d, %d, NULL);";

        // Add the values to the string template with String.format (this will
        // fill the template with the given data for each of the %d's and %s's,
        // in order)
        String cmd = String.format(cmdTemplate, vin, make, model, year, msrp);

        // Run the SQL command and return its value
        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Delete a car from the database using the given VIN
     * 
     * @param vin   The VIN number of the car
     * @return Number of rows affected
     */
    public int deleteCar(int vin) {
        String cmdTemplate = "DELETE FROM Car WHERE vin = %d";
        String cmd = String.format(cmdTemplate, vin);

        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Update a car by VIN with the given values
     * 
     * @param vin   VIN number of the car to update
     * @param make  Car's make
     * @param model Car's model
     * @param year  Car's year
     * @param msrp  Car's MSRP
     * @return Number of rows affected
     */
    public int updateCar(int vin, String make, String model, int year, int msrp) {
        String cmdTemplate = "UPDATE Car SET Make='%s', Model='%s', Year=%d, MSRP=%d WHERE VIN=%d;";
        String cmd = String.format(cmdTemplate, make, model, year, msrp, vin);
        return sqlUtil.executeUpdate(cmd);
    }
    
    /**
     * Find a car by its VIN
     * 
     * @param vin The VIN of the desired car
     * @return A Car object if found, null otherwise
     */
    public Car findCar(int vin) {
        Car foundCar = null;
        
        String cmdTemplate =
                "SELECT Vin, Make, Model, Year, MSRP FROM Car WHERE Vin = %d";
        String cmd = String.format(cmdTemplate, vin);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");
                
                foundCar = new Car(vin, make, model, year, msrp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundCar;
    }
    
    /**
     * Get a list of all the Car relations in the database
     * 
     * @return List of all the Car relations
     */
    public List<Car> getCars() {
        List<Car> results = new ArrayList<Car>();

        // Put the query in a string
        String cmd = "SELECT * FROM Car;";

        // Run the query (you could also just put the string directly in the
        // query, but putting it in a string might make things easier to expand
        // on later)
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            // Go to the next row (will continue until there are no more rows
            // and rs.next() returns false)
            while (rs.next()) {
                // Get each relevant attribute from the relation
                int vin = rs.getInt("Vin");
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");

                // Create a new Car object from the relation data
                Car c = new Car(vin, make, model, year, msrp);

                // Add the newly-created Car object to the list
                results.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Return the list of Car objects
        return results;
    }
}
