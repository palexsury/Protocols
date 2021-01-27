import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static java.math.BigInteger.ZERO;

public class Main {

    private static final String fileName = "Secret.txt";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("1 - генерация секрета | 2 - восстановление секрета");
        Scanner s = new Scanner(System.in);
        int mode = s.nextInt();
        System.out.println("------------------------------");
        if (mode == 1) {
            System.out.print("n = ");
            int n = s.nextInt();
            System.out.print("k = ");
            int k = s.nextInt();
            System.out.print("Секрет = ");
            BigInteger secret = new BigInteger(s.next());
            Trent.generateSecretShare(k, n, secret ,fileName);
            System.out.println("Секрет разделен");
        }
        if (mode == 2) {
            System.out.println(Restorer.calc(fileName));
        }
    }

    public static BigInteger randomNum(BigInteger p) {
        Random random = new Random();
        BigInteger result = new BigInteger(p.bitLength(), random);
        while (result.compareTo(p) >= 0 || result.compareTo(ZERO) <= 0) {
            result = new BigInteger(p.bitCount(), random);
        }
        return result;
    }
}