package dao;

import bo.Financing;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;
import java.sql.PreparedStatement;

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
     * @param rate The monthly interest rate
     * @param moPayment The monthly payment
     * @return Number of rows affected
     */
    public int addFinancing(int cid, int rate, int moPayment) {
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("INSERT INTO Financing" + "(cID, InterestRate, MonthlyPayment)" + "VALUES(?, ?, ?);");
            pst.setInt(1, cid);
            pst.setInt(2, rate);
            pst.setInt(3, moPayment);
        
         return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }

    /**
     * Delete a Financing from the database using the given primary keys
     * 
     * @param cid ID of customer
     * @return Number of rows affected
     */
    public int deleteFinancing(int cid) {
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("DELETE FROM Financing WHERE cID= ?");
        pst.setInt(1, cid);
        return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }

    /**
     * Update a Financing by cID with the given values
     *
     * @param cid ID of customer
     * @param rate Interest rate
     * @param moPayment Monthly payment
     * @return Number of rows affected
     */
    public int updateFinancing(int cid, int rate, int moPayment) {
        String cmdTemplate = "UPDATE Financing SET InterestRate=%d, MonthlyPayment=%d WHERE cID=%d";
        String cmd = String.format(cmdTemplate, rate, moPayment, cid);
        return sqlUtil.executeUpdate(cmd);
    }
    
    /**
     * Find a Financing by its cID
     * 
     * @param cid ID of customer
     * @return A Car object if found, null otherwise
     */
    public Financing findFinancing(int cid) {
        Financing foundFin = null;
        
        String cmdTemplate = "SELECT cID, InterestRate, MonthlyPayment FROM Financing WHERE cID=%d";
        String cmd = String.format(cmdTemplate, cid);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                int rate = rs.getInt("InterestRate");
                int moPayment = rs.getInt("MonthlyPayment");
                
                foundFin = new Financing(cid, rate, moPayment);
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
                int rate = rs.getInt("InterestRate");
                int moPayment = rs.getInt("MonthlyPayment");

                // Create a new Financing object from the relation data
                Financing fin = new Financing(cid, rate, moPayment);
                results.add(fin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FinancingHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
}
