package com.wedding.weddingsystem.utils;

import com.wedding.weddingsystem.models.CateringVendor;
import com.wedding.weddingsystem.models.FloristVendor;
import com.wedding.weddingsystem.models.PhotographyVendor;
import com.wedding.weddingsystem.models.Vendor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class VendorFileHandler {

    private static final String FILE_PATH = "vendors.txt";

   
    private static String getFilePath(String basePath) {
        if (basePath != null && !basePath.isEmpty()) {
            return basePath + File.separator + FILE_PATH;
        }
        return FILE_PATH;
    }

    // ==================== CREATE ====================

    /**
     * Adds a new vendor to the file.
     */
    public static void addVendor(Vendor vendor, String basePath) throws IOException {
        String filePath = getFilePath(basePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(vendor.toFileString());
            writer.newLine();
        }
    }

    // ==================== READ ====================

    /**
     * Reads all vendors from the file and returns as a list.
     */
    public static List<Vendor> getAllVendors(String basePath) throws IOException {
        List<Vendor> vendors = new ArrayList<>();
        String filePath = getFilePath(basePath);
        File file = new File(filePath);

        if (!file.exists()) {
            return vendors; // Return empty list if file doesn't exist yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Vendor vendor = parseVendor(line);
                if (vendor != null) {
                    vendors.add(vendor);
                }
            }
        }
        return vendors;
    }

    /**
     * Searches for a vendor by vendorId.
     */
    public static Vendor getVendorById(String vendorId, String basePath) throws IOException {
        List<Vendor> vendors = getAllVendors(basePath);
        for (Vendor v : vendors) {
            if (v.getVendorId().equalsIgnoreCase(vendorId)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Searches vendors by name (partial match, case-insensitive).
     */
    public static List<Vendor> searchVendorsByName(String name, String basePath) throws IOException {
        List<Vendor> results = new ArrayList<>();
        List<Vendor> vendors = getAllVendors(basePath);
        for (Vendor v : vendors) {
            if (v.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(v);
            }
        }
        return results;
    }

    /**
     * Searches vendors by category (exact match, case-insensitive).
     */
    public static List<Vendor> searchVendorsByCategory(String category, String basePath) throws IOException {
        List<Vendor> results = new ArrayList<>();
        List<Vendor> vendors = getAllVendors(basePath);
        for (Vendor v : vendors) {
            if (v.getCategory().equalsIgnoreCase(category)) {
                results.add(v);
            }
        }
        return results;
    }

    // ==================== UPDATE ====================

    /**
     * Updates an existing vendor by rewriting the entire file.
     */
    public static boolean updateVendor(Vendor updatedVendor, String basePath) throws IOException {
        List<Vendor> vendors = getAllVendors(basePath);
        boolean found = false;

        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getVendorId().equalsIgnoreCase(updatedVendor.getVendorId())) {
                vendors.set(i, updatedVendor);
                found = true;
                break;
            }
        }

        if (found) {
            rewriteFile(vendors, basePath);
        }
        return found;
    }

    // ==================== DELETE ====================

    /**
     * Deletes a vendor by vendorId.
     */
    public static boolean deleteVendor(String vendorId, String basePath) throws IOException {
        List<Vendor> vendors = getAllVendors(basePath);
        boolean removed = vendors.removeIf(v -> v.getVendorId().equalsIgnoreCase(vendorId));

        if (removed) {
            rewriteFile(vendors, basePath);
        }
        return removed;
    }

    // ==================== HELPER METHODS ====================

    /**
     * Rewrites the entire file with the given list of vendors.
     */
    private static void rewriteFile(List<Vendor> vendors, String basePath) throws IOException {
        String filePath = getFilePath(basePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Vendor v : vendors) {
                writer.write(v.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Parses a pipe-delimited line into the appropriate Vendor subclass.
     * Format: vendorId|name|category|contactInfo|pricing|extra1|extra2
     */
    private static Vendor parseVendor(String line) {
        String[] parts = line.split("\\|");

        if (parts.length < 5) return null;

        String vendorId = parts[0].trim();
        String name = parts[1].trim();
        String category = parts[2].trim();
        String contactInfo = parts[3].trim();
        double pricing;

        try {
            pricing = Double.parseDouble(parts[4].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        switch (category.toLowerCase()) {
            case "photography":
                PhotographyVendor pv = new PhotographyVendor();
                pv.setVendorId(vendorId);
                pv.setName(name);
                pv.setCategory(category);
                pv.setContactInfo(contactInfo);
                pv.setPricing(pricing);
                if (parts.length > 5) pv.setPhotographyStyle(parts[5].trim());
                if (parts.length > 6) pv.setDroneAvailable(Boolean.parseBoolean(parts[6].trim()));
                return pv;

            case "catering":
                CateringVendor cv = new CateringVendor();
                cv.setVendorId(vendorId);
                cv.setName(name);
                cv.setCategory(category);
                cv.setContactInfo(contactInfo);
                cv.setPricing(pricing);
                if (parts.length > 5) cv.setCuisineType(parts[5].trim());
                if (parts.length > 6) {
                    try {
                        cv.setMaxGuestCapacity(Integer.parseInt(parts[6].trim()));
                    } catch (NumberFormatException e) {
                        cv.setMaxGuestCapacity(0);
                    }
                }
                return cv;

            case "florist":
                FloristVendor fv = new FloristVendor();
                fv.setVendorId(vendorId);
                fv.setName(name);
                fv.setCategory(category);
                fv.setContactInfo(contactInfo);
                fv.setPricing(pricing);
                if (parts.length > 5) fv.setFlowerSpecialty(parts[5].trim());
                if (parts.length > 6) fv.setVenueDecoration(Boolean.parseBoolean(parts[6].trim()));
                return fv;

            default:
                return null;
        }
    }

    /**
     * Generates the next vendor ID based on existing records.
     */
    public static String generateNextId(String basePath) throws IOException {
        List<Vendor> vendors = getAllVendors(basePath);
        int maxId = 0;
        for (Vendor v : vendors) {
            try {
                String numPart = v.getVendorId().replaceAll("[^0-9]", "");
                int id = Integer.parseInt(numPart);
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {
                // skip
            }
        }
        return "V" + String.format("%03d", maxId + 1);
    }
}
