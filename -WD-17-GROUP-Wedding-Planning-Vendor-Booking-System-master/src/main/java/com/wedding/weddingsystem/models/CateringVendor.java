package com.wedding.weddingsystem.models;

/**
 * CateringVendor — extends Vendor.
 * Overrides getServiceDescription() to provide catering-specific details.
 */
public class CateringVendor extends Vendor {

    private String cuisineType;    // e.g., "Sri Lankan", "Indian", "Western", "Chinese"
    private int maxGuestCapacity;

    // Default constructor
    public CateringVendor() {
        super();
    }

    // Parameterized constructor
    public CateringVendor(String vendorId, String name, String contactInfo, double pricing,
                          String cuisineType, int maxGuestCapacity) {
        super(vendorId, name, "Catering", contactInfo, pricing);
        this.cuisineType = cuisineType;
        this.maxGuestCapacity = maxGuestCapacity;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getMaxGuestCapacity() {
        return maxGuestCapacity;
    }

    public void setMaxGuestCapacity(int maxGuestCapacity) {
        this.maxGuestCapacity = maxGuestCapacity;
    }

    /**
     * Polymorphic override — returns catering-specific service description.
     */
    @Override
    public String getServiceDescription() {
        return "Catering Service by " + getName() +
               " | Cuisine: " + cuisineType +
               " | Max Guests: " + maxGuestCapacity +
               " | Price per plate: Rs. " + getPricing();
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + cuisineType + "|" + maxGuestCapacity;
    }
}
