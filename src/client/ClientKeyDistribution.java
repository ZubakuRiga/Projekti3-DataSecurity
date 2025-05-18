package client;

import crypto.AESutil;
import crypto.RSAUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Base64;
import java.util.Scanner;

public class ClientKeyDistribution {
    public static void main(String[] args) {
        System.out.println("Mire se vini te Shperndarja e Çelesit Simetrik");

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Scanner scanner = null;

        try {
            // Gjeneron key pair
            KeyPair keyPair = RSAUtil.generateRSAKeyPair();
            if (keyPair == null) {
                System.err.println("Gjenerimi i RSA key pair deshtoj!");
                return;
            }

            String base64PublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            // Lidhja me serverin
            try {
                socket = new Socket("localhost", 5000);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("U krijua lidhja me serverin per Key Distributin!");
            } catch (IOException e) {
                System.err.println("Lidhja me server deshtoj: " + e.getMessage());
                return;
            }

            // Dergimi i RSA public key tek serveri
            out.println(base64PublicKey);
            System.out.println("RSA public key u dergua te serveri!");

            // Pranimi dhe dekriptimi i RSA key
            String encryptedKey = in.readLine();
            if (encryptedKey == null) {
                System.err.println("Nuk u pranua asnje cels nga serveri!");
                return;
            }

            byte[] decryptedKeyBytes = RSAUtil.decryptRSA(encryptedKey, keyPair.getPrivate());
            if (decryptedKeyBytes == null) {
                System.err.println("Dekriptimi celsit deshtoj!");
                return;
            }

            SecretKey aesKey = new SecretKeySpec(decryptedKeyBytes, 0, decryptedKeyBytes.length, "AES");
            System.out.println("Symmetric key u pranua dhe u dekriptua me RSA!");

            // Mesazhi qe do te enkriptohet
            scanner = new Scanner(System.in);
            System.out.print("Shkruani mesazhin qe do te enkriptohet\n> ");
            String message = scanner.nextLine();

            // Enkripto me AES key
            String encryptedMessage = AESutil.encryptAES(message, aesKey);
            if (encryptedMessage == null) {
                System.err.println("Enkriptimi i mesazhit deshtoj!");
                return;
            }

            out.println(encryptedMessage);
            System.out.println("mesazhi u enkriptua me cels simetrik dhe u dergua ne server");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (scanner != null)
                    scanner.close();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

    }
}
