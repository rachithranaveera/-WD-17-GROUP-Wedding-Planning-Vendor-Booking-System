<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.wedding.weddingsystem.models.Vendor, com.wedding.weddingsystem.models.PhotographyVendor, com.wedding.weddingsystem.models.CateringVendor, com.wedding.weddingsystem.models.FloristVendor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Vendor - Wedding Planning System</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Tahoma,sans-serif; background:linear-gradient(135deg,#667eea,#764ba2); min-height:100vh; display:flex; justify-content:center; align-items:flex-start; padding:30px; }
        .container { background:#fff; border-radius:16px; box-shadow:0 20px 60px rgba(0,0,0,.3); padding:40px; width:100%; max-width:650px; margin-top:20px; }
        h1 { text-align:center; color:#4a1a6b; margin-bottom:8px; font-size:28px; }
        .subtitle { text-align:center; color:#888; margin-bottom:30px; font-size:14px; }
        .form-group { margin-bottom:18px; }
        label { display:block; margin-bottom:6px; color:#333; font-weight:600; font-size:14px; }
        input, select { width:100%; padding:12px 16px; border:2px solid #e0e0e0; border-radius:8px; font-size:14px; transition:border-color .3s; outline:none; }
        input:focus, select:focus { border-color:#764ba2; box-shadow:0 0 0 3px rgba(118,75,162,.15); }
        .checkbox-group { display:flex; align-items:center; gap:10px; }
        .checkbox-group input[type="checkbox"] { width:auto; transform:scale(1.3); accent-color:#764ba2; }
        .extra-fields { background:#f8f6fc; padding:20px; border-radius:10px; margin-top:10px; border:1px dashed #c4a7e7; }
        .extra-fields h3 { color:#764ba2; margin-bottom:15px; font-size:16px; }
        .btn { display:inline-block; padding:12px 30px; border:none; border-radius:8px; font-size:15px; font-weight:600; cursor:pointer; text-decoration:none; transition:all .3s; }
        .btn-primary { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; width:100%; margin-top:10px; }
        .btn-primary:hover { transform:translateY(-2px); box-shadow:0 8px 25px rgba(118,75,162,.4); }
        .vendor-id { background:#f8f6fc; padding:12px 16px; border-radius:8px; color:#764ba2; font-weight:700; font-size:16px; text-align:center; margin-bottom:20px; }
        .nav-links { display:flex; justify-content:center; gap:15px; margin-top:20px; }
        .nav-links a { color:#764ba2; text-decoration:none; font-size:14px; font-weight:500; }
        .nav-links a:hover { text-decoration:underline; }
        .error-msg { background:#ffe0e0; color:#c00; padding:12px; border-radius:8px; margin-bottom:20px; text-align:center; }
    </style>
</head>
<body>
<div class="container">
    <h1>Edit Vendor</h1>
    <p class="subtitle">Wedding Planning & Vendor Booking System</p>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error-msg"><%= request.getAttribute("error") %></div>
    <% } %>

    <% Vendor vendor = (Vendor) request.getAttribute("vendor");
       if (vendor != null) {
           String cat = vendor.getCategory().toLowerCase();
           boolean isPhoto = cat.equals("photography");
           boolean isCatering = cat.equals("catering");
           boolean isFlorist = cat.equals("florist");
    %>

    <div class="vendor-id">Vendor ID: <%= vendor.getVendorId() %></div>

    <form action="vendor" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="vendorId" value="<%= vendor.getVendorId() %>">
        <input type="hidden" name="category" value="<%= vendor.getCategory() %>">

        <div class="form-group">
            <label for="name">Vendor Name</label>
            <input type="text" id="name" name="name" value="<%= vendor.getName() %>" required>
        </div>

        <div class="form-group">
            <label>Category</label>
            <input type="text" value="<%= vendor.getCategory() %>" disabled style="background:#f0f0f0;">
        </div>

        <div class="form-group">
            <label for="contactInfo">Contact Info</label>
            <input type="text" id="contactInfo" name="contactInfo" value="<%= vendor.getContactInfo() %>" required>
        </div>

        <div class="form-group">
            <label for="pricing">Pricing (Rs.)</label>
            <input type="number" id="pricing" name="pricing" step="0.01" min="0" value="<%= vendor.getPricing() %>" required>
        </div>

        <% if (isPhoto) { PhotographyVendor pv = (PhotographyVendor) vendor; %>
        <div class="extra-fields">
            <h3>Photography Details</h3>
            <div class="form-group">
                <label for="photographyStyle">Photography Style</label>
                <select id="photographyStyle" name="photographyStyle">
                    <option value="Candid" <%= "Candid".equals(pv.getPhotographyStyle()) ? "selected" : "" %>>Candid</option>
                    <option value="Traditional" <%= "Traditional".equals(pv.getPhotographyStyle()) ? "selected" : "" %>>Traditional</option>
                    <option value="Cinematic" <%= "Cinematic".equals(pv.getPhotographyStyle()) ? "selected" : "" %>>Cinematic</option>
                    <option value="Documentary" <%= "Documentary".equals(pv.getPhotographyStyle()) ? "selected" : "" %>>Documentary</option>
                </select>
            </div>
            <div class="form-group">
                <div class="checkbox-group">
                    <input type="checkbox" id="droneAvailable" name="droneAvailable" <%= pv.isDroneAvailable() ? "checked" : "" %>>
                    <label for="droneAvailable" style="margin-bottom:0">Drone Coverage Available</label>
                </div>
            </div>
        </div>
        <% } else if (isCatering) { CateringVendor cv = (CateringVendor) vendor; %>
        <div class="extra-fields">
            <h3>Catering Details</h3>
            <div class="form-group">
                <label for="cuisineType">Cuisine Type</label>
                <select id="cuisineType" name="cuisineType">
                    <option value="Sri Lankan" <%= "Sri Lankan".equals(cv.getCuisineType()) ? "selected" : "" %>>Sri Lankan</option>
                    <option value="Indian" <%= "Indian".equals(cv.getCuisineType()) ? "selected" : "" %>>Indian</option>
                    <option value="Western" <%= "Western".equals(cv.getCuisineType()) ? "selected" : "" %>>Western</option>
                    <option value="Chinese" <%= "Chinese".equals(cv.getCuisineType()) ? "selected" : "" %>>Chinese</option>
                    <option value="Multi-Cuisine" <%= "Multi-Cuisine".equals(cv.getCuisineType()) ? "selected" : "" %>>Multi-Cuisine</option>
                </select>
            </div>
            <div class="form-group">
                <label for="maxGuestCapacity">Max Guest Capacity</label>
                <input type="number" id="maxGuestCapacity" name="maxGuestCapacity" min="1" value="<%= cv.getMaxGuestCapacity() %>">
            </div>
        </div>
        <% } else if (isFlorist) { FloristVendor fv = (FloristVendor) vendor; %>
        <div class="extra-fields">
            <h3>Florist Details</h3>
            <div class="form-group">
                <label for="flowerSpecialty">Flower Specialty</label>
                <select id="flowerSpecialty" name="flowerSpecialty">
                    <option value="Roses" <%= "Roses".equals(fv.getFlowerSpecialty()) ? "selected" : "" %>>Roses</option>
                    <option value="Orchids" <%= "Orchids".equals(fv.getFlowerSpecialty()) ? "selected" : "" %>>Orchids</option>
                    <option value="Lilies" <%= "Lilies".equals(fv.getFlowerSpecialty()) ? "selected" : "" %>>Lilies</option>
                    <option value="Mixed Bouquets" <%= "Mixed Bouquets".equals(fv.getFlowerSpecialty()) ? "selected" : "" %>>Mixed Bouquets</option>
                    <option value="Tropical" <%= "Tropical".equals(fv.getFlowerSpecialty()) ? "selected" : "" %>>Tropical</option>
                </select>
            </div>
            <div class="form-group">
                <div class="checkbox-group">
                    <input type="checkbox" id="venueDecoration" name="venueDecoration" <%= fv.isVenueDecoration() ? "checked" : "" %>>
                    <label for="venueDecoration" style="margin-bottom:0">Venue Decoration Available</label>
                </div>
            </div>
        </div>
        <% } %>

        <button type="submit" class="btn btn-primary">Update Vendor</button>
    </form>
    <% } %>

    <div class="nav-links">
        <a href="vendor?action=list">Back to Vendor List</a>
        <a href="vendor?action=add">Add New Vendor</a>
    </div>
</div>
</body>
</html>
