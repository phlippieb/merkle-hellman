package cos720a3;

/**
 * Encrypts data using Merkle-Hellman.
 * Command-line arguments are:
 * <plaintext filename> <ciphertext filename> <public key filename>
 * @author phlippie
 */
public class Encrypt {

    public static void main (String [] args) throws Exception {
        if (args.length != 3) {
            System.out.println ("Wrong number of arguments.");
            usage();
            return;
        }
        String plaintextFileName = args[0];
        String ciphertextFileName = args[1];
        String publicKeyFileName = args[2];

        if (! (Util.isValidFile(plaintextFileName) && Util.isValidFile(ciphertextFileName) && Util.isValidFile(publicKeyFileName))) {
            System.out.println ("Some input files are invalid.");
            usage ();
            return;
        }

        String plaintext = Util.readFile(plaintextFileName);
        if (plaintext == null) {
            System.out.println ("Plaintext file [" + plaintextFileName + "] is invalid.");
            usage();
            return;
        }
        
        PublicKey k = KeyFileIO.readPublicKeyFromFilename(publicKeyFileName);
        if (k == null || ! k.isValid()) {
            System.out.println ("Public key in file [" + publicKeyFileName + "] is invalid.");
            usage ();
        }
        
        Integer [] cipher = Encryption.encryptTextStringAsNumbers(plaintext, k);
        if (cipher == null || cipher.length == 0) {
            System.out.println ("Something went wrong during encryption.");
            return;
        }

        StringBuilder sb = new StringBuilder ();
        for (Integer i : cipher) {
            if (i == null) {
                System.out.println ("Something went wrong during encryption.");
                return;
            }
            sb.append(i.toString() + " ");
        }
        String cipherText = sb.toString();
        System.out.println("Encrypted value(s): ["+cipherText+"]");
        Util.writeStringToFile(cipherText, ciphertextFileName);
    }

    static void usage () {
        String usage = "Required arguments are <plaintext input file> <ciphertext output file> <public key file>";
        System.out.println(usage);
    }

}
