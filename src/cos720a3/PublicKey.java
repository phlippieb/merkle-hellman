package cos720a3;

import java.util.ArrayList;

/**
 * A simple class with the following public properties:
 * *    knapsack, of type ArrayList<Integer>
 * *    q, of type Integer
 * *    r, of type Integer
 * @author phlippie
 */
public class PublicKey {
    public ArrayList<Integer> knapsack;
    public Integer q, r;

    public PublicKey () {
        this.knapsack = new ArrayList<Integer>();
    }

    /**
     * Returns true if the key is a valid Merkle-Hellman public key. This is the case if:
     * *    The knapsack is super-increasing; i.e. each item is larger than the sum of all previous items
     * *    q is larger than the sum of the knapsack
     * *    r is co-prime with q, i.e. the greatest common denominator between q and r is 1
     * @return True if the above criteria is met, false otherwise
     */
    public boolean isValid() {
        Integer runningTotal = knapsack.get(0);
        //test if knapsack is super-increasing - i.e., if each item is larger than the sum of each previous item
        for (int i = 1; i < knapsack.size(); i++) {
            if (knapsack.get(i) < runningTotal) {
                return false;
            }
            runningTotal += knapsack.get(i);
        }
        //test if q is larger than the total sum of the knapsack
        if (q < runningTotal) {
            return false;
        }
        //test if r and q are co-prime
        if (Util.gcd(q, r) != 1) {
            return false;
        }
        // passed all tests
        return true;
    }

    /**
     * Creates a new PrivateKey object whose knapsack is a derivitive of this public key.
     * The private knapsack is a collection of numbers B = B(B1, B2, ...), where
     *      Bi = rWi mod q, where
     *      Wi is the i'th element in the public knapsack.
     * @return
     */
    public PrivateKey derivePrivateKey () {
        PrivateKey privateKey = new PrivateKey();
        privateKey.knapsack = new ArrayList<Integer>();

        for (int i = 0; i < knapsack.size(); i++) {
            privateKey.knapsack.add(r * knapsack.get(i) % q);
        }

        return privateKey;
    }

    /**
     * Returns a string representation of the object.
     * The string consists of all the elements of the knapsack, followed by q and r, each separated by a single space.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < knapsack.size(); i++) {
            sb.append(knapsack.get(i).toString());
            sb.append(" ");
        }
        sb.append(q.toString());
        sb.append(" ");
        sb.append(r.toString());
        
        return sb.toString();
    }
}
