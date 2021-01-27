
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Restorer {
    public static String calc(String path) {

        BigInteger[][] A = new BigInteger[100][];
        BigInteger p;
        try {
            Scanner s = new Scanner(new File(path));
            p = new BigInteger(s.next());
            String str = s.nextLine();
            int i = 0;
            while (s.hasNextLine()){
                str = s.nextLine();
                String[] nums = str.split("\\s");
                A[i] = new BigInteger[nums.length];
                for (int j = 0; j < nums.length; j++) {
                    A[i][j] = new BigInteger(nums[j]);
                }
                i++;
            }
            BigInteger[][] matrix = new BigInteger[A[0].length - 1][];
            if (A[0].length - 1 >= 0) System.arraycopy(A, 0, matrix, 0, A[0].length - 1);
            Gauss gauss = new Gauss(matrix, p);
            return gauss.calc()[0];

        } catch (IOException err) {
            err.printStackTrace();
        } catch (NumberFormatException | NullPointerException err){
            System.out.println("Количество долей < k");
        }
        return "Ошибка";

    }
}