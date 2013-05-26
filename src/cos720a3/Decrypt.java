package cos720a3;

import java.math.BigInteger;

/**
 *
 * @author phlippie
 */
public class Decrypt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                if (args.length != 3) {
            System.out.println ("Wrong number of arguments.");
            usage();
            return;
        }
        String ciphertextFileName = args[0];
        String plaintextFileName = args[1];
        String privateKeyFileName = args[2];

        if (! (Util.isValidFile(ciphertextFileName) && Util.isValidCreateFile(plaintextFileName) && Util.isValidFile(privateKeyFileName))) {
            System.out.println ("Some input files are invalid.");
            usage ();
            return;
        }

        try {
            String rawCiphertext = Util.readFile(ciphertextFileName);
            if (rawCiphertext == null) {
                System.out.println ("Ciphertext file [" + ciphertextFileName + "] is invalid.");
                usage();
                return;
            }
            String [] cipherTextArray = rawCiphertext.split(" ");
            BigInteger [] cipherNumbersArray = new BigInteger [cipherTextArray.length];
            for (int i = 0; i < cipherTextArray.length; i++) {
                cipherNumbersArray[i] = new BigInteger(cipherTextArray[i]);
            }

            PrivateKey k = KeyFileIO.readPrivateKeyFromFilename(privateKeyFileName);
            if (k == null) {
                System.out.println ("Private key in file [" + privateKeyFileName + "] is invalid.");
                usage ();
            }

            String plaintext = Decryption.decryptNumbersAsTextString(cipherNumbersArray, k);
            if (plaintext == null) {
                System.out.println ("Something went wrong during encryption.");
                return;
            }

            System.out.println("Decrypted string: ["+plaintext+"]");
            Util.writeStringToFile(plaintext, plaintextFileName);
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
    }

    static void usage () {
        String usage = "Required arguments are <ciphertext input file> <plaintext output file> <private key file>";
        System.out.println(usage);
    }

}
