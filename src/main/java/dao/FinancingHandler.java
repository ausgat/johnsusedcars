package dao;

import bo.Financing;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 * Handles interactions between the Java code and the database relations for
 * Financing
 *
 */
public class FinancingHandler {
    private final SQLUtil sqlUtil;
    
    public FinancingHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    
    /**
     * Create a new Financing relation in the database
     * 
     * @param cid ID of the customer
     * @param sid ID of the sale of the car
     * @param rate The monthly interest rate
     * @param moPayment The monthly payment
     * @return Number of rows affected
     */
    public int addFinancing(int cid, int sid, int rate, int moPayment) {
        String cmdTemplate = "INSERT INTO Financing (cID, sID, InterestRate, MonthlyPayment) VALUES(%d, %d, %d, %d);";
        String cmd = String.format(cmdTemplate, cid, sid, rate, moPayment);
        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Delete a Financing from the database using the given primary keys
     * 
     * @param cid ID of customer
     * @param sid ID of the sale of the car
     * @return Number of rows affected
     */
    public int deleteFinancing(int cid, int sid) {
        String cmdTemplate = "DELETE FROM Financing WHERE cID=%d AND sID=%d";
        String cmd = String.format(cmdTemplate, cid, sid);
        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Update a Financing by cID and sID with the given values
     *
     * @param cid ID of customer
     * @param sid ID of sale of car
     * @param rate Interest rate
     * @param moPayment Monthly payment
     * @return Number of rows affected
     */
    public int updateFinancing(int cid, int sid, int rate, int moPayment) {
        String cmdTemplate = "UPDATE Financing SET InterestRate=%d, MonthlyPayment=%d WHERE cID=%d AND sID=%d;";
        String cmd = String.format(cmdTemplate, rate, moPayment, cid, sid);
        return sqlUtil.executeUpdate(cmd);
    }
    
    /**
     * Find a Financing by its cID and sID
     * 
     * @param cid ID of customer
     * @param sid ID of sale of car
     * @return A Car object if found, null otherwise
     */
    public Financing findFinancing(int cid, int sid) {
        Financing foundFin = null;
        
        String cmdTemplate =
                "SELECT cID, sID, InterestRate, MonthlyPayment FROM Financing WHERE cID=%d AND sID=%d";
        String cmd = String.format(cmdTemplate, cid, sid);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                int rate = rs.getInt("InterestRate");
                int moPayment = rs.getInt("MonthlyPayment");
                
                foundFin = new Financing(cid, sid, rate, moPayment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FinancingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundFin;
    }
    
    /**
     * Get a list of all the Financing relations in the database
     * 
     * @return List of all the Financing relations
     */
    public List<Financing> getFinancings() {
        List<Financing> results = new ArrayList<>();

        String cmd = "SELECT * FROM Financing;";
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            while (rs.next()) {
                // Get each relevant attribute from the relation
                int cid = rs.getInt("cID");
                int sid = rs.getInt("sID");
                int rate = rs.getInt("InterestRate");
                int moPayment = rs.getInt("MonthlyPayment");

                // Create a new Financing object from the relation data
                Financing fin = new Financing(cid, sid, rate, moPayment);
                results.add(fin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FinancingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
}
