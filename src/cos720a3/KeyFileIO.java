package cos720a3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
     * @param k A PrivateKey object to write to a file
     * @param filename The path to the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFilename(PrivateKey k, String filename) throws IOException {
        if (filename == null) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        writePublicKeyToFile(k, new File (filename));
    }

    /**
     * Writes the given public key to the file represented by the given File object.
     * The key is in the form: knapsack, q, r
     * @param k A PrivateKey object to write to a file
     * @param file A file object that represents the file to write to
     * @throws IOException
     */
    public static void writePublicKeyToFile(PrivateKey k, File file) throws IOException {
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
            w.write(new Double(k.getFromKnapsack(i)).toString() + " ");
        }
        w.write(new Double(k.getQ()).toString() + " " + new Double(k.getR()).toString());
        w.close();
    }

    /**
     * Writes the given private key to the file at the given path.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PublicKey object to write to file
     * @param filename The path to the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFilename(PublicKey k, String filename) throws IOException{
        if (filename == null) {
            throw new RuntimeException ("Outputfile is invalid");
        }
        writePrivateKeyToFile(k, new File (filename));
    }

    /**
     * Writes the given private key to the file represented by the given File object.
     * The private key is simply an ArrayList of integers that represent a knapsack derived from the public key.
     * @param k A PublicKey object to write to file
     * @param file A File object representing the file to write the private key to
     * @throws IOException
     */
    public static void writePrivateKeyToFile(PublicKey k, File file) throws IOException, RuntimeException {
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
            w.write(new Double(k.getFromKnapsack(i)).toString() + " ");
        }
        w.close();
    }

    public static PrivateKey readPublicKeyFromFilename(String filename) throws IOException {
        if (Util.isValidFile(filename)) {
            return readPublicKeyFromFile(new File(filename));
        } else {
            return null;
        }
    }

    public static PrivateKey readPublicKeyFromFile(File file) throws IOException {
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
            Double kq = 0.0, kr = 0.0, kx = 0.0;
            int i;
            for (i = 0; i < key.length -2; i++) {
                if (key[i] == null) {
                    publicKey.clearKey();
                    return null;
                }
                try {
                    kx = Double.parseDouble(key[i]);
                    publicKey.addToKnapsack(kx);
                } catch (NumberFormatException e) {
                    publicKey.clearKey();
                    return null;
                }
            }

            try {
                kq = Double.parseDouble(key[i++]);
                kr = Double.parseDouble(key[i]);
                publicKey.setQ(kq);
                publicKey.setR(kr);
            } catch (NumberFormatException e) {
                kq = 0.0;
                kr = 0.0;
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

    public static PublicKey readPrivateKeyFromFilename(String filename) throws IOException {
        return readPrivateKeyFromFile(new File (filename));
    }

    public static PublicKey readPrivateKeyFromFile(File file) throws IOException {
        BufferedReader r = new BufferedReader (new FileReader (file));
        String[] key = r.readLine().split(" ");
        PublicKey privateKey = new PublicKey();
        int i;
        for (i = 0; i < key.length; i++) {
            privateKey.addToKnapsack(Double.parseDouble(key[i]));
        }

        return privateKey;
    }




}
