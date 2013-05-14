/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cos720a3;

/**
 *
 * @author phlippie
 */
public class Encrypt {

    public static void main (String [] args) throws Exception {
        String publicKeyFileName = args[0];
        PublicKey k = KeyFileIO.readPublicKeyFromFilename(publicKeyFileName);
        Integer [] encrypted = Encryption.encryptTextStringAsNumbers("abcdefghijklmnop", k);
        
        System.out.print("Encrypted value(s): [");
        for (int i = 0; i < encrypted.length; i++) {
            System.out.print(encrypted[i] + " ");
        }
        System.out.println("]");

    }

}
