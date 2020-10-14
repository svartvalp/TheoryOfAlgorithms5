package prac7;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("while (b > 02) done a:=0a;");
        List<Token> tokenList = lexer.getAllTokens();
        new Parser(tokenList).parse();
        lexer.getTokenEntries().forEach(entry -> System.out.println(entry.getValue()));
    }
}
