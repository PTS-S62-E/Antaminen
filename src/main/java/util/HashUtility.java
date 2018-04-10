package util;

import io.sentry.Sentry;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtility {
    private static final String salt = "PTS6-S62-E";

    public static String hash(String textToHash) {
        String saltWithText = salt + textToHash;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Sentry.capture(e);
            e.printStackTrace();
        }
        byte[] hash = digest.digest(saltWithText.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);

        return encoded;
    }
}
