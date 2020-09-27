package com.svartvalp.prac4;

import com.svartvalp.prac3.Lexer;
import com.svartvalp.prac3.Token;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        List<Token> tokens = new Lexer(input).getAllTokens();
        tokens.forEach(System.out::println);
        new Parser(tokens).parseExpression();
    }
}
