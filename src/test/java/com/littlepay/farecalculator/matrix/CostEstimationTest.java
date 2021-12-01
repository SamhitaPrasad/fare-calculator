package com.littlepay.farecalculator.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostEstimationTest {

    double[][] cost = {{0, 3.25, 7.3}, {3.25, 0, 5.5}, {7.3, 5.5, 0}};
    String[][] costString = {{"0", "3.25", "7.3"}, {"3.25", "0", "5.5"}, {"7.3", "5.5", "0"}};

    @Test
    public void minCostEstimate_scenarios() {
        // Travelling from stop a to the same stop a should return 0
        assertEquals(0, CostEstimation.minCost(cost, 0, 0));
        assertEquals(0, CostEstimation.minCost(cost, 1, 1));
        assertEquals(0, CostEstimation.minCost(cost, 2, 2));

        // Travelling from stop a to the same stop a should return respective fare
        assertEquals(3.25, CostEstimation.minCost(cost, 0, 1));
        assertEquals(3.25, CostEstimation.minCost(cost, 1, 0));

        assertEquals(5.5, CostEstimation.minCost(cost, 1, 2));
        assertEquals(5.5, CostEstimation.minCost(cost, 2, 1));

        assertEquals(7.3, CostEstimation.minCost(cost, 0, 2));
        assertEquals(7.3, CostEstimation.minCost(cost, 2, 0));
    }

    @Test
    public void minCostEstimate_IndexOutOfBounds_exception() {
        assertEquals(0, CostEstimation.minCost(cost, 5, 4));
    }

    /**
     * If a passenger taps on at one stop but forgets to tap off at another stop, this is called an incomplete trip. The
     * passenger will be charged the maximum amount for a trip from that stop to any other stop they could have
     * travelled to. For example, if a passenger taps on at Stop 2, but does not tap off, they could potentially have
     * travelled to either stop 1 ($3.25) or stop 3 ($5.50), so they will be charged the higher value of $5.50.
     */
    @Test
    public void maxCostEstimateFromSource_scenarios_string() {

        assertEquals("7.3", CostEstimation.maxCostEstimateFromSource(costString, "0"));
        assertEquals("5.5", CostEstimation.maxCostEstimateFromSource(costString, "1"));
        assertEquals("7.3", CostEstimation.maxCostEstimateFromSource(costString, "0"));

    }

}
