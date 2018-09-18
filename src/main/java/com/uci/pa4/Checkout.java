/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.pa4;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Matthew
 */
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");

        try (PrintWriter out = response.getWriter()) {
            //format page
            RequestDispatcher rd = request.getRequestDispatcher("template.html");
            rd.include(request, response);
            if (cart == null || cart.size() == 0) {
                out.print("<p style=\"padding-left:20px\">Your cart is empty. Keep shopping and come back later.</p>");
            } else {
                //create arraylist of values to display final cart
                ArrayList<ArrayList<String>> info = new ArrayList<>();
                //if cart has items, show list from session, then pull up price from db
                DatabaseSetup dbs = new DatabaseSetup();
                //create string to contain all product ids
                String itemIDs = "";
                //loop through list of items in cart
                for (int i = 0; i < cart.size(); i++) {
                    itemIDs += cart.get(i)+";";
                    String sql;
                    sql = "SELECT make,model,price FROM specs WHERE product_id=\"" + cart.get(i) + "\"";
                    try (ResultSet rs = dbs.getStatement().executeQuery(sql)) {
                        while (rs.next()) {
                            ArrayList<String> tempInfo = new ArrayList<>();
                            tempInfo.add(rs.getString("make"));
                            tempInfo.add(rs.getString("model"));
                            tempInfo.add(rs.getString("price"));
                            info.add(tempInfo);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dbs.close();
                out.println("<h2 style=\"padding-left: 20px;\">Cart Items</h2>\n");
                //list all items in cart
                out.println("<ul>");
                //calculate total price of cart
                float totalPrice = 0;
                for (int x = 0; x < info.size(); x++) {
                    out.print("<li>" + info.get(x).get(0) + " " + info.get(x).get(1) + ": $" + info.get(x).get(2) + "</li>");
                    totalPrice += Float.valueOf(info.get(x).get(2));
                }
                session.setAttribute("total", totalPrice);
                //convert float to 2 places
                DecimalFormat tot = new DecimalFormat();
                tot.setMaximumFractionDigits(2);
                out.println("</ul>\n");
                //show total price
                out.println("<h3 style=\"padding-left: 20px;\">Total Price: $" + tot.format(totalPrice) + "</h3>\n");
                //create table to complete order
                out.println("<form method=\"post\" action=\"SubmitOrder\" name = \"order_form\" style=\"padding-left: 20px;\">");
                out.println("<table style\"border-spacing: 10px;\">");
                out.println("<tr><td>First Name:</td><td><input type=\"text\" name=\"fname\" autofocus></td></tr>\n");
                out.println("<tr><td>Last Name:</td><td><input type=\"text\" name=\"lname\"></td></tr>\n");
                out.println("<tr><td>Email:</td><td><input type=\"email\" name=\"email\"></td></tr>\n");
                out.println("<tr><td>Street Address:</td><td><input type=\"text\" name=\"street\"></td></tr>\n");
                out.println("<tr><td>City:</td><td><input type=\"text\" name=\"city\" style=\"width: 100px;\"></td></tr>\n");
                out.println("<tr><td>State:</td><td><input type=\"text\" name=\"state\" style=\"width: 50px; maxlength=\"3\"\"></td></tr>\n");
                out.println("<tr><td>Zip Code:</td><td><input type=\"number\" name=\"zip\" maxlength=\"10\" style=\"width: 100px;\"></td></tr>\n");
                out.println("<tr><td>Credit Card Number:</td><td><input type=\"number\" name=\"cc\" maxlength=\"19\"></td></tr>\n");
                out.println("<tr><td>Credit Card CVV:</td><td><input type=\"number\" name=\"cvv\" maxlength=\"3\" style=\"width: 50px;\"></td></tr>\n");
                out.println("<tr><td>Phone Number:</td><td><input type=\"number\" name=\"phone\" maxlength=\"10\" style=\"width: 110px;\"></td></tr>\n");
                out.println("<input type = \"hidden\" name = \"items\" value=\""+itemIDs+"\"/>\n");
                out.println("</table>\n");
                out.println("<input type=\"submit\" value=\"Submit\" name=\"sub\" class=\"sub_button\" />\n");
                out.println("</form>\n");
            }

            out.print("</body>\n</html>");
            out.close();
        }

    }
}
