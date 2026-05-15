package com.example.wd17usermangement.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COUPLE")
public class Couple extends User {
    private Double estimatedBudget;

    public Double getEstimatedBudget() { return estimatedBudget; }
    public void setEstimatedBudget(Double estimatedBudget) { this.estimatedBudget = estimatedBudget; }
}
