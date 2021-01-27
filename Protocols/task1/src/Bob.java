import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Bob {
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static boolean check (String messageFileName, String hashFileName, String secretFileName) {
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
        String hashNew = (sha.digest());

        System.out.println("Расчитан хеш полученного сообщения:");

        //String hashNew = byteArrayToHex(arr);
        System.out.println(hashNew);

        String hashOld = "read err";
        try {
            Scanner s = new Scanner(new File(hashFileName));
            hashOld = s.nextLine();
            System.out.println("Получен отправленный хеш:");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(hashOld);


        if(!hashNew.equals(hashOld)) {
            System.out.println("Хеши не совпадают, сообщение не прошло проверку подлиности");
            return false;
        }

        System.out.println("Хеши совпадают, сообщение подлино");
        return true;
    }
}