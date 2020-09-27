package com.svartvalp.prac3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private String input;
    private int index;

    private static final Map<String, TokenType> SYMBOL_MAP;

    static {
        SYMBOL_MAP = new LinkedHashMap<>();
        SYMBOL_MAP.put("for", TokenType.KEY_WORD);
        SYMBOL_MAP.put("do", TokenType.KEY_WORD);
        SYMBOL_MAP.put(">=", TokenType.MORE_OR_EQUAL);
        SYMBOL_MAP.put("<=", TokenType.LESS_OR_EQUAL);
        SYMBOL_MAP.put(":=", TokenType.ASSIGN);
        SYMBOL_MAP.put("!=", TokenType.NOT_EQUAL);
        SYMBOL_MAP.put("(", TokenType.LPAR);
        SYMBOL_MAP.put(")", TokenType.RPAR);
        SYMBOL_MAP.put(";",TokenType.END_S);
        SYMBOL_MAP.put("=", TokenType.EQUAL);
        SYMBOL_MAP.put("<", TokenType.LESS);
        SYMBOL_MAP.put("*",TokenType.MULTI);
        SYMBOL_MAP.put("+", TokenType.ADD);
        SYMBOL_MAP.put("-", TokenType.SUB);
        SYMBOL_MAP.put("/", TokenType.DIV);
        SYMBOL_MAP.put(">", TokenType.MORE);
        SYMBOL_MAP.put("!", TokenType.FACTOR);
    }

    public Lexer(String input) {
        this.input = input;
        this.index = 0;
    }

    private int match(String pattern) {
        return match(Pattern.compile(pattern));
    }

    private int match(Pattern pattern) {
        Matcher matcher = pattern.matcher(input);
        matcher.region(index, input.length());
        if(matcher.lookingAt()) {
            return matcher.end();
        }
        return -1;
    }

    private Token matchNumber() {
        int matched = match("[0-9][0-9a-z]*");
        return matched > 0 ? new Token(TokenType.NUMBER, input.substring(index, matched), index, matched) : null;
    }

    private Token matchAnySymbol() {
        for(Map.Entry<String, TokenType> entry : SYMBOL_MAP.entrySet()) {
            String key = entry.getKey();
            TokenType value = entry.getValue();
            int matched = match(Pattern.quote(key));
            if(matched > 0) {
                return new Token(value, input.substring(index, matched), index, matched);
            }
        }
        return null;
    }

    private Token matchSpaces() {
        int i = index;
        while(i < input.length()) {
            if(Character.isSpaceChar(input.charAt(i))) {
                i++;
            } else {
                break;
            }
        }
        return i > index ? new Token(TokenType.SPACES, input.substring(index, i), index, i) : null;
    }

    private Token matchVariable() {
        int matched = match("[a-zA-Z]+[\\w]*");
        return matched > 0 ? new Token(TokenType.VAR, input.substring(index, matched), index, matched) : null;
    }


    private Token matchAnyToken() {
        if(index >= input.length()) {
            return null;
        }
        Token token;

        token = matchSpaces();
        if(token == null) {
            token = matchNumber();
        }
        if(token == null) {
            token = matchAnySymbol();
        }
        if(token == null) {
            token = matchVariable();
        }

        if(token == null) {
            throw new ParseException("Unexpected characted", index);
        }

        return token;
    }

    private Token nextToken() {
        while (true) {
            Token token = matchAnyToken();
            if(token == null) {
                return token;
            }
            index = token.getTo();
            if(token.getType() != TokenType.SPACES) {
                return token;
            }
        }
    }

    public List<Token> getAllTokens() {
        List<Token> tokens = new ArrayList<>();
        while (true) {
            Token token = nextToken();
            if(token == null) {
                break;
            }
            tokens.add(token);
        }
        return tokens;
    }

}
