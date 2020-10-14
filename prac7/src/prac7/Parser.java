package prac7;

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

    public void parse()  {
        if("while".equals(match(TokenType.KEY_WORD)) && match(TokenType.LPAR) != null) {
            parseExpression();
            match(TokenType.RPAR);
            match(TokenType.KEY_WORD);
            parseExpression();
            match(TokenType.END_S);
        }
        if(index > tokens.size()) {
            parse();
        }
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
        if(match(TokenType.NUMBER) != null || match(TokenType.VAR) != null) {
            error("Missing identifier");
        }
        if(index < tokens.size() && tokens.get(index).getType().equals(TokenType.FACTOR)) {
            index++;
        }
    }

}
