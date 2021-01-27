import java.io.*;
import java.util.Scanner;

public class Alice {


    public static void send (String messageFileName, String hashFileName, String secretFileName) {
        String mes = "read err";
        {
            try {
                Scanner s = new Scanner(new File(messageFileName));
                mes = s.nextLine();
                System.out.println("Считано сообщение");
            } catch (IOException e) {
                System.out.println("Сообщение не считано");
                e.printStackTrace();
            }

        }
        String secret = "";
        {
            try {
                Scanner s = new Scanner(new File(secretFileName));
                secret = s.nextLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(secret.equals(""))
                System.out.println("Секретный ключ не используется");
            else
                System.out.println("Считан секретный ключ");
        }

        mes = mes + secret;

        SHA sha = new SHA();
        sha.update(mes.getBytes());
        String s = (sha.digest());

        System.out.println("Получен хеш сообщения:");
        try {
            PrintWriter writer = new PrintWriter(hashFileName);
            writer.println(s);//byteArrayToHex(arr));
            System.out.println(s);//byteArrayToHex(arr));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Отправитель отправил сообщение");
    }

}