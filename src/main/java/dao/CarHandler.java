/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author ausgat
 */
public class CarHandler {
    private SQLUtil sqlUtil;
    
    public CarHandler() {
        sqlUtil = new SQLUtil();
    }
    
    public int addCar(int vin, String make, String model, int year, int msrp) {
        String cmdTemplate = "INSERT INTO Car VALUES(%d, '%s', '%s', %d, %d, NULL);";
        String cmd = String.format(cmdTemplate, vin, make, model, year, msrp);
        return sqlUtil.executeUpdate(cmd);
    }
    
    public List<Car> getCars() {
        List<Car> results = new ArrayList<Car>();
        String cmd = "SELECT * FROM Car;";
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            while (rs.next()) {
                int vin = rs.getInt("Vin");
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");

                Car c = new Car(vin, make, model, year, msrp);
                results.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
}
