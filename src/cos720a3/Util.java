package cos720a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {

    public static double gcd(double x, double y) {
        try {
            if (y == 0.0) {
                return x;
            }
            double result = gcd(y, x%y);
            return result;
        } catch (StackOverflowError e) {
            return -1.0;
        }
    }

    public static String stringToBinaryString (String s) {
        char [] cArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c:cArray) {
            String cBinaryString = Integer.toBinaryString((int)c);
            while (cBinaryString.length() < 8) {
                cBinaryString = "0" + cBinaryString;
            }
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
        if (isValidFile(filename)) {
            return new Scanner(new File(filename)).useDelimiter("\\Z").next();
        }
        return null;
    }

    public static void writeStringToFile (String s, String filename) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(new File(filename)));
        br.write(s);
        br.close();
    }

        public static void appendwriteStringToFile (String s, String filename) throws IOException {

       /* BufferedWriter br = new BufferedWriter(new FileWriter(filename,true));
        br.write(s);
        br.close();*/

        FileWriter fileWriter = new FileWriter(filename,true);

        //Use BufferedWriter instead of FileWriter for better performance
        BufferedWriter bufferFileWriter  = new BufferedWriter(fileWriter);
        fileWriter.append(s);

        //Don't forget to close Streams or Reader to free FileDescriptor associated with it
        bufferFileWriter.close();


   }

    public static boolean isValidFile (String filename) {
        File test = new File(filename);
        if (test.exists() && test.canRead() && !test.isDirectory() && (test.length() > 0) && test.length() < 10000) {
           return true;
        } else {
            return false;
        }
    }

    public static boolean isValidCreateFile (String filename) {
        File test = new File (filename);
        if (test.exists() && !test.isDirectory()) {
            return true;
        } else {
            try {
                test.createNewFile();
                test.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
    static char binaryToChar(String binStr){
                char[] temp = binStr.toCharArray();
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += (Integer.parseInt(Character.toString(temp[i])) << (temp.length - i - 1));
        }

        return (char) sum;
        }
}

