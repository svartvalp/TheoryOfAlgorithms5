package com.svartvalp.prac5;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Main {
    public static void main(String[] args) {
        String text = "1 + 3 + 2 * 2";
        ExprLexer lexer = new ExprLexer(CharStreams.fromString(text));
        ExprParser parser = new ExprParser(new CommonTokenStream(lexer));
        ExprNode root = visitExpr(parser.expr());
        System.out.println(root);
        System.out.println(eval(root));
    }


    public static ExprNode visitMul(ExprParser.MulContext context) {
        if(context.num != null) {
            return new NumberExprNode(context.num);
        } else {
            return visitExpr(context.expr());
        }
    }

    public static ExprNode visitExpr(ExprParser.ExprContext context) {
        ExprNode left = visitAdd(context.left);
        for (int i = 0; i < context.right.size(); i++) {
            ExprParser.AddContext rightNode = context.right.get(i);
            Token op = context.op.get(i);
            ExprNode right = visitAdd(rightNode);
            left = new BinOpExprNode(left, op, right);
        }
        return left;
    }

    public static ExprNode visitAdd(ExprParser.AddContext context) {
        ExprNode left = visitMul(context.left);
        for (int i = 0; i < context.right.size(); i++) {
            ExprParser.MulContext rightNode = context.right.get(i);
            Token op = context.op.get(i);
            ExprNode right = visitMul(rightNode);
            left = new BinOpExprNode(left, op, right);
        }
        return left;
    }

    public static double eval(ExprNode node) {
        if(node instanceof NumberExprNode) {
            NumberExprNode number = (NumberExprNode) node;
            return Double.parseDouble(number.toString());
        }
        if(node instanceof BinOpExprNode) {
            BinOpExprNode binOpExprNode = (BinOpExprNode) node;
            double left = eval(binOpExprNode.getLeft());
            double right = eval(binOpExprNode.getRight());
            switch (binOpExprNode.getOp().getType()) {
                case ExprLexer.ADD: return left + right;
                case ExprLexer.SUB: return left - right;
                case ExprLexer.DIV: return left / right;
                case ExprLexer.MUL: return left * right;
            }
        }
        throw new IllegalStateException();
    }
}
