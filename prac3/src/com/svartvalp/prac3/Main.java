package com.svartvalp.prac3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        Lexer lexer = new Lexer(input);
        lexer.getAllTokens().forEach(System.out::println);
    }
}
