package com.svartvalp.prac5;

import org.antlr.v4.runtime.Token;

public class BinOpExprNode implements ExprNode {
    private final ExprNode left;
    private final Token op;
    private final ExprNode right;

    public BinOpExprNode(ExprNode left, Token op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + op.getText() + right.toString();
    }

    public ExprNode getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public ExprNode getRight() {
        return right;
    }
}
