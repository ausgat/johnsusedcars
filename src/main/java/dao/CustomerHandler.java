/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import bo.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.SQLUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jsmit
 */
public class CustomerHandler {
    
    private SQLUtil sqlUtil;
    
    public CustomerHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    public List<Customer>getCustomers(){
        List<Customer> results = new ArrayList<>();
        String cmd = "select cID, cName, cPhone, cEmail from Customer";
        ResultSet rs = sqlUtil.executeQuery(cmd);
        try {
            while(rs.next()){
                int id = rs.getInt("cID");
                String name = rs.getString("cName");
                String phone = rs.getString("cPhone");
                String email = rs.getString("cEmail");
                
                Customer c = new Customer(id, name, phone, email);
                results.add(c);
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    }
    

