package prac7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int index;
    private final TokenTable table;

    public Set<Map.Entry<Integer, Token>> getTokenEntries() {
        return table.tokenSet();
    }

    public Lexer(String input) {
        this.input = input;
        this.index = 0;
        this.table = new TokenTable();
        table.insert(new Token(TokenType.KEY_WORD, "while"));
        table.insert(new Token(TokenType.KEY_WORD, "done"));
        table.insert(new Token(TokenType.MORE_OR_EQUAL, ">="));
        table.insert(new Token(TokenType.LESS_OR_EQUAL, "<="));
        table.insert(new Token(TokenType.ASSIGN, ":="));
        table.insert(new Token(TokenType.NOT_EQUAL, "!="));
        table.insert(new Token(TokenType.LPAR, "("));
        table.insert(new Token(TokenType.RPAR, ")"));
        table.insert(new Token(TokenType.END_S, ";"));
        table.insert(new Token(TokenType.EQUAL, "=="));
        table.insert(new Token(TokenType.LESS, "<"));
        table.insert(new Token(TokenType.MULTI, "*"));
        table.insert(new Token(TokenType.ADD, "+"));
        table.insert(new Token(TokenType.SUB, "-"));
        table.insert(new Token(TokenType.DIV, "/"));
        table.insert(new Token(TokenType.MORE, ">"));
        table.insert(new Token(TokenType.FACTOR, "!"));
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
        if(matched < 0) {
            return null;
        }
        String text = input.substring(index, matched);
        Token token = table.lookup(text);
        if (token == null) {
            token = new Token(TokenType.NUMBER, text, index, matched);
        } else {
            token = new Token(TokenType.NUMBER, token.getText(), index, matched);
        }
        table.insert(token);
        return token;
    }

    private Token matchAnySymbol() {
        for(Map.Entry<Integer, Token> entry : table.tokenSet()) {
            Token value = entry.getValue();
            int matched = match(Pattern.quote(value.getText()));
            if(matched > 0) {
                return new Token(value.getType(), value.getText(), index, matched);
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
        if(matched < 0) {
            return null;
        }
        String text = input.substring(index, matched);
        Token token = table.lookup(text);
        if (token == null) {
            token = new Token(TokenType.VAR, text, index, matched);
        } else {
            token = new Token(TokenType.VAR, token.getText(), index, matched);
        }
        table.insert(token);
        return token;
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
            throw new ParseException("Unexpected character " + index, index);
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
