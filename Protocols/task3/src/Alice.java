import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class Alice {

    private final BigInteger[] s;
    private final BigInteger[] v;
    private final int k;
    private final BigInteger n;

    public Alice(Parameters parameters, String fileName) throws FileNotFoundException {
        k = parameters.getK();
        n = parameters.getN();
        this.s = new BigInteger[k];
        this.v = new BigInteger[k];
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            s[i] = Main.randomNum(n);
            boolean b = random.nextBoolean();
            v[i] = s[i].multiply(s[i]).mod(n).modInverse(n);
            if (b) {
                v[i] = v[i].negate().mod(n);
            }
        }
        String vString = (new Gson()).toJson(v);
        PrintWriter pw = new PrintWriter(fileName);
        pw.println(vString);
        pw.close();
    }

    public SignedMessage generateSign(String m) {
        BigInteger r = Main.randomNum(n);
        BigInteger x = r.multiply(r).mod(n);
        BigInteger h = Main.hash(m, x);
        boolean[] e = new boolean[k];
        BigInteger y = r;
        for (int i = 0; i < k; i++) {
            if (h.testBit(i)) {
                e[i] = true;
                y = y.multiply(s[i]).mod(n);
            }
            else {
                e[i] = false;
            }
        }
        return new SignedMessage(m, e, y);
    }

}
