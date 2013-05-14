package cos720a3;

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

        PublicKey publicKey = new PublicKey();
        PrivateKey privateKey;

        //generate a superincreasing knapsack
        double max = initialMax, publicKnapsackTotal = 0.0;
        Integer randomNumber = (int)(Math.random()*max);
        publicKey.knapsack.add(randomNumber);
        for (int i = 1; i < knapsackSize; i++) {
            publicKnapsackTotal = arrayListTotal(publicKey.knapsack);
            while (randomNumber <= publicKnapsackTotal) {
                max += 10;
                randomNumber = (int)(Math.random()*max);
            }
            publicKey.knapsack.add(randomNumber);
        }

        //generate random q such that q > sum of knapsack
        //double publicKnapsackTotal = 0.0;
        for (int i = 0; i < publicKey.knapsack.size(); i++) {
            publicKnapsackTotal += publicKey.knapsack.get(i);
        }
        do {
            max += 10;
            randomNumber = (int)(Math.random()*max);
        } while (randomNumber <= publicKnapsackTotal);
        publicKey.q = randomNumber;

        do {
            max += 10;
            randomNumber = (int)(Math.random()*max);
        } while (Util.gcd(randomNumber, publicKey.q) != 1);
        publicKey.r = randomNumber;


        //////////////////////
        //generate private key
        privateKey = publicKey.derivePrivateKey();


        /////////////////////
        //write keys to files
        KeyFileIO.writePublicKeyToFilename(publicKey, publicKeyFileName);
        KeyFileIO.writePrivateKeyToFilename(privateKey, privateKeyFileName);

    }

    static double arrayListTotal (ArrayList<Integer> array) {
        double total = 0.0;
        for (int i = 0; i < array.size(); i++) {
            total += array.get(i);
        }
        return total;
    }


}
