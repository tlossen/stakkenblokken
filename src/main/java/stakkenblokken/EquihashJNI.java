package stakkenblokken;

public class EquihashJNI {
    static {
        System.loadLibrary("equihash");
    }

    private native int[] find(int n, int k, int seed);

    private native boolean verify(int n, int k, int seed, int[] proof);

    public static void main(String[] args) {
        EquihashJNI equihash = new EquihashJNI();
        int[] proof = equihash.find(120, 5, 3);
        System.out.println(equihash.verify(120, 5, 3, proof));
    }
}
