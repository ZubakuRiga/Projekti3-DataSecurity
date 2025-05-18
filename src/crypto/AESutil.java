package crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESutil {

    // Kjo metodë gjeneron një çelës AES të tipit 128-bit.
    public static SecretKey generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // AES 128-bit
            return keyGen.generateKey();
        } catch (Exception e) {
            System.err.println("Gabim gjatë gjenerimit të çelësit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metodë enkripton një tekst duke përdorur çelësin AES dhe e kthen të enkriptuar në formatin Base64.
    public static String encryptAES(String data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Gabim gjatë enkriptimit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metodë dekripton një tekst të enkriptuar në Base64 dhe e kthen në tekst të qartë duke përdorur çelësin AES.
    public static String decryptAES(String encrypted, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.err.println("Gabim gjatë dekriptimit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metodë konverton një çelës AES të koduar në Base64 në objektin SecretKey.
    public static SecretKey decodeAESKey(String encodedKey) {
        byte[] decoded = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decoded, 0, decoded.length, "AES");
    }
}
