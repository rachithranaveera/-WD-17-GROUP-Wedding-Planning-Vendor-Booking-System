package com.wedding.weddingsystem.models;

/**
 * Abstract Vendor class — base model for all vendor types.
 */
public abstract class Vendor {
    private String vendorId;
    private String name;
    private String category;
    private String contactInfo;
    private double pricing;

    // Default constructor
    public Vendor() {
    }

    // Parameterized constructor
    public Vendor(String vendorId, String name, String category, String contactInfo, double pricing) {
        this.vendorId = vendorId;
        this.name = name;
        this.category = category;
        this.contactInfo = contactInfo;
        this.pricing = pricing;
    }

    // Getters and Setters
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    /**
     * Abstract method — each vendor type MUST override this
     * to provide its own service description (Polymorphism).
     */
    public abstract String getServiceDescription();

    /**
     * Converts vendor data to a pipe-delimited string for file storage.
     */
    public String toFileString() {
        return vendorId + "|" + name + "|" + category + "|" + contactInfo + "|" + pricing;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", pricing=" + pricing +
                '}';
    }
}
