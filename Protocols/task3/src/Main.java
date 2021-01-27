import com.dedipresta.crypto.hash.sha256.Sha256$;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String parametersFileName = "Parameters.txt";
    private static final String messageFileName = "Message.txt";
    private static final String signedMessageFileName = "SignedMessage.txt";
    private static final String publicKeyFileName = "PublicKey.txt";

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        System.out.println("1 - сгенерировать параметры");
        System.out.println("2 - подписать сообщение");
        System.out.println("3 - проверить подпись");
        System.out.println("------------------------------");
        Scanner sc = new Scanner(System.in);
        int mode = sc.nextInt();
        System.out.println("------------------------------");
        if (mode == 1) {
            System.out.print("Длина p = ");
            int pLength = sc.nextInt();
            System.out.print("Длина q = ");
            int qLength = sc.nextInt();
            System.out.print("Параметр k = ");
            int k = sc.nextInt();
            generateParameters(pLength, qLength, k);
        }
        if (mode == 2) {
            signMessage();
        }
        if (mode == 3) {
            verifySign();
        }
    }

    private static void writeToFile(String string, String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(fileName));
        pw.write(string);
        pw.close();
    }

    private static String readFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String string = br.readLine();
        br.close();
        return string;
    }

    private static void generateParameters(int pLength, int qLength, int k) throws FileNotFoundException {
        Parameters parameters = new Parameters(pLength, qLength, k);
        String jsonString = parameters.toString();
        writeToFile(jsonString, parametersFileName);
        System.out.println("Параметры успешно сгенерированы");
    }

    private static void signMessage() throws IOException {
        Parameters parameters = gson.fromJson(readFromFile(parametersFileName), Parameters.class);
        Alice alice = new Alice(parameters, publicKeyFileName);
        SignedMessage SM = alice.generateSign(readFromFile(messageFileName));
        String jsonString = SM.toString();
        writeToFile(jsonString, signedMessageFileName);
        System.out.println("Сообщение успешно подписано!");
    }

    private static void verifySign() throws IOException {
        Parameters parameters = gson.fromJson(readFromFile(parametersFileName), Parameters.class);
        SignedMessage SM = gson.fromJson(readFromFile(signedMessageFileName), SignedMessage.class);
        BigInteger[] v = gson.fromJson(readFromFile(publicKeyFileName), BigInteger[].class);
        Bob bob = new Bob(parameters, SM, v);
        if (bob.verifySign()) {
            System.out.println("Подпись прошла проверку!!!");
        }
        else {
            System.out.println("Подпись не прошла проверку!");
        }
    }

    public static BigInteger randomNum(BigInteger n) {
        Random random = new Random();
        BigInteger result = new BigInteger(n.bitCount(), random);
        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitCount(), random);
        }
        return result;
    }

    public static BigInteger hash(String m, BigInteger x) {
        byte[] bytes = Sha256$.MODULE$.hash(m + x.toString());
        String hString = BaseEncoding.base16().encode(bytes);
        return new BigInteger(hString, 16);
    }
}
