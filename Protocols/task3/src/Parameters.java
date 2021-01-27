
import com.google.gson.Gson;
import java.math.BigInteger;
import java.util.Random;

public class Parameters {

    private final BigInteger n;
    private final int k;

    public Parameters(int pLength, int qLength, int k) {
        this.k = k;
        BigInteger q;
        BigInteger p;
        Random random = new Random();
        do {
            p = BigInteger.probablePrime(pLength, random);
            q = BigInteger.probablePrime(qLength, random);
        } while (!q.isProbablePrime(100) && !p.isProbablePrime(100));
        n = p.multiply(q);
    }

    public String toString() {
        return (new Gson()).toJson(this);
    }

    public int getK() {
        return k;
    }

    public BigInteger getN() {
        return n;
    }

}
