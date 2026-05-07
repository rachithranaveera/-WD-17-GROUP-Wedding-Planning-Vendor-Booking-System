package com.wedding.weddingsystem.models;

/**
 * PhotographyVendor — extends Vendor.
 * Overrides getServiceDescription() to provide photography-specific details.
 */
public class PhotographyVendor extends Vendor {

    private String photographyStyle;  // e.g., "Candid", "Traditional", "Cinematic"
    private boolean droneAvailable;

    // Default constructor
    public PhotographyVendor() {
        super();
    }

    // Parameterized constructor
    public PhotographyVendor(String vendorId, String name, String contactInfo, double pricing,
                             String photographyStyle, boolean droneAvailable) {
        super(vendorId, name, "Photography", contactInfo, pricing);
        this.photographyStyle = photographyStyle;
        this.droneAvailable = droneAvailable;
    }

    public String getPhotographyStyle() {
        return photographyStyle;
    }

    public void setPhotographyStyle(String photographyStyle) {
        this.photographyStyle = photographyStyle;
    }

    public boolean isDroneAvailable() {
        return droneAvailable;
    }

    public void setDroneAvailable(boolean droneAvailable) {
        this.droneAvailable = droneAvailable;
    }

    /**
     * Polymorphic override — returns photography-specific service description.
     */
    @Override
    public String getServiceDescription() {
        return "Photography Service by " + getName() +
               " | Style: " + photographyStyle +
               " | Drone Coverage: " + (droneAvailable ? "Yes" : "No") +
               " | Price: Rs. " + getPricing();
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + photographyStyle + "|" + droneAvailable;
    }
}
