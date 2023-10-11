import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.LinkedList;

public class FileEncryptionDecryption {

    public static void main(String[] args) {
        LinkedList<Byte> encryptedData = new LinkedList<>();
        File inputFile = new File("input.txt");
        File encryptedFile = new File("encrypted.txt");
        File decryptedFile = new File("decrypted.txt");

        try {
            String key = "YourSecretKey"; // Change this to your secret key
            // Encrypt the file and store it in the data structure
            encryptFile(key, inputFile, encryptedData);

            // Decrypt the data from the data structure and save it to a file
            decryptFile(key, encryptedData, decryptedFile);

            System.out.println("File encryption and decryption completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void encryptFile(String key, File inputFile, LinkedList<Byte> encryptedData) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) inputFile.length()];
        inputStream.read(inputBytes);

        byte[] encryptedBytes = cipher.doFinal(inputBytes);

        for (byte b : encryptedBytes) {
            encryptedData.add(b);
        }

        inputStream.close();
    }

    public static void decryptFile(String key, LinkedList<Byte> encryptedData, File outputFile) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] encryptedBytes = new byte[encryptedData.size()];
        for (int i = 0; i < encryptedBytes.length; i++) {
            encryptedBytes[i] = encryptedData.get(i);
        }

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(decryptedBytes);

        outputStream.close();
    }
}
