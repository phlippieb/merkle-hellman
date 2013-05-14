package cos720a3;

/**
 * Implementation of the Merkle-Hellman encryption.
 * @author phlippie
 */
public class Encryption {
    public static String encryptAsString (String plaintext, PublicKey key) {
        String bString = Util.stringToBinaryString(plaintext);
        System.out.println(bString);
        String nString = Util.binaryStringToString(bString);
        System.out.println(nString);
        return "";
    }

}
