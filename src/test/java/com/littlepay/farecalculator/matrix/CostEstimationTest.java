package com.littlepay.farecalculator.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostEstimationTest {

    double[][] cost = {{0, 3.25, 7.3}, {3.25, 0, 5.5}, {7.3, 5.5, 0}};

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

}
