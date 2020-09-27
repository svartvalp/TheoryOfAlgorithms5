package com.svartvalp.prac3;

public class ParseException extends RuntimeException {

    private int index;

    public ParseException(String message, int index) {
        super(message);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
