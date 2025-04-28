/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import bo.Customer;
import java.sql.PreparedStatement;
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
    public int addCustomer(String name, String phone, String email){
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("INSERT INTO Customer (cName, cPhone, cEmail) " + " VALUES (?, ?, ?)", true);
        pst.setString(1, name);
        pst.setString(2, phone);
        pst.setString(3, email);
        return sqlUtil.executeUpdateWithGenKey(pst);
        }catch(SQLException ex){
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int deleteCustomer(int id){
        String cmdTemplate = "DELETE FROM CUSTOMER where cID = %d";
        String cmd = String.format(cmdTemplate, id);
        return sqlUtil.executeUpdate(cmd);
    }
    public int updateCustomer(int id, String name, String phone, String email){
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("UPDATE Customer SET cName = ?, cPhone = ?, cEmail = ? WHERE cId = ?", 
            true);
        pst.setString(1, name);
        pst.setString(2, phone);
        pst.setString(3, email);
        pst.setInt(4, id);
        return sqlUtil.executeUpdate(pst);
        }catch(SQLException ex){
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public List<Customer>getCustomers(String keyword){
        List<Customer> results = new ArrayList<>();
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("SELECT * FROM Customer WHERE cName LIKE ?");
       
         pst.setString(1, "%" + keyword + "%");
        ResultSet rsCustomer = pst.executeQuery();
        
            while(rsCustomer.next()){
                int id = rsCustomer.getInt("cID");
                String name = rsCustomer.getString("cName");
                String phone = rsCustomer.getString("cPhone");
                String email = rsCustomer.getString("cEmail");
                
                Customer sc = new Customer(id, name, phone, email);
                results.add(sc);
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public Customer findCustomer(int cid) {
        Customer customer = null;
        try {
            PreparedStatement pst = sqlUtil.prepareStatement(
                    "SELECT * FROM Customer WHERE cID=?"
            );
            pst.setInt(1, cid);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("cName");
                String phone = rs.getString("cPhone");
                String email = rs.getString("cEmail");
                customer = new Customer(cid, name, phone, email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customer;
    }
}
    

