package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cat_tool {

    public static void main(String[] args) {
        System.out.println("WELCOME TO CAT TOOL :");

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


            System.out.println("Below is the list of commands :");
            System.out.println("1.  `<ENTER YOUR FILE PATH FROM CONTENT ROOT>` example: test.txt");
            System.out.println("2.  `EXIT` - To exit from the program ");
            System.out.println("3.  `head -n1 test.txt` - to read the input from from standard in");
            System.out.println("4.  `test.txt test2.txt` - to concatenate files");
            System.out.println("5.  `cat -n test.txt test2.txt` - To number lines (including blank lines)");
            System.out.println("6.  `cat -n -b test.txt test2.txt` - To number lines (excluding blank lines)");
            System.out.println("------------------------");


            try {

                String commandLine = reader.readLine();
                if (commandLine.equalsIgnoreCase("exit")) {
                    System.out.println("Thank you for using this program ");
                    break;
                }
                executeCommand(commandLine);
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

                private static void executeCommand(String commandLine) {

                    String[] commandParts = commandLine.split("\\s+");
                    if (commandParts.length == 0) {
                        System.err.println("No command provided.");
                        return;
                    }

                    if (commandParts[0].equals("head") && commandParts.length > 1) {
                        handleHeadCommand(commandParts);
                    } else if (commandParts[0].equals("cat")) {
                        handleCatCommand(commandParts);
                    } else {
                        List<String> files = new ArrayList<>();
                        for (String part : commandParts) {
                            files.add(part);
                        }
                        concatenateFiles(files);
                    }
                }

                private static void handleHeadCommand(String[] commandParts) {
                    if (commandParts.length < 3 || !commandParts[1].equals("-n")) {
                        System.err.println("Usage: head -n <number> <file1> <file2> ...");
                        return;
                    }

                    int numberOfLines;
                    try {
                        numberOfLines = Integer.parseInt(commandParts[2]);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number of lines: " + commandParts[2]);
                        return;
                    }

                    List<String> files = new ArrayList<>();
                    for (int i = 3; i < commandParts.length; i++) {
                        files.add(commandParts[i]);
                    }

                    printFileHead(files, numberOfLines);
                }

                private static void handleCatCommand(String[] commandParts) {
                    if (commandParts.length < 2 || !commandParts[1].equals("-n")) {
                        System.err.println("Usage: cat -n [-b] <file1> <file2> ...");
                        return;
                    }

                    boolean numberNonBlankOnly = false;
                    int startIdx = 2;
                    if (commandParts.length > 2 && commandParts[2].equals("-b")) {
                        numberNonBlankOnly = true;
                        startIdx = 3;
                    }

                    List<String> files = new ArrayList<>();
                    for (int i = startIdx; i < commandParts.length; i++) {
                        files.add(commandParts[i]);
                    }
                    numberLines(files, numberNonBlankOnly);
                }

                private static void printFileHead(List<String> files, int numberOfLines) {
                    for (String filePath : files) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            int lineCount = 0;
                            while ((line = reader.readLine()) != null && lineCount < numberOfLines) {
                                System.out.println(line);
                                lineCount++;
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                        }
                    }
                }

                private static void numberLines(List<String> files, boolean numberNonBlankOnly) {
                    for (String filePath : files) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            int lineNumber = 1;
                            while ((line = reader.readLine()) != null) {
                                if (numberNonBlankOnly) {
                                    if (!line.trim().isEmpty()) {
                                        System.out.println(lineNumber + " " + line);
                                        lineNumber++;
                                    }
                                } else {
                                    System.out.println(lineNumber + " " + line);
                                    lineNumber++;
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                        }
                    }
                }

                private static void concatenateFiles(List<String> files) {
                    for (String filePath : files) {
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                        }
                    }
                }
}
