package com.javarush.cryptanalyzer.ryabov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Cracker {
    private static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String RUSSIAN_ALPHABET_LOWERCASE = RUSSIAN_ALPHABET.toLowerCase(Locale.ROOT);
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "., ?!-:;()\"";
    private static final double[] RUSSIAN_FREQUENCIES = {0.075, 0.017, 0.046, 0.016, 0.03, 0.087, 0.087, 0.009, 0.018, 0.075, 0.012, 0.034, 0.042, 0.031, 0.065, 0.110, 0.028, 0.048, 0.055, 0.065, 0.025, 0.002, 0.011, 0.005, 0.015, 0.007, 0.004, 0.017, 0.017, 0.019, 0.003, 0.022, 0.022};

    public Cracker(String text) throws IOException {
        File file = new File("output.txt");
        file.createNewFile();
        String decryptedText = breakCaesarCipher(text);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(decryptedText+"\n");
        fileWriter.flush();
    }

    private static String breakCaesarCipher(String cipherText) {
        int bestShift = -1;
        double bestChiSquared = Double.MAX_VALUE;

        for (int shift = 0; shift < 33; shift++) {
            String decryptedText = decryptWithShift(cipherText.replaceAll("[^а-яА-Я]",""), shift);
            double chiSquared = calculateChiSquared(decryptedText.replaceAll("[^а-яА-Я]", ""));

            if (chiSquared < bestChiSquared) {
                bestShift = shift;
                bestChiSquared = chiSquared;
            }
        }

        return decryptWithShift(cipherText, bestShift);
    }

    private static String decryptWithShift(String text, int shift) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if(!Character.isLowerCase(c)) {
                    int index = RUSSIAN_ALPHABET.indexOf(c);
                    int newIndex = (index - shift + 33) % 33;
                    decryptedText.append(RUSSIAN_ALPHABET.charAt(newIndex));
                } else {
                    int index = RUSSIAN_ALPHABET_LOWERCASE.indexOf(c);
                    int newIndex = (index - shift + 33) % 33;
                    decryptedText.append(RUSSIAN_ALPHABET_LOWERCASE.charAt(newIndex));
                }
            } else {
                if (Character.isDigit(c)) {
                    int index = NUMBERS.indexOf(c);
                    int newIndex = (index - shift + 10) % 10;
                    decryptedText.append(NUMBERS.charAt(newIndex));
                } else {
                    if(SYMBOLS.contains(Character.toString(c))) {
                    int index = SYMBOLS.indexOf(c);
                    int newIndex = (index - shift + 11) % 11;
                    decryptedText.append(SYMBOLS.charAt(newIndex));
                } else {
                        decryptedText.append(c);
                    }
                }
            }
        }

        return decryptedText.toString();
    }

    private static double calculateChiSquared(String text) {
        int[] letterFrequencies = new int[33];
        int letterCount = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int index;
                if(!Character.isLowerCase(c)) {
                    index = RUSSIAN_ALPHABET.indexOf(c);
                } else {
                    index = RUSSIAN_ALPHABET_LOWERCASE.indexOf(c);
                }
                letterFrequencies[index]++;
                letterCount++;
            }
        }

        double chiSquared = 0.0;

        for (int i = 0; i < 33; i++) {
            double observedFrequency = (double) letterFrequencies[i] / letterCount;
            double expectedFrequency = RUSSIAN_FREQUENCIES[i];
            double difference = observedFrequency - expectedFrequency;
            chiSquared += (difference * difference) / expectedFrequency;
        }

        return chiSquared;
    }
}
