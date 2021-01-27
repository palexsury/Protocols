import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String messageFileName = "Message.txt";
        String hashFileName = "Hash.txt";
        String secretFileName = "Secret.txt";

        System.out.println("1 - отправка сообщения 2 - проверка сообщения");
        while (true){
            Scanner s = new Scanner(System.in);
            int mode = s.nextInt();
            if(mode == 0)
                break;
            if(mode == 1)
                Alice.send(messageFileName, hashFileName, secretFileName);
            if(mode == 2)
                Bob.check(messageFileName, hashFileName, secretFileName);
        }
    }
}