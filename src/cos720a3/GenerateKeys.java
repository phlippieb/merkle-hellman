package cos720a3;

import java.util.ArrayList;

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

        final int minimumKnapsackSize = 3;
        final int maximumKnapsackSize = 20;

        //////////////////
        // check arguments

        if (args.length != 3) {
            System.out.println ("Invalid number of arguments");
            usage();
            return;
        }

        int knapsackSize = -1;
        try {
            knapsackSize = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Size parameter is invalid");
            usage();
            return;
        }

        if (knapsackSize < minimumKnapsackSize || knapsackSize > maximumKnapsackSize) {
            System.out.println("Size parameter is invalid. Please use a size between " + minimumKnapsackSize + " and " + maximumKnapsackSize);
            usage();
            return;
        }

        String privateKeyFileName = args[1], publicKeyFileName = args[2];

        if (!Util.isValidCreateFile(publicKeyFileName) || !Util.isValidCreateFile(privateKeyFileName)) {
            System.out.println("Invalid filename(s)");
            usage();
            return;
        }

        ///////////////////////
        // generate keys

        try {
            PrivateKey publicKey = generatePublicKey(knapsackSize);
            if (publicKey == null) {
                System.out.println("Error");
                return;
            }
            System.out.println("public key:  "+publicKey);
            PublicKey privateKey = publicKey.derivePublicKey();
            if (privateKey == null) {
                System.out.println("Error");
                return;
            }
            System.out.println("private key: "+privateKey);


            /////////////////////
            //write keys to files
            KeyFileIO.writePublicKeyToFilename(publicKey, publicKeyFileName);
            KeyFileIO.writePrivateKeyToFilename(privateKey, privateKeyFileName);

            //////////////////
            //test key reading
            PrivateKey testP = KeyFileIO.readPublicKeyFromFilename(publicKeyFileName);
            System.out.println("test public key read: " + testP);
            PublicKey testPr = KeyFileIO.readPrivateKeyFromFilename(privateKeyFileName);
            System.out.println("test private key read: " + testPr);
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

    static PrivateKey generatePublicKey (int size) {
        int initialMax = 50;
        int incrementSize = 1;
        PrivateKey publicKey = new PrivateKey();

        //generate a superincreasing knapsack
        double max = initialMax, publicKnapsackTotal = 0.0;
        Double randomNumber = Math.ceil(Math.random()*max);
        publicKey.addToKnapsack(randomNumber);
        publicKnapsackTotal += randomNumber;
        for (int i = 1; i < size; i++) {
            while (randomNumber <= publicKnapsackTotal) {
                max += incrementSize;
                randomNumber = Math.ceil(Math.random()*max);
            }
            publicKey.addToKnapsack(randomNumber);
            publicKnapsackTotal += randomNumber;
        }

        //generate random q such that q > sum of knapsack
        //double publicKnapsackTotal = 0.0;
        //for (int i = 0; i < publicKey.getKnapsackSize(); i++) {
        //    publicKnapsackTotal += publicKey.knapsack.get(i);
        //}
        do {
            max += incrementSize;
            randomNumber = Math.ceil(Math.random()*max);
        } while (randomNumber <= publicKnapsackTotal);
        Double q = randomNumber;
        publicKey.setQ(q);

        double gcd;
        do {
            max += incrementSize;
            randomNumber = Math.ceil(Math.random()*max);
            gcd = Util.gcd(randomNumber, q);
            if (gcd == -1) {
                System.out.println("Error.");
                return null;
            }
        } while (gcd != 1);
        publicKey.setR(randomNumber);
        return publicKey;
    }

    static void usage () {
        System.out.println("Required arguments: <key knapsack size> <private key filename> <public key filename>");
    }


}
