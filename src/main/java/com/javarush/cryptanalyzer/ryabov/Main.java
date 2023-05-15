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
        System.out.println("Available mode are: cracking, ciphering and deciphering");
        while(true) {
            String mode = console.nextLine();
            if (mode.equals("crack")) {
                new Cracker(sc.nextLine());
                System.out.println("The result of cracking is in output.txt");
                break;
            } else {
                if (mode.equals("decipher")) {
                    System.out.println("Enter the key");
                    int key = Integer.parseInt(console.nextLine());
                    new Decipher(sc.nextLine(), key);
                    System.out.println("The result of deciphering is in output.txt");
                    break;
                } else {
                    if (mode.equals("cipher")) {
                        System.out.println("Enter the key");
                        int key = Integer.parseInt(console.nextLine());
                        new Cipher(sc.nextLine(), key);
                        System.out.println("The result of ciphering is in output.txt");
                        break;
                    } else {
                        System.out.println("No correct mode");
                    }
                }
            }
        }
    }
}
