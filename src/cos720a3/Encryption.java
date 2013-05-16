package cos720a3;

import java.util.ArrayList;

/**
 * Implementation of the Merkle-Hellman encryption.
 * @author phlippie
 */
public class Encryption {
    
    public static Integer [] encryptTextStringAsNumbers (String plaintext, PublicKey key) {
        if (plaintext == null || plaintext.length() == 0 || key == null || key.getKnapsackSize() == 0) {
            return null;
        }
        String bPlaintext = Util.stringToBinaryString(plaintext);
        if (bPlaintext == null || bPlaintext.length() == 0) {
            return null;
        }

        Integer [] result;

        int keySize = key.getKnapsackSize();
        int numberOfPasses = bPlaintext.length() / keySize;
        result = new Integer [numberOfPasses];
        for (int i = 0; i < numberOfPasses; i++ ) {
            String bPlaintextSubstring = bPlaintext.substring(i*keySize,(i+1)*keySize);
            if (bPlaintextSubstring == null || bPlaintextSubstring.length() == 0) {
                return null;
            }
            result[i] = encryptBlock(bPlaintextSubstring, key);
            if (result[i] == null) {
                return null;
            }
        }


        return result;
    }

    public static Integer encryptBlock(String binaryBlock, PublicKey key) {
        if (binaryBlock == null || key == null || binaryBlock.length() != key.getKnapsackSize()) {
            return null;
        }

        ArrayList<Integer> C = new ArrayList<Integer>();
        for (int i = 0; i < key.getKnapsackSize(); i++) {
            try {
                if (key.getFromKnapsack(i) == null) {
                    return null;
                }
                if (binaryBlock.charAt(i) == '1') {
                    C.add(key.getFromKnapsack(i));
                }
            } catch (IndexOutOfBoundsException ioobe) {
                return null;
            }
        }
        Integer blockTotal = 0;
        for (int i = 0; i < C.size(); i++) {
            blockTotal += C.get(i);
        }
        return blockTotal;
    }

}
