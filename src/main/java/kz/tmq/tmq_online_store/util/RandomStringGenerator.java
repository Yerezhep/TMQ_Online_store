package kz.tmq.tmq_online_store.util;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RandomStringGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32).toUpperCase();
    }
}
