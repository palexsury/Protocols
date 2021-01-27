import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Alice {

    static int bitLength = 12;
    static BigInteger j = new BigInteger("Личная информация Алисы".getBytes());

    BigInteger N;
    BigInteger e;
    BigInteger r;

    public void generateKey(String publicKeyFileName, String privateKeyFileName) {

        Random r = new Random();

        BigInteger p = BigInteger.probablePrime(bitLength, r);
        BigInteger q = BigInteger.probablePrime(bitLength, r);
        N = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.ZERO;
        for (int i = 0; i < 100000; i++) {
            e = BigInteger.probablePrime(bitLength, r);
            if(e.compareTo(BigInteger.ONE) == 1
                    && e.compareTo(phi) == -1 && e.gcd(phi).compareTo(BigInteger.ONE) == 0)
                break;
        }

        BigInteger s = e.modInverse(phi);
        BigInteger x = j.modPow(s, N).modInverse(N);
        BigInteger y = x.modPow(e, N);

        try {
            PrintWriter pw = new PrintWriter(publicKeyFileName);
            pw.println(N.toString());
            pw.println(e.toString());
            pw.println(y.toString());
            System.out.println("Опубликован открытый ключ");
            System.out.println("N = " + N);
            System.out.println("e = " + e);
            System.out.println("y = " + y);
            pw.close();

            pw = new PrintWriter(privateKeyFileName);
            pw.println(N.toString());
            pw.println(e.toString());
            pw.println(x.toString());
            System.out.println("Сохранен закрытый ключ");
            System.out.println("x = " + x);
            pw.close();
        } catch (IOException exe) {
            exe.printStackTrace();
        }
    }

    public void sendA (String privateKeyFileName, String mailBoxFileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(privateKeyFileName));
        N = new BigInteger(scanner.nextLine());
        e = new BigInteger(scanner.nextLine());
        if(e.compareTo(BigInteger.ZERO) == 0 || N.compareTo(BigInteger.ZERO) == 0)
            System.out.println("Секретный ключ не используется");
        else
            System.out.println("Считан секретный ключ");

        r = BigInteger.ZERO;
        while (r.compareTo(N) >= 0 || r.compareTo(BigInteger.ONE) <= 0) {
            r = new BigInteger(bitLength, new Random());
        }
        System.out.println("Выбранно r = " + r);

        BigInteger a = r.modPow(e, N);
        System.out.println("Сгенерированно сообщение a = " + a);


        PrintWriter pw = new PrintWriter(mailBoxFileName);
        pw.println(a.toString());
        System.out.println("Сообщение отправлено");
        pw.close();
    }

    public void setR(BigInteger r) {
        this.r = r;
    }

    public void sendZ (String publicKeyFileName, String mailBoxFileName) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(publicKeyFileName));
        N = new BigInteger(scanner.nextLine());
        e = new BigInteger(scanner.nextLine());
        BigInteger x = new BigInteger(scanner.nextLine());
        if (x.compareTo(BigInteger.ZERO) == 0 || N.compareTo(BigInteger.ZERO) == 0) {
            System.out.println("Секретный ключ не используется");
            return;
        }
        else {
            System.out.println("Считан секретный ключ");
        }

        BigInteger c;
        scanner = new Scanner(new File(mailBoxFileName));
        c = new BigInteger(scanner.nextLine());
        if (c.compareTo(BigInteger.ZERO) == 0 || c.compareTo(r.modPow(e, N)) == 0) {
            System.out.println("Случайное число получателя не найдено");
            return;
        }
        else {
            System.out.println("Считано случайное число получателя");
        }

        if(r == null || r.compareTo(BigInteger.ZERO) == 0){
            System.out.println("Отсутствует случайное число отправителя ");
            System.out.println("r = " + r);
            return;
        }
        else {
            BigInteger z = r.multiply(x.modPow(c, N));
            System.out.println("Сгенерированно сообщение z = " + z);
            PrintWriter pw = new PrintWriter(mailBoxFileName);
            pw.println(z.toString());
            System.out.println("Сообщение отправлено");
            pw.close();
        }
    }
}