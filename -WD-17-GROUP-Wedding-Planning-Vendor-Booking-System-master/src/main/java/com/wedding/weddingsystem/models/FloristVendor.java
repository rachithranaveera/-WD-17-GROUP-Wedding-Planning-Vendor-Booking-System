package com.wedding.weddingsystem.models;

/**
 * FloristVendor — extends Vendor.
 * Overrides getServiceDescription() to provide florist-specific details.
 */
public class FloristVendor extends Vendor {

    private String flowerSpecialty;  // e.g., "Roses", "Orchids", "Mixed Bouquets"
    private boolean venueDecoration;

    // Default constructor
    public FloristVendor() {
        super();
    }

    // Parameterized constructor
    public FloristVendor(String vendorId, String name, String contactInfo, double pricing,
                         String flowerSpecialty, boolean venueDecoration) {
        super(vendorId, name, "Florist", contactInfo, pricing);
        this.flowerSpecialty = flowerSpecialty;
        this.venueDecoration = venueDecoration;
    }

    public String getFlowerSpecialty() {
        return flowerSpecialty;
    }

    public void setFlowerSpecialty(String flowerSpecialty) {
        this.flowerSpecialty = flowerSpecialty;
    }

    public boolean isVenueDecoration() {
        return venueDecoration;
    }

    public void setVenueDecoration(boolean venueDecoration) {
        this.venueDecoration = venueDecoration;
    }

    /**
     * Polymorphic override — returns florist-specific service description.
     */
    @Override
    public String getServiceDescription() {
        return "Florist Service by " + getName() +
               " | Specialty: " + flowerSpecialty +
               " | Venue Decoration: " + (venueDecoration ? "Yes" : "No") +
               " | Price: Rs. " + getPricing();
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + flowerSpecialty + "|" + venueDecoration;
    }
}
