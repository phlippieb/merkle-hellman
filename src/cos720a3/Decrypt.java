/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cos720a3;

/**
 *
 * @author Kinesa
 */
public class Decrypt {

    public static void main(String[] args) throws Exception {
        boolean firstwrite = true;
        if (args.length != 3) {
            System.out.println("Wrong number of arguments.");
            usage();
            return;
        }
        String ciphertextFileName = args[0];
        String plaintextFileName = args[1];
        String privateKeyFileName = args[2];

        if (!(Util.isValidFile(ciphertextFileName) && Util.isValidCreateFile(plaintextFileName) && Util.isValidFile(privateKeyFileName))) {
            System.out.println("Some input files are invalid.");
            usage();
            return;
        }

        try {
            String ciphertext = Util.readFile(ciphertextFileName);
            if (ciphertext == null) {
                System.out.println("ciphertext file [" + ciphertextFileName + "] is invalid.");
                usage();
                return;
            }

            PrivateKey k = KeyFileIO.readPrivateKeyFromFilename(privateKeyFileName);
            if (k == null) {
                System.out.println("private key in file [" + privateKeyFileName + "] is invalid.");
                usage();
            }

            String[] cipherTextArray;
            cipherTextArray = ciphertext.split(" ");

            for (int c = 0; c < cipherTextArray.length; c++) {
                String ciphervalue = cipherTextArray[c];
                byte[] cipher = Decryption.decryptor(ciphervalue, k);
                // Write to file
                if (cipher == null || cipher.length == 0) {
                    System.out.println("Something went wrong during decryption.");
                    return;
                }

                if (firstwrite == true) {
                    Util.writeStringToFile(new String(cipher), plaintextFileName);
                    firstwrite = false;
                } else {
                    Util.appendwriteStringToFile(new String(cipher), plaintextFileName);

                }


            }
            System.out.println("Decryption completed");



        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
    }

    static void usage() {
        String usage = "Required arguments are <ciphertext input file> <plaintext output file> <private key file>";
        System.out.println(usage);
    }
}