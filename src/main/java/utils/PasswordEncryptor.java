package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
/**
*
* @author breyo
*/
public class PasswordEncryptor {
    public static String encryptPassword(String password){
    try{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] cBytes = md.digest(password.getBytes());
        password = Base64.getEncoder().encodeToString(cBytes);
    } catch(NoSuchAlgorithmException ex){
        Logger.getLogger(PasswordEncryptor.class.getName()).log(Level.SEVERE,null,ex);
    }
    return password;

    }
}