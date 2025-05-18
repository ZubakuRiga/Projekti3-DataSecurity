package crypto;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSAUtil {

    /**
     * Generates an RSA key pair (public and private keys).
     * @return KeyPair object or null if generation fails.
     */
    public static KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048); // Secure key size
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("RSA algorithm not available: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to generate RSA key pair: " + e.getMessage());
        }
        return null; // Return null to indicate failure
    }

    /**
     * Encrypts byte data using a given RSA public key.
     * @param data Raw byte array to encrypt.
     * @param key PublicKey used for encryption.
     * @return Base64-encoded encrypted string, or null on failure.
     */
    public static String encryptRSA(byte[] data, PublicKey key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data); // Perform encryption
            return Base64.getEncoder().encodeToString(encrypted); // Encode to Base64 for safe transport
        } catch (GeneralSecurityException e) {
            System.err.println("Encryption failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during encryption: " + e.getMessage());
        }
        return null; // Return null on failure
    }

    /**
     * Decrypts a Base64-encoded encrypted string using a private RSA key.
     * @param encrypted Base64-encoded string to decrypt.
     * @param key PrivateKey used for decryption.
     * @return Decrypted byte array, or null on failure.
     */
    public static byte[] decryptRSA(String encrypted, PrivateKey key) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encrypted); // Decode from Base64
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encryptedBytes); // Perform decryption
        } catch (GeneralSecurityException e) {
            System.err.println("Decryption failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Base64 decoding failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during decryption: " + e.getMessage());
        }
        return null; // Return null on failure
    }
}
