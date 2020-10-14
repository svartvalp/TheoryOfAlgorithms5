package prac7;

public class Token {
    private TokenType type;
    private String text;
    private int index;
    private int to;

    public Token(TokenType tokenType, String text) {
        this.type = tokenType;
        this.text = text;
    }

    public Token(TokenType type, String text, int index, int to) {
        this.type = type;
        this.text = text;
        this.index = index;
        this.to = to;
    }

    public TokenType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Token{ " +
                "type=" + type +
                ", text='" + text + '\'' +
                ", index=" + index +
                " }";
    }
}
