<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wedding Planning & Vendor Booking System</title>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:'Segoe UI',Tahoma,sans-serif; background:linear-gradient(135deg,#667eea,#764ba2); min-height:100vh; display:flex; justify-content:center; align-items:center; }
        .container { text-align:center; background:rgba(255,255,255,0.95); border-radius:20px; padding:60px 50px; box-shadow:0 25px 80px rgba(0,0,0,.3); max-width:550px; width:90%; }
        h1 { color:#4a1a6b; font-size:32px; margin-bottom:10px; }
        .subtitle { color:#888; font-size:16px; margin-bottom:40px; }
        .menu { display:flex; flex-direction:column; gap:15px; }
        .menu a { display:block; padding:16px 30px; background:linear-gradient(135deg,#667eea,#764ba2); color:#fff; text-decoration:none; border-radius:10px; font-size:16px; font-weight:600; transition:all .3s; }
        .menu a:hover { transform:translateY(-3px); box-shadow:0 10px 30px rgba(118,75,162,.4); }
        .footer { margin-top:30px; color:#aaa; font-size:12px; }
    </style>
</head>
<body>
<div class="container">
    <h1>Wedding Planning System</h1>
    <p class="subtitle">Vendor Management Module</p>
    <div class="menu">
        <a href="vendor?action=add">Add New Vendor</a>
        <a href="vendor?action=list">View All Vendors</a>
        <a href="vendor?action=search">Search Vendors</a>
    </div>
    <p class="footer">vendors</p>
</div>
</body>
</html>
