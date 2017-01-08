package stakkenblokken;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AES {

    private static final String CIPHER_AES = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, UnsupportedEncodingException {
        SecretKey key = generateKey();
        Util.printHex("key", key.getEncoded());
        byte[] encrypted = encrypt(key, "ene mene miste, überflüssige umlaute kønnen alles kaputt machen");
        Util.printHex("encrypted", encrypted);
        String decrypted = decrypt(key, encrypted);
        Util.printString("decrypted", decrypted);
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

}
