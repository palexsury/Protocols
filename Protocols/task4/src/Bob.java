import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Bob {
    BigInteger c = null;
    BigInteger a;

    public void sendC (String publicKeyFileName, String mailBoxFileName) throws FileNotFoundException {
        BigInteger N, e, y;

        Scanner s = new Scanner(new File(publicKeyFileName));
        N = new BigInteger(s.nextLine());
        e = new BigInteger(s.nextLine());
        y = new BigInteger(s.nextLine());
        if(y.compareTo(BigInteger.ZERO) == 0 || N.compareTo(BigInteger.ZERO) == 0 || e.compareTo(BigInteger.ZERO) == 0){
            System.out.println("Открытый ключ не используется");
            return;
        }
        else
            System.out.println("Считан открытый ключ");

        a = BigInteger.ZERO;

        s = new Scanner(new File(mailBoxFileName));
        a = new BigInteger(s.nextLine());
        if(a.compareTo(BigInteger.ZERO) == 0 ) {
            System.out.println("Случайное число отправителя не найдено");
            return;
        }
        else
            System.out.println("Считано случайное число отправителя");

        c = BigInteger.ZERO;
        while (c.compareTo(e) >= 0 || c.compareTo(BigInteger.ONE) < 0) {
            c = new BigInteger(e.bitLength(), new Random());
        }
        System.out.println("Выбранно c = " + c);

        PrintWriter writer = new PrintWriter(mailBoxFileName);
        writer.println(c.toString());
        System.out.println("Сообщение отправлено");
        writer.close();
    }

    public void setC(BigInteger c) {
        this.c = c;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public boolean check (String publicKeyFileName, String mailBoxFileName) throws FileNotFoundException {
        BigInteger N, e, y;
        Scanner s = new Scanner(new File(publicKeyFileName));
        N = new BigInteger(s.nextLine());
        e = new BigInteger(s.nextLine());
        y = new BigInteger(s.nextLine());
        if (y.compareTo(BigInteger.ZERO) == 0 || N.compareTo(BigInteger.ZERO) == 0 || e.compareTo(BigInteger.ZERO) == 0) {
            System.out.println("Открытый ключ не используется");
            return false;
        }
        else {
            System.out.println("Считан открытый ключ");
        }

        BigInteger z;

        s = new Scanner(new File(mailBoxFileName));
        z = new BigInteger(s.nextLine());
        if(z.compareTo(BigInteger.ZERO) == 0 || z.compareTo(c) == 0 ){
            System.out.println("Число с преведущего шага не получено");
            return false;
        }
        else {
            System.out.println("Получено число с преведущего шага");
        }

        if(a == null || a.compareTo(BigInteger.ZERO) == 0 ) {
            System.out.println("Случайное число отправителя не найдено");
            return false;
        }
        else {
            System.out.println("Считано случайное число отправителя");
        }

        if(this.c == null || c.compareTo(BigInteger.ZERO) == 0){
            System.out.println("Отсутствует случайное число получателя ");
        } else {
            z = z.modPow(e, N);
            BigInteger newZ = a.multiply(y.modPow(c, N)).mod(N);
            System.out.println("z^L ?= a * y^C (mod n)");
            System.out.println(z + " = " + newZ);
            if(z.compareTo(newZ) == 0) {
                System.out.println("Аутентификация пройдена");
                return true;
            }
        }
        System.out.println("Аутентификация не пройдена");
        return false;
    }
}