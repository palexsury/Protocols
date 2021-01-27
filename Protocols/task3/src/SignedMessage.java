import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class SignedMessage {
    private String m;
    private boolean[] e;
    private BigInteger y;

    public SignedMessage(String m, boolean[] e, BigInteger y) {
        this.m = m;
        this.e = e;
        this. y = y;
    }

    public String getM() {
        return m;
    }

    public boolean[] getE() {
        return e;
    }

    public BigInteger getY() {
        return y;
    }

    public void writeToFile(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(fileName));
        pw.println(toString());
        pw.close();
    }

    public String toString() {
        return (new Gson()).toJson(this);
    }

}