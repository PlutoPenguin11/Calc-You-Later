package com.calcyoulater.working;
import java.util.Vector; 
import java.util.ArrayList; 
import org.mariuszgromada.math.mxparser.*;

import com.calcyoulater.storage.Equation;

public class Grapher {
    private String function="";
    private int Lower = -500;
    private int Upper = 500;
    private ArrayList<Vector<Double>> points = new ArrayList<Vector<Double>>();
    private Vector<Integer> Bounds = new Vector<Integer>(2);

    public Grapher(String function) {
        this.function = function;    
    }

    public ArrayList<Vector<Double>> parseFunction() {
        String enter = this.function;
        Function f = new Function(enter);
        double tStep = 0.01;
        while(Lower<Upper){
            double Y = f.calculate(Lower);
            double X = Lower;
            Vector<Double> point = new Vector<Double>(2) ;
            point.add(X);
            point.add(Y);
            points.add(point);
            Lower+=tStep;

        }   
        return points;
    }

    public void setBounds(int lower,int higher){
        Lower = lower;
        Upper = higher;
        Bounds.clear();
        Bounds.add(Lower);
        Bounds.add(Upper);
    }

    
}
