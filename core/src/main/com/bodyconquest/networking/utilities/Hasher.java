package main.com.bodyconquest.networking.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public Hasher() {
    }

    public String hash(String message) {
        String sha3_256hex = "";

        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            final byte[] hashbytes = digest.digest(
                    message.getBytes(StandardCharsets.UTF_8));
            sha3_256hex = bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }

        return sha3_256hex;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
