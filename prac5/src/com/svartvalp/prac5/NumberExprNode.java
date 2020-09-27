package com.svartvalp.prac5;

import org.antlr.v4.runtime.Token;

public class NumberExprNode implements ExprNode {
    private final Token token;

    public NumberExprNode(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token.getText();
    }
}
