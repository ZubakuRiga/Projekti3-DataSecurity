package crypto;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSAUtil {

    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public static String encryptRSA(byte[] data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    public static byte[] decryptRSA(String encrypted, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(Base64.getDecoder().decode(encrypted));
    }

}