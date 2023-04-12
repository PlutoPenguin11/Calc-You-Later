package com.calcyoulater.storage;

import java.io.Serializable;
import java.text.DecimalFormat;
import org.mariuszgromada.math.mxparser.*;

public class Equation implements Serializable {

    private String text;
    protected Equation next;
    protected Equation prev;

    public Equation(String equation) {
        text = equation;
    }

    public String getNode() {
        return text;
    }

    public void setNext(Equation newNext) {
        next = newNext;
    }

    public void setPrev(Equation newPrev) {
        prev = newPrev;
    }

    public String parse() {
        DecimalFormat df = new DecimalFormat("0.00000");
        Expression expression = new Expression(text);
        double num = expression.calculate();
        return df.format(num);
    }
}
