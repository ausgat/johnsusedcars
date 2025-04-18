package utils;

import bo.Salesperson;
import javax.swing.JLabel;

/**
 *
 * Global variables accessible from anywhere in the application
 * 
 */
public class GlobalData {
    
    /**
     * The currently logged-in salesperson
     */
    public static Salesperson salesperson = null;

    public static Salesperson getSalesperson() {
        return salesperson;
    }

    /**
     * Set the currently logged-in salesperson. This also updates the main form
     * login label if it has been set.
     * @param salesperson Salesperson to log in
     */
    public static void setSalesperson(Salesperson salesperson) {
        GlobalData.salesperson = salesperson;
        
        if (loginLabel != null) {
            if (GlobalData.salesperson != null)
                loginLabel.setText(String.format("Logged in: %s",
                        salesperson.getName()));
            else
                loginLabel.setText("Not logged in");
        }
    }

    /**
     * Main form label that provides login status
     */
    private static JLabel loginLabel = null;

    /**
     * Set the global login label
     * @param loginLabel
     */
    public static void setLoginLabel(JLabel loginLabel) {
        GlobalData.loginLabel = loginLabel;
    }
}
