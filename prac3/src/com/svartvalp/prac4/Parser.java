package com.svartvalp.prac4;

import com.svartvalp.prac3.ParseException;
import com.svartvalp.prac3.Token;
import com.svartvalp.prac3.TokenType;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int index;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }


    private Token match(TokenType type) {
        if(index >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(index);
        if(token.getType() != type) {
            return  null;
        }
        index++;
        return token;
    }


    private void error(String messsage) {
        int errorPosition;
        if(index >= tokens.size()) {
            if(tokens.isEmpty()) {
                errorPosition = 0;
            } else {
                errorPosition = tokens.get(tokens.size() - 1).getTo();
            }
        } else {
            Token token = tokens.get(index);
            errorPosition = token.getIndex();
        }
        throw new ParseException(messsage, errorPosition);
    }

    public void parseExpression() {
        parseTerm();
        while(true) {
            if ((match(TokenType.ADD) != null) || (match(TokenType.SUB) != null)) {
                parseTerm();
            } else {
                break;
            }
        }
    }

    private void parseTerm() {
        parseFactor();
        while (true) {
            if((match(TokenType.MULTI) != null) || (match(TokenType.DIV) != null)) {
                parseFactor();
            } else {
                break;
            }
        }
    }

    private void parseFactor() {
        if(tokens.get(index).getType().equals(TokenType.SUB)) {
            index++;
        }
        Token number = match(TokenType.NUMBER);
        if(number == null) {
            error("Missing number");
        }
        if(index < tokens.size() && tokens.get(index).getType().equals(TokenType.FACTOR)) {
            index++;
        }
    }

}
