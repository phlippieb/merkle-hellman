package cos720a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Util {
    public static int gcd(int x, int y) {
        BigInteger b1 = new BigInteger(Integer.toString(x));
        BigInteger b2 = new BigInteger(Integer.toString(y));
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    public static String stringToBinaryString (String s) {
        char [] cArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c:cArray) {
            String cBinaryString = Integer.toBinaryString((int)c);
            sb.append(cBinaryString);
        }
        return sb.toString();
    }

    public static String binaryStringToString (String s) {
        String [] bytes = new String[s.length()/7];
        for (int i = 0; i < s.length(); i+=7) {
            bytes[i/7] = s.substring(i, i+7);
        }
        int [] charCodes = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            charCodes[i] = Integer.parseInt(bytes[i],2);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charCodes.length; i++) {
            sb.append ((new Character((char)charCodes[i])).toString());
        }
        return sb.toString();
    }

    public static String readFile (String filename) throws IOException {
        return new Scanner(new File(filename)).useDelimiter("\\Z").next();
    }

    public static void writeStringToFile (String s, String filename) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(new File(filename)));
        br.write(s);
        br.close();
    }
}

