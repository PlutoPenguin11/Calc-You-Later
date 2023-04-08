package com.calcyoulater.working;
import java.text.DecimalFormat;

import org.mariuszgromada.math.mxparser.*;

import com.calcyoulater.storage.Equation;

public class Parser {
    private String equation="";

    public Parser(String equation) {
        this.equation = equation;    

    }

    public String parseInput() {
        DecimalFormat df = new DecimalFormat("0.00000");
        String enter = this.equation;
        Equation newEquation = new Equation(enter);
        Expression expression = newEquation.parse();
        
        double num = expression.calculate();
        
    
        return df.format(num);
    }
}
