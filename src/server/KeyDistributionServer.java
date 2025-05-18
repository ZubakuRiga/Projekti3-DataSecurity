package server;

import crypto.RSAUtil;
import crypto.AESutil;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.Base64;

public class KeyDistributionServer {

    public static void main(String[] args) {
        System.out.println("Starting Key Distribution Server...");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Listening on port 5000...");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 1: Marrja e celesit publik nga klienti
            String base64ClientPublicKey = in.readLine();
            if (base64ClientPublicKey == null || base64ClientPublicKey.isBlank()) {
                System.err.println("No RSA public key received from client.");
                return;
            }

            PublicKey clientPublicKey;
            try {
                byte[] publicKeyBytes = Base64.getDecoder().decode(base64ClientPublicKey);
                clientPublicKey = KeyFactory.getInstance("RSA")
                        .generatePublic(new X509EncodedKeySpec(publicKeyBytes));
                System.out.println("Client's RSA public key parsed successfully.");
            } catch (Exception e) {
                System.err.println("Failed to parse client's public key: " + e.getMessage());
                return;
            }

            // Step 2: Gjenerimi i celesit nga serveri
            SecretKey aesKey = AESutil.generateAESKey();
            if (aesKey == null) {
                System.err.println("Failed to generate AES key.");
                return;
            }

            // Step 3: Enkriptimi i celesit AES me celesin publik te klientit
            String encryptedKey = RSAUtil.encryptRSA(aesKey.getEncoded(), clientPublicKey);
            if (encryptedKey == null) {
                System.err.println("Failed to encrypt AES key with client's public RSA key.");
                return;
            }

            // Step 4: Dergimi i celesit te enkriptuar tek klienti
            out.println(encryptedKey);
            System.out.println("Encrypted symmetric key sent to client.");

            // Step 5: Marrja e mesazhit nga klienti
            String encryptedMessage = in.readLine();
            if (encryptedMessage == null || encryptedMessage.isBlank()) {
                System.err.println("No encrypted message received from client.");
                return;
            }

            // Step 6: Dekriptimi i mesazhit
            String decryptedMessage = AESutil.decryptAES(encryptedMessage, aesKey);
            if (decryptedMessage == null) {
                System.err.println("Failed to decrypt message from client.");
                return;
            }

            // Step 7: Shfaqja e mesazhit
            System.out.println("Message decrypted successfully:");
            System.out.println("> " + decryptedMessage);

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
                System.out.println("Server shutdown completed.");
            } catch (IOException e) {
                System.err.println("Error closing server resources: " + e.getMessage());
            }
        }
    }
}