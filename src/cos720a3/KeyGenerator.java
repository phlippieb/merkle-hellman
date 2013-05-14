package cos720a3;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

public class KeyGenerator {

    /**
     * Generates a private and public key as text files.
     * Expected arguments are:
     *  <size>: The number of items in the knapsack
     *  <private key file> The file to write the private key to
     *  <public key file> The file to write the public key to
     * @param args the command line arguments:
     */
    public static void main(String[] args) throws Exception {
        int initialMax = 50;

        //////////////////
        // check arguments

        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        int knapsackSize = Integer.parseInt(args[0]);
        String privateKeyFileName = args[1], publicKeyFileName = args[2];

        ///////////////////////
        // generate public key

        ArrayList<Integer> publicKnapsack = new ArrayList<Integer>();
        int q, r;

        

        //generate a superincreasing knapsack
        double max = initialMax, publicKnapsackTotal = 0.0;
        Integer randomNumber = (int)(Math.random()*max);
        publicKnapsack.add(randomNumber);
        System.out.print("public key:  [ " + randomNumber + " ");
        for (int i = 1; i < knapsackSize; i++) {
            publicKnapsackTotal = arrayListTotal(publicKnapsack);
            while (randomNumber <= publicKnapsackTotal) {
                max += 10;
                randomNumber = (int)(Math.random()*max);
            }
            publicKnapsack.add(randomNumber);
            System.out.print(randomNumber + " ");
        }
        System.out.print("] [");

        //generate random q such that q > sum of knapsack
        //double publicKnapsackTotal = 0.0;
        for (int i = 0; i < publicKnapsack.size(); i++) {
            publicKnapsackTotal += publicKnapsack.get(i);
        }
        do {
            max += 10;
            randomNumber = (int)(Math.random()*max);
        } while (randomNumber <= publicKnapsackTotal);
        q = randomNumber;
        System.out.print(q + "] [");

        do {
            max += 10;
            randomNumber = (int)(Math.random()*max);
        } while (getGCD(randomNumber, q) != 1);
        r = randomNumber;
        System.out.println(r + "]");


        //////////////////////
        //generate private key
        ArrayList<Integer> privateKnapsack = new ArrayList<Integer>();
        System.out.print ("private key: [ ");
        for (int i = 0; i < publicKnapsack.size(); i++) {
            privateKnapsack.add(r * publicKnapsack.get(i) % q);
            System.out.print (privateKnapsack.get(i) + " ");
        }
        System.out.println("]");


        /////////////////////
        //write keys to files
        KeyFileIO.writePublicKeyToFilename(publicKnapsack, q, r, publicKeyFileName);
        KeyFileIO.writePrivateKeyToFilename(privateKnapsack, privateKeyFileName);

    }

    static int getGCD(int x, int y) {
        BigInteger b1 = new BigInteger(Integer.toString(x));
        BigInteger b2 = new BigInteger(Integer.toString(y));
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    static double arrayListTotal (ArrayList<Integer> array) {
        double total = 0.0;
        for (int i = 0; i < array.size(); i++) {
            total += array.get(i);
        }
        return total;
    }


}
