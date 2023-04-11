package com.calcyoulater.working;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.io.IOException;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.mariuszgromada.math.mxparser.*;

public class Grapher {
    private String function = "";
    private double lower = -50.0;
    private double upper = 50.0;
    private ArrayList<Double> x = new ArrayList<>();
    private ArrayList<Double> y = new ArrayList<>();
    private double yValue;
    private double xValue;
    final XYChart chart = new XYChartBuilder().width(600).height(400).title("f(x)").xAxisTitle("X").yAxisTitle("Y")
            .build();

    public Grapher(String function) {
        this.function = function;
        try {
            parseFunction();
        } catch (IOException e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Calc-You-Later");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel chartPanel = new XChartPanel<XYChart>(chart);
                frame.add(chartPanel, BorderLayout.CENTER);

                JLabel label = new JLabel("Graph", SwingConstants.CENTER);
                frame.add(label, BorderLayout.SOUTH);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public boolean parseFunction() throws IOException {
        String enter = this.function;
        Function f = null;
        double tStep = 1.0;

        try {
            f = new Function(enter);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        while (lower < upper) {

            yValue = f.calculate(lower);
            xValue = lower;
            x.add(xValue);
            y.add(yValue);
            lower += tStep;
        }
        chart.addSeries(enter, x, y);

        return true;
    }
}