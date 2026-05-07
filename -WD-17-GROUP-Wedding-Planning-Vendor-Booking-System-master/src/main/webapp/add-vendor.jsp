<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Vendor - Wedding Planning System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 30px;
        }

        .container {
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            padding: 40px;
            width: 100%;
            max-width: 650px;
            margin-top: 20px;
        }

        h1 {
            text-align: center;
            color: #4a1a6b;
            margin-bottom: 8px;
            font-size: 28px;
        }

        .subtitle {
            text-align: center;
            color: #888;
            margin-bottom: 30px;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            color: #333;
            font-weight: 600;
            font-size: 14px;
        }

        input, select {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            transition: border-color 0.3s;
            outline: none;
        }

        input:focus, select:focus {
            border-color: #764ba2;
            box-shadow: 0 0 0 3px rgba(118, 75, 162, 0.15);
        }

        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .checkbox-group input[type="checkbox"] {
            width: auto;
            transform: scale(1.3);
            accent-color: #764ba2;
        }

        .extra-fields {
            display: none;
            background: #f8f6fc;
            padding: 20px;
            border-radius: 10px;
            margin-top: 10px;
            border: 1px dashed #c4a7e7;
        }

        .extra-fields.active {
            display: block;
        }

        .extra-fields h3 {
            color: #764ba2;
            margin-bottom: 15px;
            font-size: 16px;
        }

        .btn {
            display: inline-block;
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: all 0.3s;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            width: 100%;
            margin-top: 10px;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(118, 75, 162, 0.4);
        }

        .btn-secondary {
            background: #f0f0f0;
            color: #555;
            margin-top: 10px;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .error-msg {
            background: #ffe0e0;
            color: #cc0000;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
        }

        .nav-links {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        .nav-links a {
            color: #764ba2;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>➕ Add New Vendor</h1>
        <p class="subtitle">Wedding Planning & Vendor Booking System</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="error-msg"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="vendor" method="post">
            <input type="hidden" name="action" value="add">

            <div class="form-group">
                <label for="name">Vendor Name</label>
                <input type="text" id="name" name="name" placeholder="Enter vendor name" required>
            </div>

            <div class="form-group">
                <label for="category">Category</label>
                <select id="category" name="category" required onchange="showExtraFields()">
                    <option value="">-- Select Category --</option>
                    <option value="Photography">Photography</option>
                    <option value="Catering">Catering</option>
                    <option value="Florist">Florist</option>
                </select>
            </div>

            <div class="form-group">
                <label for="contactInfo">Contact Info (Phone/Email)</label>
                <input type="text" id="contactInfo" name="contactInfo" placeholder="e.g., 077-1234567 or vendor@email.com" required>
            </div>

            <div class="form-group">
                <label for="pricing">Pricing (Rs.)</label>
                <input type="number" id="pricing" name="pricing" step="0.01" min="0" placeholder="e.g., 50000.00" required>
            </div>

            <!-- Photography Extra Fields -->
            <div id="photographyFields" class="extra-fields">
                <h3>📷 Photography Details</h3>
                <div class="form-group">
                    <label for="photographyStyle">Photography Style</label>
                    <select id="photographyStyle" name="photographyStyle">
                        <option value="Candid">Candid</option>
                        <option value="Traditional">Traditional</option>
                        <option value="Cinematic">Cinematic</option>
                        <option value="Documentary">Documentary</option>
                    </select>
                </div>
                <div class="form-group">
                    <div class="checkbox-group">
                        <input type="checkbox" id="droneAvailable" name="droneAvailable">
                        <label for="droneAvailable" style="margin-bottom:0">Drone Coverage Available</label>
                    </div>
                </div>
            </div>

            <!-- Catering Extra Fields -->
            <div id="cateringFields" class="extra-fields">
                <h3>🍽️ Catering Details</h3>
                <div class="form-group">
                    <label for="cuisineType">Cuisine Type</label>
                    <select id="cuisineType" name="cuisineType">
                        <option value="Sri Lankan">Sri Lankan</option>
                        <option value="Indian">Indian</option>
                        <option value="Western">Western</option>
                        <option value="Chinese">Chinese</option>
                        <option value="Multi-Cuisine">Multi-Cuisine</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="maxGuestCapacity">Max Guest Capacity</label>
                    <input type="number" id="maxGuestCapacity" name="maxGuestCapacity" min="1" placeholder="e.g., 500">
                </div>
            </div>

            <!-- Florist Extra Fields -->
            <div id="floristFields" class="extra-fields">
                <h3>💐 Florist Details</h3>
                <div class="form-group">
                    <label for="flowerSpecialty">Flower Specialty</label>
                    <select id="flowerSpecialty" name="flowerSpecialty">
                        <option value="Roses">Roses</option>
                        <option value="Orchids">Orchids</option>
                        <option value="Lilies">Lilies</option>
                        <option value="Mixed Bouquets">Mixed Bouquets</option>
                        <option value="Tropical">Tropical</option>
                    </select>
                </div>
                <div class="form-group">
                    <div class="checkbox-group">
                        <input type="checkbox" id="venueDecoration" name="venueDecoration">
                        <label for="venueDecoration" style="margin-bottom:0">Venue Decoration Available</label>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Add Vendor</button>
        </form>

        <div class="nav-links">
            <a href="vendor?action=list">📋 View All Vendors</a>
            <a href="vendor?action=search">🔍 Search Vendors</a>
        </div>
    </div>

    <script>
        function showExtraFields() {
            // Hide all extra fields
            document.getElementById('photographyFields').classList.remove('active');
            document.getElementById('cateringFields').classList.remove('active');
            document.getElementById('floristFields').classList.remove('active');

            // Show relevant fields
            var category = document.getElementById('category').value.toLowerCase();
            if (category === 'photography') {
                document.getElementById('photographyFields').classList.add('active');
            } else if (category === 'catering') {
                document.getElementById('cateringFields').classList.add('active');
            } else if (category === 'florist') {
                document.getElementById('floristFields').classList.add('active');
            }
        }
    </script>
</body>
</html>
