import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.Arrays;

public class Bob {

    private final SignedMessage SM;
    private final Parameters parameters;
    private final BigInteger[] v;

    public Bob(Parameters parameters, SignedMessage SM, BigInteger[] v) {
        this.parameters = parameters;
        this.SM = SM;
        this.v = v;
    }

    public boolean verifySign() {
        BigInteger y = SM.getY();
        BigInteger n = parameters.getN();
        boolean[] e = SM.getE();
        int k = parameters.getK();
        BigInteger z = y.multiply(y);
        for (int i = 0; i < k; i++) {
            if (e[i]) {
                z = z.multiply(v[i]).mod(n);
            }
        }
        String m = SM.getM();
        BigInteger h1 = Main.hash(m, z);
        BigInteger h2 = Main.hash(m, n.subtract(z));
        boolean[] e1 = new boolean[k];
        boolean[] e2 = new boolean[k];
        for (int i = 0; i < k; i++) {
            e1[i] = h1.testBit(i);
            e2[i] = h2.testBit(i);
        }
        return Arrays.equals(e, e1) || Arrays.equals(e, e2);
    }


}
