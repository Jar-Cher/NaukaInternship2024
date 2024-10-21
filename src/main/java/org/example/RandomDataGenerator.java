package org.example;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {
    private static final Random rand = new Random();
    private static final int MAX_WAGE = 1_000_000;
    private static final int MAX_LETTERS = 20;
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    public static int getRandomWage() {
        return rand.nextInt(MAX_WAGE);
    }

    public static String getRandomLocalDate() {
        long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2007, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return String.valueOf(randomDate);
    }

    public static String getRandomString() {
        StringBuilder result = new StringBuilder();
        int length = rand.nextInt(2, MAX_LETTERS);
        for (int i = 0; i < length; i++) {
            result.append(LETTERS.charAt(rand.nextInt(LETTERS.length())));
        }
        return result.toString();
    }
}
