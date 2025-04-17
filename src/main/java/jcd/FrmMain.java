package jcd;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import utils.GlobalData;

/**
 * Swing form for the main window containing all the other windows
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * Creates new form FrmMain
     */

    // Create instances of each form we will need (e.g. login form, car list
    // form, etc.)
    // NOTE: This will not show the forms yet
    FrmLogin frmLogin = new FrmLogin();
    FrmListCars frmListCars = new FrmListCars();
    FrmMakeSale frmSellCar = new FrmMakeSale();
    FrmAddCar frmAddCar = new FrmAddCar();
    FrmAddCustomers frmAddCustomer = new FrmAddCustomers();
    FrmListSales frmListSales = new FrmListSales();
    FrmMakeSale frmMakeSale = new FrmMakeSale();

    /**
     * Hashmap containing all the forms we'll be using. Key is a string
     * containing the name of the form, value is the form object as instantiated
     * above.
     */
    Map<String, JInternalFrame> forms = new HashMap<>();
    
    public FrmMain() {
        // Basic Swing stuff
        initComponents();

        // Give a name to each form and add it to our hashmap
        forms.put("frmLogin", frmLogin);
        forms.put("frmListCars", frmListCars);
        forms.put("frmSellCar", frmSellCar);
        forms.put("frmAddCar", frmAddCar);
        forms.put("frmAddCustomer", frmAddCustomer);
        forms.put("frmListSales", frmListSales);
        forms.put("frmMakeSale", frmMakeSale);
        
        // For each form we created, add it to the form container in the main
        // window (NOTE: This still won't show the forms yet)
        forms.values().forEach((frm)->{
            jdpContainer.add(frm);
        });
    }
    
    /**
     * Show a form without first checking if a Salesperson is logged in
     * @param name The name of the form
     */
    private void showForm(String name) {
        showForm(name, false);
    }
    
    /**
     * Show a named form, checking whether a Salesperson is logged in if desired
     * 
     * @param name The name of the form
     * @param checkLogin Whether to check if a Salesperson is logged in
     */
    private void showForm(String name, boolean checkLogin) {

        // If the global variable sp (Salesperson) is null, nobody is logged in
        if (checkLogin && GlobalData.sp == null) {
            // Do not pass go, do not collect $200--show the login form
            showForm("frmLogin", false);

        // We're here because either we're not checking for a login OR
        // sp != null (we're actually logged in)
        } else {
            // Get the form from our map of forms based on the given name
            JInternalFrame form = forms.get(name);

            // If the form exists but had been closed at some point, we have to
            // recreate it
            if (form != null && form.isClosed()) {
                try {
                    // Get the class of the closed form and make a new instance
                    form = forms.get(name).getClass().getDeclaredConstructor().newInstance();

                    // Replace the closed form in our map with the new one
                    forms.put(name, form);

                    // Add the new form to our main window
                    jdpContainer.add(form);
                } catch (InstantiationException | IllegalAccessException |
                        NoSuchMethodException | SecurityException |
                        IllegalArgumentException |
                        InvocationTargetException ex) {
                    Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            // Some way or another we have the form we need--make it visible
            // (NOTE: This finally shows the form)
            form.setVisible(true);

            try {
                // Try to select the form (bring it to the front)
                form.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FrmMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpContainer = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mniLogIn = new javax.swing.JMenuItem();
        mniLogOut = new javax.swing.JMenuItem();
        mnuManage = new javax.swing.JMenu();
        mnuCars = new javax.swing.JMenu();
        mniListCars = new javax.swing.JMenuItem();
        mniAddCar = new javax.swing.JMenuItem();
        mnuCustomers = new javax.swing.JMenu();
        mniListCustomers = new javax.swing.JMenuItem();
        mniAddCustomer = new javax.swing.JMenuItem();
        mnuInventory = new javax.swing.JMenu();
        mniListInventories = new javax.swing.JMenuItem();
        mniAddInventory = new javax.swing.JMenuItem();
        mnuSales = new javax.swing.JMenu();
        mniListSales = new javax.swing.JMenuItem();
        mniAddSale = new javax.swing.JMenuItem();
        mnuStaff = new javax.swing.JMenu();
        mniListStaff = new javax.swing.JMenuItem();
        mniAddStaff = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jdpContainerLayout = new javax.swing.GroupLayout(jdpContainer);
        jdpContainer.setLayout(jdpContainerLayout);
        jdpContainerLayout.setHorizontalGroup(
            jdpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jdpContainerLayout.setVerticalGroup(
            jdpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );

        getContentPane().add(jdpContainer, java.awt.BorderLayout.CENTER);

        mnuFile.setText("File");

        mniLogIn.setText("Log In…");
        mniLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLogInActionPerformed(evt);
            }
        });
        mnuFile.add(mniLogIn);

        mniLogOut.setText("Log Out…");
        mniLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLogOutActionPerformed(evt);
            }
        });
        mnuFile.add(mniLogOut);

        jMenuBar1.add(mnuFile);

        mnuManage.setText("Manage");

        mnuCars.setText("Cars");

        mniListCars.setText("List…");
        mniListCars.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListCarsActionPerformed(evt);
            }
        });
        mnuCars.add(mniListCars);

        mniAddCar.setText("Add...");
        mniAddCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAddCarActionPerformed(evt);
            }
        });
        mnuCars.add(mniAddCar);

        mnuManage.add(mnuCars);

        mnuCustomers.setText("Customers");

        mniListCustomers.setText("List…");
        mniListCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListCustomersActionPerformed(evt);
            }
        });
        mnuCustomers.add(mniListCustomers);

        mniAddCustomer.setText("Add...");
        mniAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAddCustomerActionPerformed(evt);
            }
        });
        mnuCustomers.add(mniAddCustomer);

        mnuManage.add(mnuCustomers);

        mnuInventory.setText("Inventory");

        mniListInventories.setText("List…");
        mniListInventories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListInventoriesActionPerformed(evt);
            }
        });
        mnuInventory.add(mniListInventories);

        mniAddInventory.setText("Add...");
        mniAddInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAddInventoryActionPerformed(evt);
            }
        });
        mnuInventory.add(mniAddInventory);

        mnuManage.add(mnuInventory);

        mnuSales.setText("Sales");

        mniListSales.setText("List…");
        mniListSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListSalesActionPerformed(evt);
            }
        });
        mnuSales.add(mniListSales);

        mniAddSale.setText("Make Sale...");
        mniAddSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAddSaleActionPerformed(evt);
            }
        });
        mnuSales.add(mniAddSale);

        mnuManage.add(mnuSales);

        mnuStaff.setText("Staff");

        mniListStaff.setText("List…");
        mniListStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniListStaffActionPerformed(evt);
            }
        });
        mnuStaff.add(mniListStaff);

        mniAddStaff.setText("Add...");
        mniAddStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAddStaffActionPerformed(evt);
            }
        });
        mnuStaff.add(mniAddStaff);

        mnuManage.add(mnuStaff);

        jMenuBar1.add(mnuManage);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Called when the "List Cars" menu item is clicked
    private void mniListCarsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListCarsActionPerformed
        showForm("frmListCars", true);
    }//GEN-LAST:event_mniListCarsActionPerformed

    // Called when the "Log In" menu item is clicked
    private void mniLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLogInActionPerformed
        showForm("frmLogin", false);
    }//GEN-LAST:event_mniLogInActionPerformed

    // Called when the "Add Car" menu item is clicked
    private void mniAddCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAddCarActionPerformed
        showForm("frmAddCar", true);
    }//GEN-LAST:event_mniAddCarActionPerformed

    private void mniLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLogOutActionPerformed
        // Close all windows
        for (Map.Entry<String, JInternalFrame> formSet : forms.entrySet()) {
            formSet.getValue().dispose();
        }
        
        // Set salesperson to null
        GlobalData.sp = null;
    }//GEN-LAST:event_mniLogOutActionPerformed

    private void mniListCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListCustomersActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_mniListCustomersActionPerformed

    private void mniAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAddCustomerActionPerformed
       showForm("frmAddCustomer"); // TODO add your handling code here:
    }//GEN-LAST:event_mniAddCustomerActionPerformed

    private void mniListInventoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListInventoriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniListInventoriesActionPerformed

    private void mniAddInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAddInventoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniAddInventoryActionPerformed

    private void mniListSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListSalesActionPerformed
        showForm("frmListSales", true);
    }//GEN-LAST:event_mniListSalesActionPerformed

    private void mniAddSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAddSaleActionPerformed
        showForm("frmMakeSale", true);
    }//GEN-LAST:event_mniAddSaleActionPerformed

    private void mniListStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniListStaffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniListStaffActionPerformed

    private void mniAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAddStaffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mniAddStaffActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JDesktopPane jdpContainer;
    private javax.swing.JMenuItem mniAddCar;
    private javax.swing.JMenuItem mniAddCustomer;
    private javax.swing.JMenuItem mniAddInventory;
    private javax.swing.JMenuItem mniAddSale;
    private javax.swing.JMenuItem mniAddStaff;
    private javax.swing.JMenuItem mniListCars;
    private javax.swing.JMenuItem mniListCustomers;
    private javax.swing.JMenuItem mniListInventories;
    private javax.swing.JMenuItem mniListSales;
    private javax.swing.JMenuItem mniListStaff;
    private javax.swing.JMenuItem mniLogIn;
    private javax.swing.JMenuItem mniLogOut;
    private javax.swing.JMenu mnuCars;
    private javax.swing.JMenu mnuCustomers;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuInventory;
    private javax.swing.JMenu mnuManage;
    private javax.swing.JMenu mnuSales;
    private javax.swing.JMenu mnuStaff;
    // End of variables declaration//GEN-END:variables
}
