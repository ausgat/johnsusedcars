/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import bo.Salesperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 *
 * @author ausgat
 */
public class SalespersonHandler {
    
    private SQLUtil sqlUtil;
    
    public SalespersonHandler() {
        sqlUtil = new SQLUtil();
    }
    
    public Salesperson login(String username, String password) {
        Salesperson sp = null;
        String sqlQuery = String.format(
            "SELECT * FROM Salesperson WHERE sName='%s'", username);
        ResultSet rs = sqlUtil.executeQuery(sqlQuery);
        try {
            while (rs.next()) {
                int spId = rs.getInt("SalespersonID");
                String spName = rs.getString("sName");
                String spPhone = rs.getString("sPhone");
                String spEmail = rs.getString("sEmail");
                sp = new Salesperson(spId, spName, spPhone, spEmail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sp;
    }
}
