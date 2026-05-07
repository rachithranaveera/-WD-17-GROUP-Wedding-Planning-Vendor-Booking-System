package com.wedding.weddingsystem.servlets;

import com.wedding.weddingsystem.models.CateringVendor;
import com.wedding.weddingsystem.models.FloristVendor;
import com.wedding.weddingsystem.models.PhotographyVendor;
import com.wedding.weddingsystem.models.Vendor;
import com.wedding.weddingsystem.utils.VendorFileHandler;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendor")
public class VendorServlet {


    /**
     * Returns the base path for the vendors.txt file.
     * Uses the application's real path on the server.
     */
    private String getBasePath() {
        return "src/main/resources/data";
    }

    // ==================== GET requests ====================
    @GetMapping
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        // Ensure data directory exists
        java.io.File dataDir = new java.io.File(getBasePath());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        switch (action) {
            case "list":
                listVendors(request, response);
                break;
            case "add":
                // Show the add vendor form
                request.getRequestDispatcher("/add-vendor.jsp").forward(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteVendor(request, response);
                break;
            case "search":
                searchVendors(request, response);
                break;
            default:
                listVendors(request, response);
                break;
        }
    }

    // ==================== POST requests ====================
    @PostMapping
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        // Ensure data directory exists
        java.io.File dataDir = new java.io.File(getBasePath());
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        switch (action) {
            case "add":
                addVendor(request, response);
                break;
            case "update":
                updateVendor(request, response);
                break;
            case "search":
                searchVendors(request, response);
                break;
            default:
                listVendors(request, response);
                break;
        }
    }

    // ==================== LIST all vendors ====================
    private void listVendors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Vendor> vendors = VendorFileHandler.getAllVendors(getBasePath());
        request.setAttribute("vendors", vendors);
        request.getRequestDispatcher("/vendor-list.jsp").forward(request, response);
    }

    // ==================== ADD a new vendor ====================
    private void addVendor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String contactInfo = request.getParameter("contactInfo");
        double pricing;

        try {
            pricing = Double.parseDouble(request.getParameter("pricing"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid pricing value.");
            request.getRequestDispatcher("/add-vendor.jsp").forward(request, response);
            return;
        }

        // Generate next ID
        String vendorId = VendorFileHandler.generateNextId(getBasePath());

        Vendor vendor;

        switch (category.toLowerCase()) {
            case "photography":
                String style = request.getParameter("photographyStyle");
                boolean drone = "on".equals(request.getParameter("droneAvailable"))
                             || "true".equals(request.getParameter("droneAvailable"));
                vendor = new PhotographyVendor(vendorId, name, contactInfo, pricing, style, drone);
                break;

            case "catering":
                String cuisine = request.getParameter("cuisineType");
                int maxGuests;
                try {
                    maxGuests = Integer.parseInt(request.getParameter("maxGuestCapacity"));
                } catch (NumberFormatException e) {
                    maxGuests = 0;
                }
                vendor = new CateringVendor(vendorId, name, contactInfo, pricing, cuisine, maxGuests);
                break;

            case "florist":
                String specialty = request.getParameter("flowerSpecialty");
                boolean decoration = "on".equals(request.getParameter("venueDecoration"))
                                  || "true".equals(request.getParameter("venueDecoration"));
                vendor = new FloristVendor(vendorId, name, contactInfo, pricing, specialty, decoration);
                break;

            default:
                request.setAttribute("error", "Invalid vendor category: " + category);
                request.getRequestDispatcher("/add-vendor.jsp").forward(request, response);
                return;
        }

        VendorFileHandler.addVendor(vendor, getBasePath());
        request.setAttribute("success", "Vendor '" + name + "' added successfully with ID: " + vendorId);
        listVendors(request, response);
    }

    // ==================== SHOW EDIT FORM ====================
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vendorId = request.getParameter("id");
        Vendor vendor = VendorFileHandler.getVendorById(vendorId, getBasePath());

        if (vendor == null) {
            request.setAttribute("error", "Vendor not found with ID: " + vendorId);
            listVendors(request, response);
            return;
        }

        request.setAttribute("vendor", vendor);
        request.getRequestDispatcher("/vendor-edit.jsp").forward(request, response);
    }

    // ==================== UPDATE a vendor ====================
    private void updateVendor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vendorId = request.getParameter("vendorId");
        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String contactInfo = request.getParameter("contactInfo");
        double pricing;

        try {
            pricing = Double.parseDouble(request.getParameter("pricing"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid pricing value.");
            showEditForm(request, response);
            return;
        }

        Vendor vendor;

        switch (category.toLowerCase()) {
            case "photography":
                String style = request.getParameter("photographyStyle");
                boolean drone = "on".equals(request.getParameter("droneAvailable"))
                             || "true".equals(request.getParameter("droneAvailable"));
                vendor = new PhotographyVendor(vendorId, name, contactInfo, pricing, style, drone);
                break;

            case "catering":
                String cuisine = request.getParameter("cuisineType");
                int maxGuests;
                try {
                    maxGuests = Integer.parseInt(request.getParameter("maxGuestCapacity"));
                } catch (NumberFormatException e) {
                    maxGuests = 0;
                }
                vendor = new CateringVendor(vendorId, name, contactInfo, pricing, cuisine, maxGuests);
                break;

            case "florist":
                String specialty = request.getParameter("flowerSpecialty");
                boolean decoration = "on".equals(request.getParameter("venueDecoration"))
                                  || "true".equals(request.getParameter("venueDecoration"));
                vendor = new FloristVendor(vendorId, name, contactInfo, pricing, specialty, decoration);
                break;

            default:
                request.setAttribute("error", "Invalid vendor category.");
                listVendors(request, response);
                return;
        }

        boolean updated = VendorFileHandler.updateVendor(vendor, getBasePath());
        if (updated) {
            request.setAttribute("success", "Vendor '" + name + "' updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update vendor. Vendor not found.");
        }
        listVendors(request, response);
    }

    // ==================== DELETE a vendor ====================
    private void deleteVendor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vendorId = request.getParameter("id");
        boolean deleted = VendorFileHandler.deleteVendor(vendorId, getBasePath());

        if (deleted) {
            request.setAttribute("success", "Vendor with ID '" + vendorId + "' deleted successfully.");
        } else {
            request.setAttribute("error", "Vendor not found with ID: " + vendorId);
        }
        listVendors(request, response);
    }

    // ==================== SEARCH vendors ====================
    private void searchVendors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchType = request.getParameter("searchType");
        String searchQuery = request.getParameter("searchQuery");

        List<Vendor> results;

        if (searchType == null || searchQuery == null || searchQuery.trim().isEmpty()) {
            results = VendorFileHandler.getAllVendors(getBasePath());
        } else {
            switch (searchType) {
                case "id":
                    Vendor v = VendorFileHandler.getVendorById(searchQuery, getBasePath());
                    results = new java.util.ArrayList<>();
                    if (v != null) results.add(v);
                    break;
                case "name":
                    results = VendorFileHandler.searchVendorsByName(searchQuery, getBasePath());
                    break;
                case "category":
                    results = VendorFileHandler.searchVendorsByCategory(searchQuery, getBasePath());
                    break;
                default:
                    results = VendorFileHandler.getAllVendors(getBasePath());
                    break;
            }
        }

        request.setAttribute("vendors", results);
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchQuery", searchQuery);
        request.getRequestDispatcher("/vendor-search.jsp").forward(request, response);
    }
}
