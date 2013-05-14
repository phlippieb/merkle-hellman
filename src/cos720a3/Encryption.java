package cos720a3;

import java.util.ArrayList;

/**
 * Implementation of the Merkle-Hellman encryption.
 * @author phlippie
 */
public class Encryption {
    public static Integer [] encryptTextStringAsNumbers (String plaintext, PublicKey key) {
        String bPlaintext = Util.stringToBinaryString(plaintext);
        Integer [] result;

        int keySize = key.knapsack.size();
        int numberOfPasses = bPlaintext.length() / keySize;
        result = new Integer [numberOfPasses];
        for (int i = 0; i < numberOfPasses; i++ ) {
            String bPlaintextSubstring = bPlaintext.substring(i*keySize,(i+1)*keySize);
            result[i] = encryptBlock(bPlaintextSubstring, key);
        }


        return result;
    }

    public static Integer encryptBlock(String binaryBlock, PublicKey key) {
        ArrayList<Integer> C = new ArrayList<Integer>();
        for (int i = 0; i < key.knapsack.size(); i++) {
            if (binaryBlock.charAt(i) == '1') {
                C.add(key.knapsack.get(i));
            }
        }
        Integer blockTotal = 0;
        for (int i = 0; i < C.size(); i++) {
            blockTotal += C.get(i);
        }
        return blockTotal;
    }

}
