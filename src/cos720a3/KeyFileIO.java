package cos720a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Reads and writes keys. Deals with q and r being at the end of a file
 * @author phlippie
 */
public class KeyFileIO {

    /**
     * Writes the given public key to the file at the given filename
     * The key is in the form: knapsack, q, r
     * @param k A PrivateKey object to write to a file
     * @param filename The path to the file to write to
     * @throws IOException
     */
    public static void writePrivateKeyToFilename(PrivateKey k, String filename) throws IOException {
        if (filename == null) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        writePrivateKeyToFile(k, new File (filename));
    }

    /**
     * Writes the given public key to the file represented by the given File object.
     * The key is in the form: knapsack, q, r
     * @param k A PrivateKey object to write to a file
     * @param file A file object that represents the file to write to
     * @throws IOException
     */
    public static void writePrivateKeyToFile(PrivateKey k, File file) throws IOException {
        if (file == null || !Util.isValidCreateFile(file.getName())) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        if (k == null || k.getKnapsackSize() < 1 || !k.isValid()) {
            k.clearKey();
            throw new RuntimeException ("Invalid key, won't write.");
        }

        BufferedWriter w = new BufferedWriter (new FileWriter (file));
        
        for (int i = 0; i < k.getKnapsackSize(); i++) {
            if (k.getFromKnapsack(i) == null) {
                file.delete();
                k.clearKey();
                throw new RuntimeException ("Invalid key, won't write.");
            }
            w.write(new BigInteger(k.getFromKnapsack(i).toString()).toString() + " ");
        }
        w.write(new BigInteger(k.getQ().toString()).toString() + " " + new BigInteger(k.getR().toString()).toString());
        w.close();
    }

    /**
     * Writes the given private key to the file at the given path.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PublicKey object to write to file
     * @param filename The path to the file to write the private key to
     * @throws IOException
     */
    public static void writePublicKeyToFilename(PublicKey k, String filename) throws IOException{
        if (filename == null) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        writePublicKeyToFile(k, new File (filename));
    }

    /**
     * Writes the given private key to the file represented by the given File object.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PublicKey object to write to file
     * @param file A File object representing the file to write the private key to
     * @throws IOException
     */
    public static void writePublicKeyToFile(PublicKey k, File file) throws IOException, RuntimeException {
        if (file == null || !Util.isValidCreateFile(file.getName())) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        if (k == null || k.getKnapsackSize() < 1) {
            throw new RuntimeException ("Invalid key, won't write.");
        }

        BufferedWriter w = new BufferedWriter (new FileWriter (file));

        for (int i = 0; i < k.getKnapsackSize(); i++) {
            if (k.getFromKnapsack(i) == null) {
                k.clearKey();
                file.delete();
                throw new RuntimeException ("Invalid key, won't write.");
            }
            w.write(new BigInteger(k.getFromKnapsack(i).toString()).toString() + " ");
        }
        w.close();
    }

    public static PrivateKey readPrivateKeyFromFilename(String filename) throws IOException {
        if (Util.isValidFile(filename)) {
            return readPrivateKeyFromFile(new File(filename));
        } else {
            return null;
        }
    }

    public static PrivateKey readPrivateKeyFromFile(File file) throws IOException {
        if (Util.isValidFile(file.getName())) {
            BufferedReader r = new BufferedReader (new FileReader (file));
            if (! r.ready()) {
                return null;
            }
            
            String[] key = r.readLine().split(" ");
            if (key == null || key.length < 3) {
                key = null;
                return null;
            }

            PrivateKey publicKey = new PrivateKey();
            BigInteger kq = new BigInteger("0"), kr = new BigInteger("0"), kx = new BigInteger("0");
            int i;
            for (i = 0; i < key.length -2; i++) {
                if (key[i] == null) {
                    publicKey.clearKey();
                    return null;
                }
                try {
                    kx = new BigInteger(key[i]);
                    publicKey.addToKnapsack(kx);
                } catch (NumberFormatException e) {
                    publicKey.clearKey();
                    return null;
                }
            }

            try {
                kq = new BigInteger(key[i++]);
                kr = new BigInteger(key[i]);
                publicKey.setQ(kq);
                publicKey.setR(kr);
            } catch (NumberFormatException e) {
                kq = BigInteger.valueOf(0);
                kr = BigInteger.valueOf(0);
                publicKey.clearKey();
                return null;
            }
            if (publicKey.isValid()) {
                return publicKey;
            } else {
                publicKey.clearKey();
                return null;
            }
        }
        return null;

    }

    public static PublicKey readPublicKeyFromFilename(String filename) throws IOException {
        return readPublicKeyFromFile(new File (filename));
    }

    public static PublicKey readPublicKeyFromFile(File file) throws IOException {
        BufferedReader r = new BufferedReader (new FileReader (file));
        String[] key = r.readLine().split(" ");
        PublicKey privateKey = new PublicKey();
        int i;
        for (i = 0; i < key.length; i++) {
            privateKey.addToKnapsack(new BigInteger(key[i]));
        }

        return privateKey;
    }




}
