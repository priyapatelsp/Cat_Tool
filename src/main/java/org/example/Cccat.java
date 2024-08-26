package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Cccat {

    public static void main(String[] args) {
        System.out.println("::::   Welcome to GREP TOOL   ::::");

            System.out.println("grep [-r] [-v] [-i] <pattern> <directory or filename>");
            System.out.println("---------------------------------------------------------");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            // if the user want to exit
            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("THANKS :)");

            }
            String[] partsInput = userInput.split(" ");
        boolean numberLines = false; // Number non-blank lines
        boolean numberAllLines = false; // Number all lines, including blanks
        int startIndex = 0;

        // Check for options

        if (partsInput.length > 0 && "-n".equals(partsInput[0])) {
            numberLines = true;
            startIndex = 1;
        } else if (partsInput.length > 0 && "-b".equals(partsInput[0])) {
            numberAllLines = true;
            startIndex = 1;
        } else {
            startIndex = 0;
        }

        if (partsInput.length <= startIndex) {
            System.err.println("Usage: java Cccat [-n|-b] <file1> [<file2> ...]");
            System.exit(1);
        }

        int lineNumber = 1; // Line numbering starts from 1

        for (int i = startIndex; i < partsInput.length; i++) {
            String arg = partsInput[i];

            if ("-".equals(arg)) {
                // Read from standard input
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        if (numberLines && !line.isEmpty()) {
                            System.out.println(lineNumber++ + "  " + line);
                        } else if (numberAllLines) {
                            System.out.println(lineNumber++ + "  " + line);
                        } else {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from standard input: " + e.getMessage());
                    System.exit(1);
                }
            } else {
                // Read from the specified file
                Path filePath = Paths.get(arg);
                try {
                    BufferedReader fileReader = Files.newBufferedReader(filePath);
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        if (numberLines && !line.isEmpty()) {
                            System.out.println(lineNumber++ + "  " + line);
                        } else if (numberAllLines) {
                            System.out.println(lineNumber++ + "  " + line);
                        } else {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file " + arg + ": " + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }
}
