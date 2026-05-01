package com.wedding.weddingsystem.models;

public class VariableExpense extends BudgetItem {

    public VariableExpense(String budgetId, String coupleId,
                           String category, double estimatedCost,
                           double actualCost, boolean isPaid) {
        super(budgetId, coupleId, category,
                estimatedCost, actualCost, isPaid);
    }

    // Polymorphism - Override
    @Override
    public double calculateVariance() {
        double variance = getEstimatedCost() - getActualCost();
        // Variable expenses can fluctuate - return percentage
        return (variance / getEstimatedCost()) * 100;
    }

    @Override
    public String toString() {
        return getBudgetId() + "," + getCoupleId() + "," +
                getCategory() + "," + getEstimatedCost() + "," +
                getActualCost() + "," + isPaid() + ",VARIABLE";
    }
}