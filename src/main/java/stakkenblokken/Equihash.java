package stakkenblokken;

import org.bouncycastle.crypto.digests.Blake2bDigest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Equihash {

    private static final int[] SOLUTION = new int[] {
        0x47e0c,  0x52478,  0x1afd2,  0x1e6c5c,
        0xd27b8,  0x1ce199,  0x1ac442,  0x1e663c,
        0xe3081,  0xe3a5e,  0x8cfea,  0x1ef496,
        0xa4268,  0x104c14,  0x1b987e,  0x1bf135,
        0x85696,  0xfd07d,  0x8938d,  0x18961c,
        0x12f68,  0xbb139,  0x1980dc,  0x1a5928,
        0x1b23c8,  0x1cbca9,  0x39831,  0x15955c,
        0x72ac1,  0x1a5ed1,  0x18887,  0x1cf8c8
    };

    private static final int SEED = 3;
    private static final int NONCE = 2;

    public static void main(String[] args) throws IOException {
        List<Binary> parts = new ArrayList(32);

        for (int i : SOLUTION) {
            byte[] in = ByteBuffer.allocate(6 * 4)
                    .putInt(SEED).putInt(SEED)
                    .putInt(SEED).putInt(SEED)
                    .putInt(NONCE).putInt(i)
                    .array();
            if (parts.isEmpty()) {
                FileOutputStream file = new FileOutputStream(new File("./47e0c.bin"));
                file.write(in);
                file.close();
            }
            System.out.println(new Binary(in));
            byte[] out = new byte[32];
            Blake2bDigest blake = new Blake2bDigest(null, out.length, null, null);
            blake.update(in, 0, in.length);
            blake.doFinal(out, 0);
            Binary part = new Binary(out);
            System.out.println(part);
            parts.add(part);
        }

//        System.out.println();
//        for (int i = 0; i < 32; i=i+2) {
//            System.out.println(parts.get(i).xor(parts.get(i+1)));
//        }
    }

}
