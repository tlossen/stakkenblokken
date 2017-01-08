package stakkenblokken;

public class Util {

    public static void printString(String label, String text) {
        System.out.println("---------------------------------------------------");
        System.out.println(label + ": (" + text.length() + ") " + text);
    }

    public static void printHex(String label, byte[] bytes) {
        String hex = toHex(bytes);
        System.out.println("---------------------------------------------------");
        System.out.println(label + ": (" + bytes.length + ") " + hex);
    }

    public static String toHex(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        final StringBuilder result = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            result.append(HEX.charAt((b & 0xF0) >> 4)).append(HEX.charAt((b & 0x0F)));
        }
        return result.toString();
    }


}
