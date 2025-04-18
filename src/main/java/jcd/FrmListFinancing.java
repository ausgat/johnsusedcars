package jcd;

import bo.Financing;
import dao.FinancingHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Swing form for the financing list
 */
public class FrmListFinancing extends javax.swing.JInternalFrame {

    FinancingHandler fh = new FinancingHandler();
    
    /** Creates new form FrmListFinancing */
    public FrmListFinancing() {
        initComponents();
        populateFinancing();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblFinancing = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnReload = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnDelete = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        btnEdit = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Loans");

        tblFinancing.setAutoCreateRowSorter(true);
        tblFinancing.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblFinancing.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblFinancing);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.setPreferredSize(new java.awt.Dimension(576, 34));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));
        jPanel5.add(filler4);

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });
        jPanel5.add(btnReload);
        jPanel5.add(filler1);

        btnDelete.setText("Delete...");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel5.add(btnDelete);
        jPanel5.add(filler2);

        btnEdit.setText("Edit...");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel5.add(btnEdit);
        jPanel5.add(filler3);

        getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        populateFinancing();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // Get the index of the selected row
        /*int selectedRow = tblCars.getSelectedRow();

        // If a row is actually selected (that is, not -1)
        if (selectedRow != -1) {

            // Get the VIN number from the first column (0) of the selected row
            int vin = (int)tblCars.getValueAt(selectedRow, 0);

            Sale sale = sh.findSale(id);

            if (sale != null) {
                DlgUpdateCar dlg = new DlgUpdateCar(null, true);
                dlg.setCar(car);
                dlg.setVisible(true);
                if (dlg.getReturnStatus() == DlgUpdateCar.RET_OK) {
                    this.populateCars();
                }
            }
        }*/
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Get the index of the selected row
        int selectedRow = tblFinancing.getSelectedRow();

        // If a row is actually selected (that is, not -1)
        if (selectedRow != -1) {

            // Get the customer ID from the first column (0) and the sale ID
            // from the second column (1) of the selected row
            int cid = (int)tblFinancing.getValueAt(selectedRow, 0);
            int sid = (int)tblFinancing.getValueAt(selectedRow, 1);

            // Show a dialog and put the result in res
            int res = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the financing?");

            // If the user clicked OK
            if (res == JOptionPane.OK_OPTION) {
                fh.deleteFinancing(cid, sid);

                // Reload the list
                populateFinancing();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Please select a financing to delete.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * Fill the sale table (tblFinancing) with all the Financing relations in
     * the database
     */
    public void populateFinancing() {

        List<Financing> fins = fh.getFinancings();

        String[] columns = new String[] { "Customer ID", "Sale ID",
            "Interest Rate", "Monthly Payment" };
        DefaultTableModel tblModel = new DefaultTableModel(columns, 0) {
            // Make sure none of the cells is editable
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        fins.forEach((f)->{
            tblModel.addRow(f.getRow());
        });
        tblFinancing.setModel(tblModel);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblFinancing;
    // End of variables declaration//GEN-END:variables

}
