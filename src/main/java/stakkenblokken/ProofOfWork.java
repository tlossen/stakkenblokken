package stakkenblokken;

import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

import java.security.NoSuchAlgorithmException;


public class ProofOfWork {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        DigestSHA3 md = new DigestSHA3(512);
        md.update(Binary.createRandom(20).bytes);
        System.out.println(new Binary(md.digest()).toHex());


//        String hashed = BCrypt.hashpw("hello", BCrypt.gensalt(12));

    }

//    public static byte[] findNonce(byte[] data) {
//
//    }

}
