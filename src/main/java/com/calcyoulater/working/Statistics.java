package com.calcyoulater.working;

/*
 * @author William Jenkins
 */
public class Statistics {
    private Statistics() {
    }

    public static double mean(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return (sum / input.length);
    }

    public static double minimum(double[] input) {
        double minimum = input[0];
        for (int i = 1; i < input.length; i++) {
            if (input[i] < minimum) {
                minimum = input[i];
            }
        }
        return minimum;
    }

    public static double maximum(double[] input) {
        double maximum = input[0];
        for (int i = 1; i < input.length; i++) {
            if (input[i] > maximum) {
                maximum = input[i];
            }
        }
        return maximum;
    }

    public static double[] quartiles(double[] input) {
        quickSort(input, 0, input.length - 1);
        double min, q1, med, q3, max;
        min = input[0];
        max = input[input.length - 1];
        int lower = input.length / 2;
        int extra = input.length % 2;
        if (extra == 1) {
            med = input[lower];
        } else {
            med = (input[lower] + input[lower - 1]) / 2;
        }
        q1 = input[lower / 2];
        q3 = input[lower + (lower / 2)];
        double[] result = { min, q1, med, q3, max };
        return result;

    }

    public static double median(double[] input) {
        quickSort(input, 0, input.length - 1);
        double med;
        int lower = input.length / 2;
        int extra = input.length % 2;
        if (extra == 1) {
            med = input[lower];
        } else {
            med = (input[lower] + input[lower - 1]) / 2;
        }
        return med;
    }

    public static double meanSquared(double[] input) {
        double x = mean(input);
        return x * x;
    }

    public static double meanOfSquares(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * input[i];
        }
        return sum / input.length;
    }

    public static int count(double[] input) {
        return input.length;
    }

    public static double sum(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum;
    }

    public static double variance(double[] input) {
        double a = meanSquared(input);
        double b = meanOfSquares(input);
        return b - a;
    }

    public static double stddev(double[] input) {
        return Math.sqrt(variance(input));
    }

    public static double stderror(double[] input) {
        double factor = Math.sqrt((double) (count(input) + 1) / (double) count(input));
        return factor * stddev(input);
    }

    public static double tstat(double[] input, double target) {
        double error = stderror(input);
        double mean = mean(input);
        return (mean - target) / error;
    }

    public static boolean isSignificant(double[] input, double target, double critical) {
        double tstat = tstat(input, target);
        if (critical > 0) {
            return (tstat > critical);
        } else {
            return (tstat < critical);
        }
    }

    private static void quickSort(double arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(double arr[], int begin, int end) {
        double pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                double swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        double swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public static String getSummary(String input) {
        if (input == null || input.equals("")) {
            return "Empty input";
        }

        double inputArray[] = null;

        try {
            inputArray = parseString(input);
        } catch (NumberFormatException e) {
            return "You entered an invalid expresion";
        }

        double quartiles[] = quartiles(inputArray);

        return "Sample size: " + count(inputArray) + "\n"
                + "Minimum: " + quartiles[0] + "\n"
                + "Q1: " + quartiles[1] + "\n"
                + "Median: " + quartiles[2] + "\n"
                + "Q3: " + quartiles[3] + "\n"
                + "Maximum: " + quartiles[4] + "\n"
                + "Mean: " + mean(inputArray) + "\n"
                + "Standard deviation: " + stddev(inputArray) + "\n"
                + "Variance: " + variance(inputArray);

    }

    private static double[] parseString(String input) {
        String inputArray[] = input.split(",");
        double parsedArray[] = new double[inputArray.length];
        try {
            for (int i = 0; i < inputArray.length; i++) {
                parsedArray[i] = Double.valueOf(inputArray[i]);
            }
        } catch (Exception e) {
            throw new java.lang.NumberFormatException();
        }

        return parsedArray;
    }
}
