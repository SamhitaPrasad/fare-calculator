package com.littlepay.farecalculator.matrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CostEstimation {

    static Logger LOGGER = LoggerFactory.getLogger(CostEstimation.class);

    // A recursive function to find the shortest path from
    // source to destination.
    public static double minCostEstimate(double[][] cost, int source, int destination) {

        LOGGER.info("Estimating cost for travelling from {} to {}", (source+1), (destination+1));

        // If source is same as destination or destination is next to source
        if (source == destination || source + 1 == destination)
            return cost[source][destination];

        // Initialize min cost as direct ticket from source to destination.
        double min = cost[source][destination];

        return min;
    }

    public static String  minCostEstimate(String[][] cost, String source, String destination) {

        LOGGER.info("Estimating cost for travelling from {} to {}", (source+1), (destination+1));


        // If source is same as destination or destination is next to source
        if (source == destination || source + 1 == destination)
            return cost[Integer.parseInt(source)][Integer.parseInt(destination)];

        // Initialize min cost as direct ticket from source to destination.
        String min = cost[Integer.parseInt(source)][Integer.parseInt(destination)];;

        return min;
    }
    public static String  maxCostEstimateFromSource(String[][]  cost, String source) {

        LOGGER.info("Estimating cost for travelling from {} ", (source + 1));
        int s = Integer.parseInt(source);
        // Initialize min cost as direct ticket from source 's' to source 'd' which is obviously 0.
        String maxPrice = "0.0";

        for (int i = s; i<=s; i++) {
            for (int j = 0; j < cost.length; j++) {
                String c = minCostEstimate(cost, String.valueOf(i), String.valueOf(j));
                if (Double.parseDouble(c) > Double.parseDouble(maxPrice))
                    maxPrice = c;
            }
        }
        for (int i = s; i>=s; i--) {
            for (int j = cost.length-1; j>0; j--) {
                String c = minCostEstimate(cost, String.valueOf(i), String.valueOf(j));
                if (Double.parseDouble(c) > Double.parseDouble(maxPrice))
                    maxPrice = c;
            }
        }
        return maxPrice;
    }

    // This function returns the smallest possible cost to
    // reach station N-1 from station 0.
    public static double minCost(double[][] cost, int source, int destination) {
//        LOGGER.info("The Minimum cost to reach station from {} to {}", (a+1), (b+1));
        //TODO: Analyse exception handling vs return 0
        if(source > cost.length || destination > cost.length){
            LOGGER.error("Cannot price for the following source and destination: {} / {}", source, destination);
            return 0;
        }
        return minCostEstimate(cost, source, destination);
    }

}
