package stakkenblokken;

import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;
import java.io.IOException;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;


public class Storage {

    public static void main(String[] args) throws IOException {
        store();
        load();
        status();
    }

    private static void status() throws IOException {
        DB db = factory.open(new File("data"), new Options());
        System.out.println(db.getProperty("leveldb.stats"));
    }

    private static void load() throws IOException {
        DB db = factory.open(new File("data"), new Options());
        try {
            byte[] key = bytes("hello_23");
            Binary value = new Binary(db.get(key));
            System.out.println(value);
        } finally {
            db.close();
        }
    }

    private static void store() throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        options.compressionType(CompressionType.NONE);
        DB db = factory.open(new File("data"), options);
        try {
            for (int i = 0; i < 100; i++) {
                byte[] key = bytes("hello_" + i);
                Binary value = Binary.createRandom(16 * 1024);
                db.put(key, value.bytes);
                System.out.println(i);
                if (i == 23) System.out.println(value);
            }
        } finally {
            db.close();
        }
    }



}
