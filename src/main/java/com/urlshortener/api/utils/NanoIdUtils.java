package com.urlshortener.api.utils;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class NanoIdUtils {
    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();
    private static final char[] DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String randomNanoId(SecureRandom random, char[] alphabet, int size) {
        if (random == null) {
            random = DEFAULT_NUMBER_GENERATOR;
        }

        if (alphabet == null || alphabet.length == 0) {
            alphabet = DEFAULT_ALPHABET;
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }

        final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
        final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

        StringBuilder idBuilder = new StringBuilder();

        while (true) {
            byte[] bytes = new byte[step];
            random.nextBytes(bytes);
            for (int i = 0; i < step; i++) {
                int alphabetIndex = bytes[i] & mask;
                if (alphabetIndex < alphabet.length) {
                    idBuilder.append(alphabet[alphabetIndex]);
                    if (idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }
            }
        }
    }
}
