package com.javarush.cryptanalyzer.ryabov;

import com.javarush.cryptanalyzer.ryabov.Cipher;
import com.javarush.cryptanalyzer.ryabov.Cracker;
import com.javarush.cryptanalyzer.ryabov.Decipher;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        Scanner sc = new Scanner(new FileReader("input.txt"));
        System.out.println("Welcome! What can I interest you with?");
        System.out.println("Available mode are: \"crack\", \"cipher\" and \"decipher\"");
        System.out.println("If you are doubting you can type \"back\" in cipher and decipher mode");
        boolean shouldContinue = true;
        String text = sc.nextLine();
        while(shouldContinue) {
            String mode = console.nextLine();
            switch (mode) {
                case "crack" -> {
                    new Cracker(text);
                    System.out.println("The result of cracking is in output.txt");
                    shouldContinue = false;
                }
                case "decipher" -> {
                    System.out.println("Enter the key");
                    while(true) {
                        try {
                            String key = console.nextLine();
                            if(key.equals("back")) {
                                System.out.println("Available mode are: \"crack\", \"cipher\" and \"decipher\"");
                                break;
                            }
                            new Decipher(text, Integer.parseInt(key));
                            System.out.println("The result of deciphering is in output.txt");
                            shouldContinue = false;
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a valid key");
                        }
                    }
                }
                case "cipher"-> {
                    System.out.println("Enter the key");
                    while(true) {
                        try {
                            String key = console.nextLine();
                            if(key.equals("back")) {
                                System.out.println("Available mode are: \"crack\", \"cipher\" and \"decipher\"");
                                break;
                            }
                            new Cipher(text, Integer.parseInt(key));
                            System.out.println("The result of ciphering is in encoded.txt");
                            shouldContinue = false;
                            break;
                        } catch (Exception e) {
                            System.out.println("Enter a valid key");
                        }
                    }
                }
                default -> System.out.println("No correct mode");
            }
        }
    }
}

