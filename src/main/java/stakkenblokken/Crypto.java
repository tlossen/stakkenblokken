package stakkenblokken;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    private static final String CIPHER_AES = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException {
        SecretKey key = generateKey();
        printHex("key", key.getEncoded());
        byte[] encrypted = encrypt(key, "ene mene miste, überflüssige umlaute kønnen alles kaputt machen");
        printHex("encrypted", encrypted);
        String decrypted = decrypt(key, encrypted);
        System.out.println("decrypted: (" + decrypted.length() + ") " + decrypted);
    }

    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(128);
        return keygen.generateKey();
    }

    private static byte[] encrypt(SecretKey key, String text) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(CIPHER_AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(text.getBytes("UTF-8"));
    }

    private static String decrypt(SecretKey key, byte[] bytes) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(CIPHER_AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(bytes), "UTF-8");
    }

    private static void printHex(String label, byte[] bytes) {
        String hex = toHex(bytes);
        System.out.println(label + ": (" + bytes.length + ") " + hex);
    }

    private static String toHex(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        final StringBuilder result = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            result.append(HEX.charAt((b & 0xF0) >> 4)).append(HEX.charAt((b & 0x0F)));
        }
        return result.toString();
    }

}
