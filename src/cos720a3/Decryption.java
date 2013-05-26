package cos720a3;

import java.math.BigInteger;

/**
 *
 * @author phlippie
 */
public class Decryption {
    public static String decryptNumbersAsTextString (BigInteger [] ciphertext, PrivateKey key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ciphertext.length; i++) {
            result.append(decryptBinaryBlock(ciphertext[i], key));
        }

        String bString = result.toString();
        System.out.println("Binary string: [" + bString+"]");
        String plainString = Util.binaryStringToString(bString);
        return plainString;
    }

    static String decryptBinaryBlock(BigInteger cryptogram, PrivateKey key) {
        BigInteger s = key.getR().modInverse(key.getQ());
        BigInteger cipher = cryptogram.multiply(s).mod(key.getQ());
        StringBuilder result = new StringBuilder();

        for (int i = key.getKnapsackSize()-1; i >= 0; i--) {
            if (key.getFromKnapsack(i).compareTo(cipher) < 0) {
                result.append("1");
                cipher = cipher.subtract(key.getFromKnapsack(i));
            }
            if (key.getFromKnapsack(i).compareTo(cipher) > 0) {
                result.append("0");
            }
            if (key.getFromKnapsack(i).compareTo(cipher) == 0) {
                break;
            }
        }

        return result.toString();

    }
}
