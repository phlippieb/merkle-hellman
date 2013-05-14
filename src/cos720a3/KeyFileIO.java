package cos720a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads and writes keys. Deals with q and r being at the end of a file
 * @author phlippie
 */
public class KeyFileIO {

    File publicKeyFile, privateKeyFile;

    /**
     * Sets this object's reference to the public key file to be a  File object for the given path.
     * @param filename The name (or path) of the public key file
     */
    public void setPublicKeyFilename(String filename) {
        this.publicKeyFile = new File (filename);
    }

    /**
     * Sets this object's reference to the public key to be the given File object.
     * @param file The File object that represents the public key
     */
    public void setPublicKeyFile(File file) {
        this.publicKeyFile = file;
    }

    /**
     * Sets this object's reference to the private key file to be a  File object for the given path.
     * @param filename The name (or path) of the private key file
     */
    private void setPrivateKeyFilename(String filename) {
        this.privateKeyFile = new File (filename);
    }

    /**
     * Sets this object's reference to the private key to be the given File object.
     * @param file The File object that represents the private key
     */
    private void setPrivateKeyFile(File file) {
        this.privateKeyFile = file;
    }

    /**
     * Writes the given public key to the file this object knows as the public key file.
     * The key is in the form: knapsack, q, r
     * @param knapsack An ArrayList object containing the elements of the superincreasing public key knapsack
     * @param q A number larger than the sum of the knapsack
     * @param r A number that is co-prime with q
     * @throws IOException
     */
    public void writePublicKey(ArrayList<Integer> knapsack, int q, int r) throws IOException {
        BufferedWriter w = new BufferedWriter (new FileWriter (publicKeyFile));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.write(new Integer(q).toString() + " " + new Integer(r).toString());
        w.close();
    }

    /**
     * Writes the given public key to the file at the given filename
     * The key is in the form: knapsack, q, r
     * @param knapsack An ArrayList object containing the elements of the superincreasing public key knapsack
     * @param q A number larger than the sum of the knapsack
     * @param r A number that is co-prime with q
     * @param filename The path to the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFilename(ArrayList<Integer> knapsack, int q, int r, String filename) throws IOException {
        BufferedWriter w = new BufferedWriter (new FileWriter (new File (filename)));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.write(new Integer(q).toString() + " " + new Integer(r).toString());
        w.close();
    }

    /**
     * Writes the given public key to the file represented by the given File object.
     * The key is in the form: knapsack, q, r
     * @param knapsack An ArrayList object containing the elements of the superincreasing public key knapsack
     * @param q A number larger than the sum of the knapsack
     * @param r A number that is co-prime with q
     * @param file A file object that represents the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFile(ArrayList<Integer> knapsack, int q, int r, File file) throws IOException {
        BufferedWriter w = new BufferedWriter (new FileWriter (file));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.write(new Integer(q).toString() + " " + new Integer(r).toString());
        w.close();
    }

    /**
     * Writes the given private key to the file that this object knows as the private key file.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param knapsack An ArrayList object containing the elements of the private key knapsack, derived from the public key
     * @throws IOException
     */
    public void writePrivateKey(ArrayList<Integer> knapsack) throws IOException{
        BufferedWriter w = new BufferedWriter (new FileWriter (privateKeyFile));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.close();
    }

    /**
     * Writes the given private key to the file at the given path.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param knapsack An ArrayList object containing the elements of the private key knapsack, derived from the public key
     * @param filename The path to the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFilename(ArrayList<Integer> knapsack, String filename) throws IOException{
        BufferedWriter w = new BufferedWriter (new FileWriter (new File (filename)));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.close();
    }

    /**
     * Writes the given private key to the file represented by the given File object.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param knapsack An ArrayList object containing the elements of the private key knapsack, derived from the public key
     * @param file A File object representing the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFile(ArrayList<Integer> knapsack, File file) throws IOException{
        BufferedWriter w = new BufferedWriter (new FileWriter (file));
        for (int i = 0; i < knapsack.size(); i++) {
            w.write(new Integer(knapsack.get(i)).toString() + " ");
        }
        w.close();
    }




}
