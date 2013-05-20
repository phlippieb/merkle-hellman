package cos720a3;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * A class with the following properties:
 * *    knapsack, of type ArrayList<Integer>
 * *    q, of type Integer
 * *    r, of type Integer
 * The class will self-destruct if not used correctly, or if an inconsistency is discovered.
 * The correct order and conditions of usage is given below. If any step is performed out of order, repeated, or given invalid parameters, the key will self-destruct.
 * 1) PrivateKey() - construct a new key
 * 2) addToKnapsack(Integer i) - add items to knapsack, as many times as required. Items must be superincreasing.
 * 3) setQ (Integer q) - set the key's q value. Q must be larger than the sum of the knapsack.
 * 4) setR (Integer r) - set the key's r value. R must be co-prome with q, i.e the greatest common divisor of q and r must be 1.
 * 5) getFromKnapsack(int index), getQ(), getR, derivePublicKey() - after 1-4 are done, these methods may be called repeatedly and in any order.
 * Public functions not mentioned above can be used at any time.
 * @author phlippie
 */
public class PrivateKey {
    private ArrayList<BigInteger> knapsack;
    private BigInteger q, r;

    /**
     * Initialise a new key with null values for q and r, and an empty knapsack.
     */
    public PrivateKey () {
        this.knapsack = new ArrayList<BigInteger>();
        this.q = null;
        this.r = null;
    }

