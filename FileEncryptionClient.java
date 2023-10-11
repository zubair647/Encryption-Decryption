import java.io.*;
import java.net.Socket;

public class FileEncryptionClient {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change to the server's IP or hostname
        int serverPort = 12345; // Change to the server's port

        try (Socket clientSocket = new Socket(serverAddress, serverPort)) {
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            // Request encryption
            outputStream.writeUTF("ENCRYPT");

            // Receive the length of the encrypted data
            int encryptedLength = inputStream.readInt();

            // Read the encrypted data
            byte[] encryptedBytes = new byte[encryptedLength];
            inputStream.readFully(encryptedBytes);

            // Handle the encrypted data here
            // You can save it to a local file or process it as needed.

            // To request decryption, you can uncomment the following lines:
            // outputStream.writeUTF("DECRYPT");
            // Handle the decrypted file received from the server

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
