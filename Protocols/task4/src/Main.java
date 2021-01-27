import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String privateKeyFileName = "PrivateKey.txt";
        String publicKeyFileName = "PublicKey.txt";
        String mailBoxFileName = "MailBox.txt";
        Alice alice = new Alice();
        Bob bob = new Bob();
        String info = "1 - A -> B : {a}\n2 - B -> A : {c}\n3 - A -> B : {z}\n4 - проверка\n5 - генерация ключей\n6 - забыть\n7 - получить справку";
        System.out.println(info);
        System.out.println("-----------------------");
        while (true) {
            Scanner s = new Scanner(System.in);
            int mode = s.nextInt();
            if (mode == 0) {
                break;
            }
            if (mode == 1) {
                alice.sendA(privateKeyFileName, mailBoxFileName);
            }
            if (mode == 2) {
                bob.sendC(publicKeyFileName, mailBoxFileName);
            }
            if (mode == 3) {
                alice.sendZ(privateKeyFileName, mailBoxFileName);
            }
            if (mode == 4) {
                bob.check(publicKeyFileName, mailBoxFileName);
            }
            if (mode == 5) {
                alice.generateKey(publicKeyFileName, privateKeyFileName);
            }
            if (mode == 6) {
                alice.setR(BigInteger.ZERO);
                bob.setA(BigInteger.ZERO);
                bob.setC(BigInteger.ZERO);
                try {
                    PrintWriter writer = new PrintWriter(mailBoxFileName);
                    writer.println("0");
                    writer.close();
                } catch (IOException exe) {
                    exe.printStackTrace();
                }
                System.out.println("Промежуточные данные удалены");
            }
            if (mode == 7) {
                System.out.println(info);
            }
            System.out.println("-----------------------");
        }
    }

}