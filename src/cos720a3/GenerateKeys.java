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

            //////////////////
            //test key reading
            PrivateKey testP = KeyFileIO.readPrivateKeyFromFilename(privateKeyFileName);
            System.out.print("test private key read: " + testP);
            System.out.println(" (r="+privateKey.getR()+")");
            PublicKey testPr = KeyFileIO.readPublicKeyFromFilename(publicKeyFileName);
            System.out.println("test public key read: " + testPr);
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

        //generate random q such that q > sum of knapsack and q < 2^129 (per spec)
        //double publicKnapsackTotal = 0.0;
        //for (int i = 0; i < publicKey.getKnapsackSize(); i++) {
        //    publicKnapsackTotal += publicKey.knapsack.get(i);
        //}
        double tmpMax = max;
        max = Math.pow(2.0, 129.0);
        do {
            randomNumber = Math.ceil(Math.random()*max);
        } while (randomNumber <= publicKnapsackTotal);
        Double q = randomNumber;
        publicKey.setQ(q);

        double gcd;
        max = tmpMax;
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
        System.out.println("Required arguments: <private key filename> <public key filename>");
    }


}
