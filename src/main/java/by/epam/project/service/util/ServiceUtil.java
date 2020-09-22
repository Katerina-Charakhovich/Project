package by.epam.project.service.util;

import by.epam.project.command.manager.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceUtil {

    public static final Logger LOGGER = LogManager.getLogger();
    public static String hash(String password){
        String hashPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashPassword = no.toString(16);
            while (hashPassword.length() < 32) {
                hashPassword = "0" + hashPassword;
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, MessageManager.getProperty("message.wrongpassword"), e);
        }
        return hashPassword;
    }
}
