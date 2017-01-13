package stakkenblokken;

import org.bouncycastle.crypto.generators.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;


public class ProofOfWork {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        measureSha3ProofOfWork();
    }

    // 11 -> 62 ms
    // 12 -> 130 ms
    // 13 -> 249 ms
    private static void measureSha3ProofOfWork() {
        Binary data = Binary.createRandom(256);
        List results = new ArrayList<Long>();
        for (int i = 1; i < 100; i++) {
            long before = System.currentTimeMillis();
            sha3ProofOfWork(data, 13);
            long time = System.currentTimeMillis() - before;
            System.out.println(time);
            results.add(time);
        }
        System.out.println(average(results));
    }

    private static Binary bcryptProofOfWork(Binary data, int difficulty) {
        DigestSHA3 sha3 = new DigestSHA3(512);
        sha3.update(data.bytes);
        while (true) {
            Binary nonce = Binary.createRandom(16);
            Binary hash = new Binary(BCrypt.generate(sha3.digest(), nonce.bytes, 10));
            if (hash.getLeadingZeroBits() >= difficulty) return nonce;
        }
    }

    private static Binary sha3ProofOfWork(Binary data, int difficulty) {
        while (true) {
            Binary nonce = Binary.createRandom(16);
            DigestSHA3 sha3 = new DigestSHA3(512);
            sha3.update(nonce.bytes);
            sha3.update(data.bytes);
            Binary hash = new Binary(sha3.digest());
            if (hash.getLeadingZeroBits() >= difficulty) return nonce;
        }
    }

    // 15 -> 79
    // 16 -> 140, 116
    // 17 -> 315
    private static float measureLeadingZeroBits() {
        List results = new ArrayList<Long>();
        for (int i = 1; i < 100; i++) {
            long before = System.currentTimeMillis();
            createRandomWithLeadingZeroBits(16);
            long time = System.currentTimeMillis() - before;
            System.out.println(time);
            results.add(time);
        }
        return average(results);
    }

    private static Binary createRandomWithLeadingZeroBits(int min) {
        while (true) {
            Binary b = Binary.createRandom(16);
            if (b.getLeadingZeroBits() >= min) return b;
        }
    }

    private static long average(List<Long> values) {
        long total = 0;
        for (Long value : values) total += value;
        return (long) (total / values.size());
    }

    // 10 -> 83
    // 11 -> 166
    // 12 -> 318,
    private static void measureBcrypt() {
        for (int i = 6; i < 20; i++) {
            long before = System.currentTimeMillis();
            Binary hash = new Binary(BCrypt.generate(
                    Binary.createRandom(64).bytes,
                    Binary.createRandom(16).bytes, i));
            long time = System.currentTimeMillis() - before;
            System.out.println(i + ", " + time + ", " + hash);
        }
    }


}
