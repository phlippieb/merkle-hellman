package cos720a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Reads and writes keys. Deals with q and r being at the end of a file
 * @author phlippie
 */
public class KeyFileIO {

    /**
     * Writes the given public key to the file at the given filename
     * The key is in the form: knapsack, q, r
     * @param k A PublicKey object to write to a file
     * @param filename The path to the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFilename(PublicKey k, String filename) throws IOException {
        BufferedWriter w = new BufferedWriter (new FileWriter (new File (filename)));
        for (int i = 0; i < k.knapsack.size(); i++) {
            w.write(new Integer(k.knapsack.get(i)).toString() + " ");
        }
        w.write(new Integer(k.q).toString() + " " + new Integer(k.r).toString());
        w.close();
    }

    /**
     * Writes the given public key to the file represented by the given File object.
     * The key is in the form: knapsack, q, r
     * @param k A PublicKey object to write to a file
     * @param file A file object that represents the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFile(PublicKey k, File file) throws IOException {
        BufferedWriter w = new BufferedWriter (new FileWriter (file));
        for (int i = 0; i < k.knapsack.size(); i++) {
            w.write(new Integer(k.knapsack.get(i)).toString() + " ");
        }
        w.write(new Integer(k.q).toString() + " " + new Integer(k.r).toString());
        w.close();
    }

    /**
     * Writes the given private key to the file at the given path.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PrivateKey object to write to file
     * @param filename The path to the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFilename(PrivateKey k, String filename) throws IOException{
        BufferedWriter w = new BufferedWriter (new FileWriter (new File (filename)));
        for (int i = 0; i < k.knapsack.size(); i++) {
            w.write(new Integer(k.knapsack.get(i)).toString() + " ");
        }
        w.close();
    }

    /**
     * Writes the given private key to the file represented by the given File object.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PrivateKey object to write to file
     * @param file A File object representing the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFile(PrivateKey k, File file) throws IOException{
        BufferedWriter w = new BufferedWriter (new FileWriter (file));
        for (int i = 0; i < k.knapsack.size(); i++) {
            w.write(new Integer(k.knapsack.get(i)).toString() + " ");
        }
        w.close();
    }




}
