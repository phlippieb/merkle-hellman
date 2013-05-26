/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cos720a3;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kinesa
 */
public class Decryption {

    public static byte[] decryptor(String ciphertext, PrivateKey key) {
        byte[] result = new byte[2];
        if (ciphertext == null || ciphertext.length() == 0 || key == null || key.getKnapsackSize() == 0) {
            return null;
        }

        String Binary = "";
        BigInteger r = key.getR().modInverse(key.getQ());
        BigInteger plainText = (new BigInteger(String.valueOf(ciphertext))).multiply(r).mod(key.getQ());
        BigInteger Check = plainText;
        int TmpCheck = 0;

        for (int i = key.getKnapsackSize() - 1; i >= 0; --i) {
            TmpCheck = Check.subtract(key.getFromKnapsack(i)).intValue();

            if (TmpCheck >= 0) {
                Binary = "1" + Binary;
                Check = Check.subtract(key.getFromKnapsack(i));
            } else {
                Binary = "0" + Binary;
            }
        }
        // for each block in the binary String, convert into a byte
        for (int i = 0, j = 0; i < Binary.length(); i += 8, ++j) {
            String tempString = Binary.substring(i, i + 8);
            result[j] = Byte.parseByte(tempString, 2);

        }

        return result;
    }
}