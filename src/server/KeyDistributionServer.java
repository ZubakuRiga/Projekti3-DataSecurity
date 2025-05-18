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
        System.out.println("Duke startuar Serverin per Shperndarjen e Celesit...");

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Duke degjuar ne portin 5000...");

            clientSocket = serverSocket.accept();
            System.out.println("Klienti u lidh.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Step 1: Marrja e çelesit publik nga klienti
            String base64ClientPublicKey = in.readLine();
            if (base64ClientPublicKey == null || base64ClientPublicKey.isBlank()) {
                System.err.println("Nuk u pranua asnje çeles publik RSA nga klienti.");
                return;
            }

            PublicKey clientPublicKey;
            try {
                byte[] publicKeyBytes = Base64.getDecoder().decode(base64ClientPublicKey);
                clientPublicKey = KeyFactory.getInstance("RSA")
                        .generatePublic(new X509EncodedKeySpec(publicKeyBytes));
                System.out.println("Çelesi publik RSA i klientit u lexua me sukses.");
            } catch (Exception e) {
                System.err.println("Deshtoi leximi i çelesit publik te klientit: " + e.getMessage());
                return;
            }

            // Step 2: Gjenerimi i çelesit nga serveri
            SecretKey aesKey = AESutil.generateAESKey();
            if (aesKey == null) {
                System.err.println("Deshtoi gjenerimi i çelesit AES.");
                return;
            }

            // Step 3: Enkriptimi i çelesit AES me çelesin publik te klientit
            String encryptedKey = RSAUtil.encryptRSA(aesKey.getEncoded(), clientPublicKey);
            if (encryptedKey == null) {
                System.err.println("Deshtoi enkriptimi i çelesit AES me çelesin publik RSA te klientit.");
                return;
            }

            // Step 4: Dergimi i çelesit te enkriptuar tek klienti
            out.println(encryptedKey);
            System.out.println("Çelesi simetrik i enkriptuar u dergua te klienti.");

            // Step 5: Marrja e mesazhit nga klienti
            String encryptedMessage = in.readLine();
            if (encryptedMessage == null || encryptedMessage.isBlank()) {
                System.err.println("Nuk u pranua asnje mesazh i enkriptuar nga klienti.");
                return;
            }

            // Step 6: Dekriptimi i mesazhit
            String decryptedMessage = AESutil.decryptAES(encryptedMessage, aesKey);
            if (decryptedMessage == null) {
                System.err.println("Deshtoi dekriptimi i mesazhit nga klienti.");
                return;
            }

            // Step 7: Shfaqja e mesazhit
            System.out.println("Mesazhi u dekriptua me sukses:");
            System.out.println("> " + decryptedMessage);

        } catch (IOException e) {
            System.err.println("Gabim I/O: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Gabim i papritur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                if (clientSocket != null)
                    clientSocket.close();
                if (serverSocket != null)
                    serverSocket.close();
                System.out.println("Serveri u mbyll me sukses.");
            } catch (IOException e) {
                System.err.println("Gabim gjate mbylljes se resurseve te serverit: " + e.getMessage());
            }
        }
    }
}
