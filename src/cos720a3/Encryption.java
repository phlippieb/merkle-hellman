package cos720a3;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Implementation of the Merkle-Hellman encryption.
 * @author phlippie
 */
public class Encryption {
    
    public static BigInteger [] encryptTextStringAsNumbers (String plaintext, PublicKey key) {
        if (plaintext == null || plaintext.length() == 0 || key == null || key.getKnapsackSize() == 0) {
            return null;
        }
        String bPlaintext = Util.stringToBinaryString(plaintext);
        if (bPlaintext == null || bPlaintext.length() == 0) {
            return null;
        }

        BigInteger [] result;

        int keySize = key.getKnapsackSize();
        int numberOfPasses = (int)Math.ceil((double)(bPlaintext.length())/(double)keySize);
        result = new BigInteger [numberOfPasses];
        for (int i = 0; i < numberOfPasses; i++ ) {
            String bPlaintextSubstring = bPlaintext.substring(i*keySize, Math.min((i+1)*keySize,bPlaintext.length()));
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

    public static BigInteger encryptBlock(String binaryBlock, PublicKey key) {
        if (binaryBlock == null || key == null || binaryBlock.length() > key.getKnapsackSize()) {
            return null;
        }

        ArrayList<BigInteger> C = new ArrayList<BigInteger>();
        for (int i = 0; i < binaryBlock.length(); i++) {
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
        BigInteger blockTotal = BigInteger.valueOf(0);
        for (int i = 0; i < C.size(); i++) {
            blockTotal = blockTotal.add(C.get(i));
        }
        return blockTotal;
    }

}
