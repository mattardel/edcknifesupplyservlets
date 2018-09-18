<%-- 
    Document   : index
    Created on : Jun 13, 2017, 12:59:38 PM
    Author     : mattardel
--%>

<%@page import="com.uci.pa4.SessionTracking"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.uci.pa4.DatabaseSetup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <link rel="shortcut icon" type="image/x-icon" href="https://image.ibb.co/b6urna/favicon.png">
        <title>EDC Knife Supply</title>
    </head>
    <body>
        <section class="content_box">
            <img id="banner" src="https://image.ibb.co/h6ED0v/banner.png" alt ="Banner"/>
            <ul class="nav_bar">
                <li class="left_nav"><a href="index.jsp">HOME</a></li>
                <li class="left_nav"><a href="about.html">ABOUT</a></li>
                <li class="left_nav"><a href="brands.html">BRANDS</a></li>
                <li class="left_nav"><a href="contact.html">CONTACT</a></li>
                <li class="right_nav"><a href="Checkout">CHECKOUT</a></li>
            </ul>

            <jsp:include page="ProductDisplay" flush="true" />
        </section>
    </body>
</html>
