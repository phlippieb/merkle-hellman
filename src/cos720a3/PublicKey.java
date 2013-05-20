package cos720a3;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * A simple class representation of a private key with the following public property:
 * *    knapsack, of type ArrayList<Integer>
 * @author phlippie
 */
public class PublicKey {
    private ArrayList<BigInteger> knapsack;

    public PublicKey() {
        this.knapsack = new ArrayList<BigInteger>();
    }

    public void addToKnapsack (BigInteger i) {
        if (this.knapsack == null || i == null) {
            throw new RuntimeException ("Key is invalid");
        }
        this.knapsack.add(i);
    }

    public BigInteger getFromKnapsack(int i) {
        if (this.knapsack == null || this.knapsack.isEmpty()) {
            throw new RuntimeException ("Key is invalid");
        }
        if (i < 0 || this.knapsack.size() < i) {
            throw new RuntimeException ("Index is invalid");
        }

        return new BigInteger(this.knapsack.get(i).toString());
    }

    public int getKnapsackSize () {
        if (this.knapsack == null) {
            throw new RuntimeException ("Key is invalid");
        }
        return this.knapsack.size();
    }

    @Override
    public String toString() {
        if (knapsack == null || knapsack.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder ();
        for (int i = 0; i < knapsack.size()-1; i++) {
            if (knapsack.get(i) == null) {
                return "";
            }
            sb.append(knapsack.get(i).toString() + " ");
        }
        if (knapsack.get(knapsack.size()-1) == null) {
            return "";
        }
        sb.append(knapsack.get(knapsack.size()-1));
        return sb.toString();
    }

    public void clearKey() {
        if (this.knapsack != null) {
            this.knapsack.clear();
            this.knapsack = null;
        }
    }
}
