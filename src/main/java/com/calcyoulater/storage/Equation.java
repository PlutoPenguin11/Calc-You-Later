package com.calcyoulater.storage;

public class Equation {
    
    private String text;
    protected Equation next;
    protected Equation prev;

    public Equation(String equation) {
        text = equation;
    }

    public String getNode() {
        return text;
    }

    public void parse() {
        //Parse with mXparser
    }

    public void setNext(Equation newNext) {
        next = newNext;
    }

    public void setPrev(Equation newPrev) {
        prev = newPrev;
    }
}
