package com.javarush.cryptanalyzer.ryabov;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Cipher {
    private static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String RUSSIAN_ALPHABET_LOWERCASE = RUSSIAN_ALPHABET.toLowerCase(Locale.ROOT);
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "., ?!-:;()\"";

    public Cipher(String text, int key) throws IOException {
        File file = new File("encoded.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(encryptWithShift(text, key)+"\n");
        writer.flush();
    }

    private static String encryptWithShift(String text, int shift) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if(!Character.isLowerCase(c)) {
                    int index = RUSSIAN_ALPHABET.indexOf(c);
                    int newIndex = (index + shift + 33) % 33;
                    decryptedText.append(RUSSIAN_ALPHABET.charAt(newIndex));
                } else {
                    int index = RUSSIAN_ALPHABET_LOWERCASE.indexOf(c);
                    int newIndex = (index + shift + 33) % 33;
                    decryptedText.append(RUSSIAN_ALPHABET_LOWERCASE.charAt(newIndex));
                }
            } else {
                if (Character.isDigit(c)) {
                    int index = NUMBERS.indexOf(c);
                    int newIndex = (index + shift + 10) % 10;
                    decryptedText.append(NUMBERS.charAt(newIndex));
                } else {
                    if(SYMBOLS.contains(Character.toString(c))) {
                        int index = SYMBOLS.indexOf(c);
                        int newIndex = (index + shift + 11) % 11;
                        decryptedText.append(SYMBOLS.charAt(newIndex));
                    } else {
                        decryptedText.append(c);
                    }
                }
            }
        }

        return decryptedText.toString();
    }
}


