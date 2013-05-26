package cos720a3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class GenerateKeys {


    /**
     * Generates a private and public key as text files.
     * Expected arguments are:
     *  <size>: The number of items in the knapsack
     *  <private key file> The file to write the private key to
     *  <public key file> The file to write the public key to
     * @param args the command line arguments:
     */
    public static void main(String[] args) throws Exception {

        //////////////////
        // check arguments

        if (args.length != 2) {
            System.out.println ("Invalid number of arguments");
            usage();
            return;
        }

        int knapsackSize = 16;

        String privateKeyFileName = args[0], publicKeyFileName = args[1];

        if (!Util.isValidCreateFile(publicKeyFileName) || !Util.isValidCreateFile(privateKeyFileName)) {
            System.out.println("Invalid filename(s)");
            usage();
            return;
        }

        ///////////////////////
        // generate keys

        try {
            PrivateKey privateKey = generatePrivateKey(knapsackSize);
            if (privateKey == null) {
                System.out.println("Error");
                return;
            }
            System.out.print("private key:  "+ privateKey);
            System.out.println(" (r="+privateKey.getR()+")");
            PublicKey publicKey = privateKey.derivePublicKey();
            if (privateKey == null) {
                System.out.println("Error");
                return;
            }
            System.out.println("public key: "+ publicKey);


            /////////////////////
            //write keys to files
            KeyFileIO.writePublicKeyToFilename(publicKey, publicKeyFileName);
            KeyFileIO.writePrivateKeyToFilename(privateKey, privateKeyFileName);

        } catch (Exception e) {
            System.out.println ("Error");
            return;
        }
    }

    static double arrayListTotal (ArrayList<Integer> array) {
        double total = 0.0;
        for (int i = 0; i < array.size(); i++) {
            total += array.get(i);
        }
        return total;
    }

    static PrivateKey generatePrivateKey (int size) {
        BigInteger initialMax = BigInteger.valueOf(1000);
        BigInteger incrementSize = BigInteger.valueOf(1000);
        PrivateKey privateKey = new PrivateKey();

        //generate a superincreasing knapsack
        BigInteger max = new BigInteger(initialMax.toString()), publicKnapsackTotal = BigInteger.valueOf(0);
        BigInteger randomNumber = randomBigIntLessThan(max);
        privateKey.addToKnapsack(randomNumber);
        publicKnapsackTotal = publicKnapsackTotal.add(randomNumber);
        BigInteger lastElement = randomNumber;
        for (int i = 1; i < size; i++) {
            while (randomNumber.compareTo(publicKnapsackTotal) <= 0) {
                max = max.add(incrementSize);
                randomNumber = randomBigIntLessThan(max);
            }
            privateKey.addToKnapsack(randomNumber);
            publicKnapsackTotal = publicKnapsackTotal.add(randomNumber);
            lastElement = randomNumber;
        }

        //generate random q such that q > sum of knapsack and q < 2^129 (per spec)
        max = BigInteger.valueOf((long)Math.pow(2.0, 129.0));
        do {
            randomNumber = randomBigIntLessThan(max);
        } while (randomNumber.compareTo(publicKnapsackTotal) <= 0);
        BigInteger q = randomNumber;
        privateKey.setQ(q);

        //generate random r such that q > sum of knapsack and r < 2^129
        BigInteger gcd;
        do {
            max = max.add(incrementSize);
            randomNumber = randomBigIntLessThan(max);
            gcd = randomNumber.gcd(q);
            if (gcd.compareTo(BigInteger.valueOf(-1)) == 0) {
                System.out.println("Error.");
                return null;
            }
        } while (randomNumber.compareTo(publicKnapsackTotal) <= 0 || gcd.compareTo(BigInteger.valueOf(1)) != 0 || randomNumber.multiply(lastElement).compareTo(q) == -1);
        privateKey.setR(randomNumber);
        return privateKey;
    }

    static void usage () {
        System.out.println("Required arguments: <private key filename> <public key filename>");
    }

    static BigInteger randomBigIntLessThan(BigInteger max) {
        BigInteger r;
        Random rnd = new Random();
        do {
            r = new BigInteger(max.bitLength(), rnd);
        } while (r.compareTo(max) >= 0);
        return r;
    }


}
