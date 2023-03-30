package com.calcyoulater;

import com.calcyoulater.working.Statistics;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    private double[] testArray;
    private double temp;

    @BeforeEach
    public void setUp() {
        testArray = new double[] { 0, 471, 727, 3.14, 123 };
    }

    @Test
    public void testMean() {
        temp = Statistics.mean(testArray);
        assertTrue(Math.abs(temp - 264.828) < 0.01);
    }

    @Test
    public void testMinimum() {
        temp = Statistics.minimum(testArray);
        assertTrue(Math.abs(temp - 0) < 0.01);

    }

    @Test
    public void testMaximum() {
        temp = Statistics.maximum(testArray);
        assertTrue(Math.abs(temp - 727) < 0.01);

    }

    @Test
    public void testQuartiles() {
        double[] temp = Statistics.quartiles(testArray);
        double[] quartiles = { 0, 3.14, 123, 471, 727 };
        boolean flag = true;
        for (int i = 0; i < 5; i++) {
            if (Math.abs(temp[i] - quartiles[i]) > 0.01) {
                flag = false;

            }
        }
        assertTrue(flag);
    }

    @Test
    public void testMedian() {
        temp = Statistics.median(testArray);
        assertTrue(Math.abs(temp - 123) < 0.01);

    }

    @Test
    public void testMeanSquared() {
        temp = Statistics.meanSquared(testArray);
        assertTrue(Math.abs(temp - 70133.8696) < 0.01);

    }

    @Test
    public void testSquareofMeans() {
        temp = Statistics.meanOfSquares(testArray);
        assertTrue(Math.abs(temp - 153101.77192) < 0.01);

    }

    @Test
    public void testCount() {
        int temp = Statistics.count(testArray);
        assertTrue(temp == 5);
    }

    @Test
    public void testSum() {
        temp = Statistics.sum(testArray);
        assertTrue(Math.abs(temp - 264.828 * 5) < 0.01);
    }

    @Test
    public void testVar() {
        temp = Statistics.variance(testArray);
        assertTrue(Math.abs(temp - 82967.9023) < 0.01);

    }

    @Test
    public void testDev() {
        temp = Statistics.stddev(testArray);
        assertTrue(Math.abs(temp - 288.041494) < 0.01);
    }

    @Test
    public void testErr() {
        temp = Statistics.stderror(testArray);
        assertTrue(Math.abs(temp - 315.5336476561572) < 0.01);

    }

    @Test
    public void testTstat() {
        temp = Statistics.tstat(testArray, 0);
        assertTrue(Math.abs(temp - 0.8393019317185086) < 0.01);
    }

    @Test
    public void testIsSignificant() {
        boolean temp = Statistics.isSignificant(testArray, 0, 0.5);
        assertTrue(temp);
    }

    @Test
    public void testIsSignificant2() {
        boolean temp = Statistics.isSignificant(testArray, 0, 1.5);
        assertTrue(!temp);
    }
}
