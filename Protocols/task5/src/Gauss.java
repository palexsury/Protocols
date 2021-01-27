import java.math.BigInteger;

public class Gauss {

    private BigInteger[][] data = null;
    private int rows = 0, cols = 0;
    private BigInteger p = BigInteger.ZERO;

    public Gauss(int rows, int cols, int p) {
        data = new BigInteger[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.p = BigInteger.valueOf(p);
    }

    public Gauss(BigInteger[][] data, BigInteger p) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
        this.p = p;
    }

    public void display() {
        for (int row = 0; row < rows; ++row) {
            if (row != 0) {
            }
            for (int col = 0; col < cols; ++col) {
                System.out.print(data[row][col].toString());

                if (col != cols - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public BigInteger[] getRow(int row){
        if(row < rows && row >= 0) {
            BigInteger [] res = new BigInteger[cols];
            System.arraycopy(data[row], 0, res, 0, cols);
            return res;
        } else
            System.out.println("wrong get row");
        return null;
    }


    public void setRow(BigInteger[] newRows, int row){
        if(newRows.length == cols && row < rows && row >= 0) {
            System.arraycopy(newRows, 0, data[row], 0, cols);
        } else
            System.out.println("wrong set row");
    }

    public void chosePivotElem(int i, int j){
        BigInteger pivot = data[i][j];
        BigInteger[] pivotRow = getRow(i);
        for (int k = 0; k < pivotRow.length; k++) {
            pivotRow[k] = pivotRow[k].multiply(pivot.modInverse(p)).mod(p);
        }
        setRow(pivotRow, i);
        for (int k = 0; k < rows; k++) {
            if(k != i){
                BigInteger a = data[k][j];
                BigInteger[] arr = getRow(k);
                for (int q = 0; q < cols; q++) {
                    arr[q] = arr[q].subtract(pivotRow[q].multiply(a)).mod(p);
                }
                setRow(arr, k);
            }
        }
    }


    public String[] calc(){
        int i = 0, j = 0;
        while(i < rows && j < cols - 1){
            if(data[i][j].compareTo(BigInteger.ZERO) == 0){
                boolean isEmpty = true;
                for (int k = i; k < rows; k++) {
                    if(data[k][j].compareTo(BigInteger.ZERO) != 0){
                        BigInteger[] row = getRow(k);
                        setRow(getRow(i), k);
                        setRow(row, i);
                        isEmpty = false;
                        break;
                    }
                }

                if(isEmpty) {
                    //i++;
                    j++;
                    continue;
                }
            }
            chosePivotElem(i, j);
            i++;
            j++;
        }

        System.out.println("Посчитано на основании " + rows + " долей");
        this.display();
        System.out.println(" ");
        System.out.print("Восстновленный секрет: ");

        String[] res = new String[cols - 1];
        for (int k = 0; k < rows; k++) {
            BigInteger sum = BigInteger.ZERO;
            for (int t = 0; t < cols - 1; t++) {
                sum = sum.add(data[k][t]);
            }
            int l = k;
            while (data[k][l].compareTo(BigInteger.ZERO) == 0)
                l++;
            StringBuilder s = new StringBuilder("");
            for (int t = l + 1; t < cols - 1; t++) {

                if(data[k][t].compareTo(BigInteger.ZERO) != 0) {
                    s.append(" + (-").append(data[k][t]).append(") * x").append(t + 1);
                }
            }
            s.append(" + ").append(data[k][cols - 1]);
            s = new StringBuilder(/*"x" + (l + 1) + " = " + */s.substring(3));


            if(sum.compareTo(BigInteger.ZERO) == 0 && data[k][cols - 1].compareTo(BigInteger.ZERO) != 0) {
                return new String[]{"Нет решений"};
            }

            if(sum.compareTo(BigInteger.ZERO) == 0 && data[k][cols - 1].compareTo(BigInteger.ZERO) == 0) {
                continue;
            }
            res[k] = s.toString();

        }


        return res;

    }

}