    /**
     * Add a value to the knapsack. If the value is invalid (i.e., renders the knapsack non-superincreasing), or if q or r have already been set, or if the knapsack is null, the object will clear itself.
     * @param i The integer value to add to the knapsack.
     * @throws RuntimeException If the added integer is invalid (i.e., renders the knapsack non-superincreasing), or if q or r have already been set (the method was called out of order), or if the knapsack is null, the object will clear itself and throw an exception.
     */
    public void addToKnapsack(BigInteger i) throws RuntimeException {
        if (this.knapsack == null || this.q != null || this.r != null) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        this.knapsack.add(i);
        if (! this.isSuperIncreasing()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
    }

    /**
     * Sets the key's q value.
     * Q must be larger than the sum of the knapsack.
     * If q is invalid, or the method was called out of order (no items have been added to the knapsack or r is already initialised), the object will self-destruct.
     * @param q The value for q. Must be larger than the sum of the knapsack.
     * @throws RuntimeException If q is invalid or the method was called out of order, the object will self-destruct and throw an exception.
     */
    public void setQ (BigInteger q) throws RuntimeException {
        if (this.knapsack == null || q == null) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        this.q = q;
        if (! this.qIsValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
    }

    /**
     * Sets the key's r value.
     * R must be co-prime with q; i.e, the greatest common divisor of q and r must be 1.
     * If r is invalid, or the method was called out of order (the knapsack or q are not yet set), the object will self-destruct.
     * @param r The value to set r to.
     * @throws RuntimeException If r is invalid or called out of order, the object will self-destruct and throw an exception.
     */
    public void setR (BigInteger r) throws RuntimeException {
        if (this.knapsack == null || this.q == null || r == null) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        this.r = r;
        if (! this.rIsValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
    }

    /**
     * Returns a single element from the knapsack, that was stored at the given index (starting from 0).
     * If the method is called out of order (before the knapsack, q, and r have all been set), or with an invalid index, the object will self-destruct.
     * @param index
     * @return
     */
    public BigInteger getFromKnapsack (int index) {
        if (this.knapsack == null || this.knapsack.isEmpty() || this.q == null || this.r == null || !this.isValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        if (this.knapsack.size() < index || index < 0) {
            this.clearKey();
            throw new RuntimeException ("Index is invalid");
        }
        return new BigInteger(this.knapsack.get(index).toString());
    }

    /**
     * Returns the key's q value.
     * If the method is called out of order (before the knapsack, q, and r have all been set), or with an invalid index, the object will self-destruct.
     * @return
     */
    public BigInteger getQ () {
        if (this.knapsack == null || this.knapsack.isEmpty() || this.q == null || this.r == null || !this.isValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        return new BigInteger(this.q.toString());
    }

    /**
     * Returns the key's r value.
     * If the method is called out of order (before the knapsack, q, and r have all been set), or with an invalid index, the object will self-destruct.
     * @return
     */
    public BigInteger getR () {
        if (this.knapsack == null || this.knapsack.isEmpty() || this.q == null || this.r == null || !this.isValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid.");
        }
        return new BigInteger(this.r.toString());
    }

    /**
     * Returns the number of elements in the key's knapsack.
     * This method may be called at any time.
     * @return The number of elements in the knapsack, or -1 if the knapsack is null.
     */
    public int getKnapsackSize () {
        if (this.knapsack == null) {
            return -1;
        }
        return this.knapsack.size();
    }

    /**
     * Returns true if the key is a valid Merkle-Hellman public key. This is the case if:
     * *    The knapsack is super-increasing; i.e. each item is larger than the sum of all previous items
     * *    q is larger than the sum of the knapsack
     * *    r is co-prime with q, i.e. the greatest common denominator between q and r is 1
     * This method may be called at any time.
     * @return True if the above criteria is met, false otherwise
     */
    public boolean isValid() {
        if (knapsack == null || knapsack.isEmpty()) {
            return false;
        }

        BigInteger runningTotal = knapsack.get(0);
        //test if knapsack is super-increasing - i.e., if each item is larger than the sum of each previous item
        for (int i = 1; i < knapsack.size(); i++) {
            if (knapsack.get(i).compareTo(runningTotal) == -1) {
                return false;
            }
            runningTotal = runningTotal.add(knapsack.get(i));
        }

        //test if q is larger than the total sum of the knapsack
        if (q.compareTo(runningTotal) == -1) {
            return false;
        }

        //test if r and q are co-prime
        if (q.gcd(r).compareTo(new BigInteger("1")) != 0) {
            return false;
        }

        // passed all tests
        return true;
    }

    /**
     * Tests whether the elements in the knapsack are superincreasing, i.e each element is larger than the sum of all preceding elements.
     * The test is run on the items in the order in which they were added.
     * This method may be called at any time.
     * @return True if the knapsack is valid and its elements are superincreasing, False if not or knapsack is null.
     */
    public boolean isSuperIncreasing () {
        if (knapsack == null || knapsack.isEmpty()) {
            return false;
        }

        BigInteger runningTotal = new BigInteger("0");
        //test if knapsack is super-increasing - i.e., if each item is larger than the sum of each previous item
        for (int i = 0; i < knapsack.size(); i++) {
            if (knapsack.get(i) == null || knapsack.get(i).compareTo(runningTotal) == -1) {
                return false;
            }
            runningTotal = runningTotal.add(knapsack.get(i));
        }
        return true;
    }

    /**
     * Tests whether q is larger than the sum of the knapsack.
     * This method may be called at any time.
     * @return True if q is larger than the sum of the knapsack, False if not or knapsack is null or contains invalid characters.
     */
    public boolean qIsValid () {
        if (this.knapsack == null || !this.isSuperIncreasing()) {
            return false;
        }
        BigInteger knapsackTotal = new BigInteger("0");
        for (int i = 0; i < this.knapsack.size(); i++) {
            if (this.knapsack.get(i) == null) {
                return false;
            }
            knapsackTotal = knapsackTotal.add(this.knapsack.get(i));
        }
        return q.compareTo(knapsackTotal) == 1;
    }

    /**
     * Tests whether r is coprime with q.
     * This method may be called at any time.
     * @return True if r and q are co-prime, False if not or if q or r are null or if q is invalid.
     */
    public boolean rIsValid () {
        if (this.r == null || this.q == null || !this.qIsValid()) {
            return false;
        }
        return this.q.gcd(this.r).compareTo(new BigInteger("1")) == 0;
    }

    /**
     * Creates a new PublicKey object whose knapsack is a derivitive of this public key.
     * The private knapsack is a collection of numbers B = B(B1, B2, ...), where
     *      Bi = rWi mod q, where
     *      Wi is the i'th element in the public knapsack.
     * This method may be called once the knapsack, q and r have all been set. If called out of order, the object will self-destruct.
     * @return A PublicKey object that is the derivative of this key.
     * @throws If the method is called out of order, or this key is invalid, the object will self-destruct and throw a runtime exception.
     */
    public PublicKey derivePublicKey () throws RuntimeException {
        if (this.knapsack == null || this.knapsack.isEmpty() || this.q == null || this.r == null || !this.isValid()) {
            this.clearKey();
            throw new RuntimeException ("Key is invalid");
        }

        PublicKey privateKey = new PublicKey();
        for (int i = 0; i < knapsack.size(); i++) {
            privateKey.addToKnapsack(r.multiply(knapsack.get(i).mod(q)));
        }

        return privateKey;
    }

    /**
     * Returns a string representation of the object.
     * The string consists of all the elements of the knapsack, followed by q and r, each separated by a single space.
     * If the key is invalid, the returned string is empty.
     * This method may be called at any time.
     * @return
     */
    @Override
    public String toString() {
        if (this.knapsack == null || this.knapsack.isEmpty() || this.q == null || this.r == null || !this.isValid()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.knapsack.size(); i++) {
            if (this.knapsack.get(i) == null) {
                sb = null;
                return "";
            }
            sb.append(knapsack.get(i).toString());
            sb.append(" ");
        }

        if (this.q == null) {
            sb = null;
            return "";
        }
        sb.append(q.toString());
        
        if (this.r == null) {
            sb = null;
            return "";
        }
        sb.append(" ");
        sb.append(r.toString());
        
        return sb.toString();
    }

    /**
     * The self-destruction method. This clears all data in the key.
     */
    public void clearKey () {
        if (this.knapsack != null) {
            this.knapsack.clear();
            this.knapsack = null;
        }
        this.q = null;
        this.r = null;
    }
}
