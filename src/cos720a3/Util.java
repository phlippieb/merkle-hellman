package cos720a3;

import java.math.BigInteger;

public class Util {
static int gcd(int x, int y) {
        BigInteger b1 = new BigInteger(Integer.toString(x));
        BigInteger b2 = new BigInteger(Integer.toString(y));
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}
