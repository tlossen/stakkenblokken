package stakkenblokken;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;


public class RSA {

    private static final String CIPHER_RSA = "RSA/ECB/PKCS1Padding";

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        KeyPair key = generateKey(2048);
        Util.printHex("public key", key.getPublic().getEncoded());
        Util.printHex("private key", key.getPrivate().getEncoded());
        byte[] encrypted = encrypt(key.getPublic(), "ene mene miste, überflüssige umlaute kønnen alles kaputt machen");
        Util.printHex("encrypted", encrypted);
        String decrypted = decrypt(key.getPrivate(), encrypted);
        Util.printString("decrypted", decrypted);
    }

    private static KeyPair generateKey(int keyLength) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] encrypt(PublicKey publicKey, String text) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(text.getBytes("UTF-8"));
    }

    public static String decrypt(PrivateKey privateKey, byte[] bytes) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(bytes), "UTF-8");
    }

}



