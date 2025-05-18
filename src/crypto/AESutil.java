package crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESutil {

    // Kjo metode gjeneron nje çeles AES te tipit 128-bit.
    public static SecretKey generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // AES 128-bit
            return keyGen.generateKey();
        } catch (Exception e) {
            System.err.println("Gabim gjate gjenerimit te çelesit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metode enkripton nje tekst duke perdorur çelesin AES dhe e kthen te
    // enkriptuar ne formatin Base64.
    public static String encryptAES(String data, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Gabim gjate enkriptimit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metode dekripton nje tekst te enkriptuar ne Base64 dhe e kthen ne tekst
    // te qarte duke perdorur çelesin AES.
    public static String decryptAES(String encrypted, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encrypted);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.err.println("Gabim gjate dekriptimit AES: " + e.getMessage());
            return null;
        }
    }

    // Kjo metode konverton nje çeles AES te koduar ne Base64 ne objektin SecretKey.
    public static SecretKey decodeAESKey(String encodedKey) {
        byte[] decoded = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decoded, 0, decoded.length, "AES");
    }
}
