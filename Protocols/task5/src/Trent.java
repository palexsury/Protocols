import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class Trent {

    private static final Random random = new Random();

    public static void generateSecretShare(int k, int n, BigInteger secret, String fileName) throws FileNotFoundException {

        int bitLength = secret.bitLength() + 1;
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        while (p.compareTo(secret) <= 0 || !p.isProbablePrime(100)) {
            p = BigInteger.probablePrime(bitLength, random);
        }
        BigInteger[] point = new BigInteger[k];
        point[0] = secret;

        for (int i = 1; i < k; i++) {
            point[i] = Main.randomNum(p);
        }
        PrintWriter pw = new PrintWriter(fileName);
        pw.println(p);
        for (int i = 0; i < n; i++) {
            BigInteger[] coefficients = new BigInteger[k];
            BigInteger d = BigInteger.ZERO;
            for (int j = 0; j < k; j++) {
                coefficients[j] = Main.randomNum(p);
                d = d.add(coefficients[j].multiply(point[j])).mod(p);
            }
            StringBuilder secretShare = new StringBuilder();
            for (BigInteger coefficient : coefficients) {
                secretShare.append(coefficient.toString()).append(" ");
            }
            secretShare.append(d);
            pw.println(secretShare);
        }
        pw.close();
    }
}