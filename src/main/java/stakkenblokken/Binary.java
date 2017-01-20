package stakkenblokken;


import java.security.SecureRandom;
import java.util.Arrays;

public class Binary {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String HEX_CHARS = "0123456789abcdef";

    public static Binary createRandom(int size) {
        byte[] result = new byte[size];
        RANDOM.nextBytes(result);
        return new Binary(result);
    }

    public final byte[] bytes;
    public final int size;

    public Binary(byte[] bytes) {
        this.bytes = bytes;
        this.size = bytes.length;
    }

    public boolean bit(int index) {
        int bite = index / 8;
        if (bite > size - 1) throw new IllegalArgumentException("index is out of bounds");
        int bit = index % 8;
        int mask = 0x80 >>> bit;
        return (bytes[bite] & mask) == mask;
    }

    public int getLeadingZeroBits() {
        int result = 0;
        for (int i=0; i<size*8; i++) {
            if (bit(i)) break;
            result++;
        }
        return result;
    }

    public Binary xor(Binary other) {
        if (size != other.size) throw new IllegalArgumentException("binaries have different size");
        byte[] result = new byte[size];
        for (int i=0; i<size; i++) {
            result[i] = (byte) (bytes[i] ^ other.bytes[i]);
        }
        return new Binary(result);
    }

    public Binary rotate(int numGroups) {
        if (size % numGroups > 0) throw new IllegalArgumentException("not divisible into given number of groups");
        int groupSize = size / numGroups;
        byte[] result = new byte[size];
        System.arraycopy(bytes, 0, result, size - groupSize, groupSize);
        System.arraycopy(bytes, groupSize, result, 0, size - groupSize);
        return new Binary(result);
    }

    public String toHex() {
        final StringBuilder result = new StringBuilder(2 * size);
        for (byte b : bytes) {
            result.append(HEX_CHARS.charAt((b & 0xF0) >> 4)).append(HEX_CHARS.charAt((b & 0x0F)));
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "Binary(" + size + ", " + toHex() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Binary binary = (Binary) o;
        return Arrays.equals(bytes, binary.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    public static void main(String[] args) {
        Binary b1 = Binary.createRandom(20);
        System.out.println(b1.toHex());

        Binary b2 = Binary.createRandom(20);
        System.out.println(b2.toHex());

        Binary b3 = b1.xor(b2);
        System.out.println(b3.toHex());

        Binary rot = b3;
        for (int i =0; i<5; i++) {
            rot = rot.rotate(5);
            System.out.println(rot.toHex());
        }

        System.out.println("rot == b3 is " + rot.equals(b3));
    }

}
