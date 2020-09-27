package com.svartvalp.prac5;

import org.antlr.v4.runtime.Token;

public class UnOpExprNode implements ExprNode {
    private final Token op;
    private final ExprNode right;

    public UnOpExprNode(Token op, ExprNode right) {
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return right.toString() + op.getText();
    }
}
