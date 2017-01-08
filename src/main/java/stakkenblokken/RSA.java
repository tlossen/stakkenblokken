package stakkenblokken;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSA {

    private static final String CIPHER_RSA = "RSA/ECB/PKCS1Padding";

    public static void main(String[] args) throws Exception {
        KeyPair key = generateKey(2048);

        Util.printHex("public key", key.getPublic().getEncoded());
        Path publicPath = FileSystems.getDefault().getPath("/tmp/public_key");
        Files.write(publicPath, key.getPublic().getEncoded());

        Util.printHex("private key", key.getPrivate().getEncoded());
        Path privatePath = FileSystems.getDefault().getPath("/tmp/private_key");
        Files.write(privatePath, key.getPrivate().getEncoded());

        PublicKey publicKey = readPublicKey(publicPath);
        byte[] encrypted = encrypt(publicKey, "ene mene miste, überflüssige umlaute kønnen alles kaputt machen");
        Util.printHex("encrypted", encrypted);

        PrivateKey privateKey = readPrivateKey(privatePath);
        String decrypted = decrypt(privateKey, encrypted);
        Util.printString("decrypted", decrypted);
    }

    private static KeyPair generateKey(int keyLength) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    private static PrivateKey readPrivateKey(Path path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Files.readAllBytes(path);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    private static PublicKey readPublicKey(Path path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Files.readAllBytes(path);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    private static byte[] encrypt(PublicKey publicKey, String text) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(text.getBytes("UTF-8"));
    }

    private static String decrypt(PrivateKey privateKey, byte[] bytes) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(CIPHER_RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(bytes), "UTF-8");
    }

}



