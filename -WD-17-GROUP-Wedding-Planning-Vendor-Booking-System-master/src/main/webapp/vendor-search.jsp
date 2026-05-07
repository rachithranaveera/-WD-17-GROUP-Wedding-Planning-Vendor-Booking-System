<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.wedding.weddingsystem.models.Vendor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Vendors - Wedding Planning System</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Tahoma,sans-serif; background:linear-gradient(135deg,#667eea,#764ba2); min-height:100vh; padding:30px; }
        .container { background:#fff; border-radius:16px; box-shadow:0 20px 60px rgba(0,0,0,.3); padding:40px; max-width:900px; margin:0 auto; }
        h1 { text-align:center; color:#4a1a6b; margin-bottom:8px; font-size:28px; }
        .subtitle { text-align:center; color:#888; margin-bottom:25px; font-size:14px; }
        .search-form { display:flex; gap:10px; margin-bottom:25px; flex-wrap:wrap; }
        .search-form select, .search-form input { padding:12px 16px; border:2px solid #e0e0e0; border-radius:8px; font-size:14px; outline:none; }
        .search-form select:focus, .search-form input:focus { border-color:#764ba2; }
        .search-form input { flex:1; min-width:200px; }
        .btn { padding:12px 24px; border:none; border-radius:8px; font-size:14px; font-weight:600; cursor:pointer; transition:all .3s; }
        .btn-primary { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; }
        .btn-primary:hover { transform:translateY(-2px); box-shadow:0 8px 25px rgba(118,75,162,.4); }
        table { width:100%; border-collapse:collapse; margin-top:10px; }
        thead th { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; padding:14px 12px; text-align:left; font-size:13px; }
        thead th:first-child { border-radius:10px 0 0 0; }
        thead th:last-child { border-radius:0 10px 0 0; }
        tbody tr:nth-child(even) { background:#f8f6fc; }
        tbody tr:hover { background:#ede7f6; }
        tbody td { padding:12px; border-bottom:1px solid #eee; font-size:14px; color:#333; }
        .category-badge { display:inline-block; padding:4px 12px; border-radius:20px; font-size:12px; font-weight:600; }
        .badge-photography { background:#e3f2fd; color:#1565c0; }
        .badge-catering { background:#fff3e0; color:#e65100; }
        .badge-florist { background:#e8f5e9; color:#2e7d32; }
        .service-desc { font-size:12px; color:#888; font-style:italic; }
        .result-info { color:#666; margin-bottom:15px; font-size:14px; }
        .empty-state { text-align:center; padding:40px; color:#999; }
        .nav-links { display:flex; justify-content:center; gap:15px; margin-top:25px; }
        .nav-links a { color:#764ba2; text-decoration:none; font-size:14px; font-weight:500; }
        .nav-links a:hover { text-decoration:underline; }
    </style>
</head>
<body>
<div class="container">
    <h1>Search Vendors</h1>
    <p class="subtitle">Wedding Planning & Vendor Booking System</p>

    <form action="vendor" method="post" class="search-form">
        <input type="hidden" name="action" value="search">
        <select name="searchType">
            <option value="name" <%= "name".equals(request.getAttribute("searchType")) ? "selected" : "" %>>By Name</option>
            <option value="id" <%= "id".equals(request.getAttribute("searchType")) ? "selected" : "" %>>By ID</option>
            <option value="category" <%= "category".equals(request.getAttribute("searchType")) ? "selected" : "" %>>By Category</option>
        </select>
        <input type="text" name="searchQuery" placeholder="Enter search term..."
               value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <% List<Vendor> vendors = (List<Vendor>) request.getAttribute("vendors");
       if (vendors != null) { %>
        <p class="result-info">Found <strong><%= vendors.size() %></strong> result(s)</p>
        <% if (vendors.size() > 0) { %>
        <table>
            <thead><tr><th>ID</th><th>Name</th><th>Category</th><th>Contact</th><th>Pricing (Rs.)</th><th>Service Description</th></tr></thead>
            <tbody>
            <% for (Vendor v : vendors) {
                String bc = v.getCategory().equalsIgnoreCase("Photography") ? "badge-photography" :
                            v.getCategory().equalsIgnoreCase("Catering") ? "badge-catering" : "badge-florist"; %>
            <tr>
                <td><strong><%= v.getVendorId() %></strong></td>
                <td><%= v.getName() %></td>
                <td><span class="category-badge <%= bc %>"><%= v.getCategory() %></span></td>
                <td><%= v.getContactInfo() %></td>
                <td><%= String.format("%.2f", v.getPricing()) %></td>
                <td class="service-desc"><%= v.getServiceDescription() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } else { %>
        <div class="empty-state"><p>No vendors match your search criteria.</p></div>
        <% } %>
    <% } %>

    <div class="nav-links">
        <a href="vendor?action=list">View All Vendors</a>
        <a href="vendor?action=add">Add Vendor</a>
    </div>
</div>
</body>
</html>
