package stakkenblokken;

import org.iq80.leveldb.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Importer {

    public static final int KB = 1024;

    public static void main(String[] args) throws IOException {

        File folder = new File("/tmp/blocks");
        FileUtils.deleteRecursively(folder);
        folder.mkdir();

        FileInputStream in = new FileInputStream("/Users/tlossen/Downloads/unity-4.6.1.dmg");
        try {
            int index = 0;
            while (in.available() > 0) {
                createFile(createPath(index++), in, Math.min(in.available(), 32 * KB));
            }
        } finally {
            in.close();
        }
    }

    private static String createPath(int index) {
        StringBuffer sb = new StringBuffer(String.format("%09d", index));
        sb.insert(6, '/');
        sb.insert(3, '/');
        return "/tmp/blocks/" + sb.toString();
    }

    private static void createFile(String path, FileInputStream in, int wanted) throws IOException {
        System.out.println("creating: " + path);
        new File(path).getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(path);
        try {
            byte[] buffer = new byte[4 * KB];
            while (wanted > 0) {
                int len = in.read(buffer, 0, Math.min(buffer.length, wanted));
                out.write(buffer, 0, len);
                wanted -= len;
            }
        } finally {
            out.close();
        }
    }
}
