<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.wedding.weddingsystem.models.Vendor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Vendors - Wedding Planning System</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Tahoma,sans-serif; background:linear-gradient(135deg,#667eea,#764ba2); min-height:100vh; padding:30px; }
        .container { background:#fff; border-radius:16px; box-shadow:0 20px 60px rgba(0,0,0,.3); padding:40px; max-width:1100px; margin:0 auto; }
        h1 { text-align:center; color:#4a1a6b; margin-bottom:8px; font-size:28px; }
        .subtitle { text-align:center; color:#888; margin-bottom:25px; font-size:14px; }
        .success-msg { background:#e0ffe0; color:#060; padding:12px; border-radius:8px; margin-bottom:20px; text-align:center; }
        .error-msg { background:#ffe0e0; color:#c00; padding:12px; border-radius:8px; margin-bottom:20px; text-align:center; }
        .action-bar { display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; flex-wrap:wrap; gap:10px; }
        .vendor-count { color:#666; font-size:14px; }
        .nav-links { display:flex; gap:15px; }
        .nav-links a { display:inline-block; padding:10px 20px; border-radius:8px; text-decoration:none; font-weight:600; font-size:14px; transition:all .3s; }
        .btn-add { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; }
        .btn-add:hover { transform:translateY(-2px); box-shadow:0 8px 25px rgba(118,75,162,.4); }
        .btn-search { background:#f0f0f0; color:#555; }
        .btn-search:hover { background:#e0e0e0; }
        table { width:100%; border-collapse:collapse; margin-top:10px; }
        thead th { background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; padding:14px 12px; text-align:left; font-size:13px; text-transform:uppercase; }
        thead th:first-child { border-radius:10px 0 0 0; }
        thead th:last-child { border-radius:0 10px 0 0; }
        tbody tr { transition:background .2s; }
        tbody tr:nth-child(even) { background:#f8f6fc; }
        tbody tr:hover { background:#ede7f6; }
        tbody td { padding:12px; border-bottom:1px solid #eee; font-size:14px; color:#333; }
        .category-badge { display:inline-block; padding:4px 12px; border-radius:20px; font-size:12px; font-weight:600; }
        .badge-photography { background:#e3f2fd; color:#1565c0; }
        .badge-catering { background:#fff3e0; color:#e65100; }
        .badge-florist { background:#e8f5e9; color:#2e7d32; }
        .action-btns { display:flex; gap:8px; }
        .action-btns a { padding:6px 14px; border-radius:6px; text-decoration:none; font-size:12px; font-weight:600; transition:all .2s; }
        .btn-edit { background:#fff3e0; color:#e65100; }
        .btn-edit:hover { background:#ffe0b2; }
        .btn-delete { background:#ffebee; color:#c62828; }
        .btn-delete:hover { background:#ffcdd2; }
        .empty-state { text-align:center; padding:60px 20px; color:#999; }
        .empty-state h3 { margin-bottom:10px; color:#764ba2; }
        .service-desc { font-size:12px; color:#888; font-style:italic; }
    </style>
</head>
<body>
<div class="container">
    <h1>All Vendors</h1>
    <p class="subtitle">Wedding Planning & Vendor Booking System</p>

    <% if (request.getAttribute("success") != null) { %>
        <div class="success-msg"><%= request.getAttribute("success") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error-msg"><%= request.getAttribute("error") %></div>
    <% } %>

    <% List<Vendor> vendors = (List<Vendor>) request.getAttribute("vendors");
       int count = (vendors != null) ? vendors.size() : 0; %>

    <div class="action-bar">
        <span class="vendor-count">Total Vendors: <strong><%= count %></strong></span>
        <div class="nav-links">
            <a href="vendor?action=add" class="btn-add">+ Add Vendor</a>
            <a href="vendor?action=search" class="btn-search">Search</a>
        </div>
    </div>

    <% if (count > 0) { %>
    <table>
        <thead><tr><th>ID</th><th>Name</th><th>Category</th><th>Contact</th><th>Pricing (Rs.)</th><th>Service Description</th><th>Actions</th></tr></thead>
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
            <td><div class="action-btns">
                <a href="vendor?action=edit&id=<%= v.getVendorId() %>" class="btn-edit">Edit</a>
                <a href="vendor?action=delete&id=<%= v.getVendorId() %>" class="btn-delete"
                   onclick="return confirm('Delete vendor: <%= v.getName() %>?')">Delete</a>
            </div></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <div class="empty-state"><h3>No vendors found</h3><p>Click "Add Vendor" to register your first vendor.</p></div>
    <% } %>
</div>
</body>
</html>
