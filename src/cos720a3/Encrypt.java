package cos720a3;

/**
 * Encrypts data using Merkle-Hellman.
 * Command-line arguments are:
 * <plaintext filename> <ciphertext filename> <public key filename>
 * @author phlippie
 */
public class Encrypt {

    public static void main (String [] args) throws Exception {
        if (args.length < 3) {
            throw new IllegalArgumentException("Required arguments are <message file> <public key file>");
        }
        String plaintextFileName = args[0];
        String ciphertextFileName = args[1];
        String publicKeyFileName = args[2];

        String plaintext = Util.readFile(plaintextFileName);
        
        PublicKey k = KeyFileIO.readPublicKeyFromFilename(publicKeyFileName);
        
        Integer [] cipher = Encryption.encryptTextStringAsNumbers(plaintext, k);

        StringBuilder sb = new StringBuilder ();
        for (Integer i : cipher) {
            sb.append(i.toString() + " ");
        }
        String cipherText = sb.toString();
        System.out.print("Encrypted value(s): ["+cipherText+"]");
        Util.writeStringToFile(cipherText, ciphertextFileName);
    }

}
