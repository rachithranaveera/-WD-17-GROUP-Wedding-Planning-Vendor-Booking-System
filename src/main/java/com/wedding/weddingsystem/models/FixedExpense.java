package com.wedding.weddingsystem.models;

public class FixedExpense extends BudgetItem {

    public FixedExpense(String budgetId, String coupleId,
                        String category, double estimatedCost,
                        double actualCost, boolean isPaid) {
        super(budgetId, coupleId, category,
                estimatedCost, actualCost, isPaid);
    }

    // Polymorphism - Override
    @Override
    public double calculateVariance() {
        return getEstimatedCost() - getActualCost();
    }

    @Override
    public String toString() {
        return getBudgetId() + "," + getCoupleId() + "," +
                getCategory() + "," + getEstimatedCost() + "," +
                getActualCost() + "," + isPaid() + ",FIXED";
    }
}