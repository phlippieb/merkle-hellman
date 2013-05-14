package cos720a3;

import java.util.ArrayList;

/**
 * A simple class representation of a private key with the following public property:
 * *    knapsack, of type ArrayList<Integer>
 * @author phlippie
 */
public class PrivateKey {
    public ArrayList<Integer> knapsack;

    public PrivateKey() {
        this.knapsack = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        if (knapsack.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder ();
        for (int i = 0; i < knapsack.size()-1; i++) {
            sb.append(knapsack.get(i).toString() + " ");
        }
        sb.append(knapsack.get(knapsack.size()-1));
        return sb.toString();
    }
}
