package com.calcyoulater.storage;

import org.mariuszgromada.math.mxparser.*;

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

    public Expression parse() {
        return new Expression(text);
    }

    public void setNext(Equation newNext) {
        next = newNext;
    }

    public void setPrev(Equation newPrev) {
        prev = newPrev;
    }
}
