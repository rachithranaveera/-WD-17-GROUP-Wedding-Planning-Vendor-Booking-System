package com.wedding.weddingsystem.models;

public abstract class BudgetItem {

    private String budgetId;
    private String coupleId;
    private String category;
    private double estimatedCost;
    private double actualCost;
    private boolean isPaid;

    // Constructor
    public BudgetItem(String budgetId, String coupleId,
                      String category, double estimatedCost,
                      double actualCost, boolean isPaid) {
        this.budgetId = budgetId;
        this.coupleId = coupleId;
        this.category = category;
        this.estimatedCost = estimatedCost;
        this.actualCost = actualCost;
        this.isPaid = isPaid;
    }

    // Getters and Setters
    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public String getCoupleId() { return coupleId; }
    public void setCoupleId(String coupleId) { this.coupleId = coupleId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(double estimatedCost) { this.estimatedCost = estimatedCost; }

    public double getActualCost() { return actualCost; }
    public void setActualCost(double actualCost) { this.actualCost = actualCost; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }

    // Abstract method - Polymorphism
    public abstract double calculateVariance();
}