package prac7;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TokenTable {
    private final Map<Integer, Token> tokenMap;

    public TokenTable() {
        this.tokenMap = new HashMap<>();
    }

    public Set<Map.Entry<Integer, Token>> tokenSet() {
        return tokenMap.entrySet();
    }

    public Token lookup(String text) {
        return tokenMap.get(hash(text));
    }

    public void insert(Token token) {
        tokenMap.put(hash(token), token);
    }

    public void remove(Integer hash) {
        tokenMap.remove(hash);
    }

    public void remove(String text) {
        remove(hash(text));
    }

    private Integer hash(Token token) {
        return hash(token.getText());
    }

    private Integer hash(String text) {
        return text.hashCode();
    }
}
