package by.epam.project.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final String ALGORITHM_FOR_HASH = "SHA-512";
    private static final int MAX_LENGTH_PASSWORD = 32;
    private static final int DEFAULT_LENGTH_HASH_PASSWORD = 16;
    private static final String SALT = "0";

    private HashPassword() {
    }

    /**
     * Algorithm for hash password
     */

    public static String hash(String password) {
        String hashPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_FOR_HASH);
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashPassword = no.toString(DEFAULT_LENGTH_HASH_PASSWORD);
            while (hashPassword.length() < MAX_LENGTH_PASSWORD) {
                hashPassword = SALT.concat(hashPassword);
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "Algorithm not found", e);
        }
        return hashPassword;
    }
}
