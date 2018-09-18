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
public class ProductDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //get parameter passed through URL
        String action = request.getParameter("action");
        //create printwriter
        PrintWriter out = response.getWriter();
        //add item to viewed arraylist in session
        HttpSession session = request.getSession(true);
        ArrayList<String> viewed = (ArrayList<String>) session.getAttribute("viewedItems");
        if (viewed == null) {
            //if no items in viewed arraylist, create new non-null arraylist
            viewed = new ArrayList<>();
        } else {
        }

        viewed.add(action);
        session.setAttribute("viewedItems", viewed);

        ArrayList<String> specs = new ArrayList<>();
        DatabaseSetup dbs = new DatabaseSetup();

        String sql = "SELECT * FROM specs WHERE product_id=\"" + action + "\"";
        try {
            try (ResultSet rs = dbs.getStatement().executeQuery(sql)) {
                while (rs.next()) {
                    specs.add(rs.getString("make")); //0
                    specs.add(rs.getString("model")); //1
                    specs.add(rs.getString("mechanism")); //2
                    specs.add(rs.getString("action")); //3
                    specs.add(rs.getString("blade_length")); //4
                    specs.add(rs.getString("blade_thickness")); //5
                    specs.add(rs.getString("open_length")); //6
                    specs.add(rs.getString("closed_length")); //7
                    specs.add(rs.getString("handle")); //8
                    specs.add(rs.getString("steel")); //9
                    specs.add(rs.getString("handle_thickness")); //10
                    specs.add(rs.getString("weight")); //11
                    specs.add(rs.getString("product_id")); //12
                    specs.add(rs.getString("photo1")); //13
                    specs.add(rs.getString("photo2")); //14
                    specs.add(rs.getString("price")); //15
                }
                dbs.close();
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionTracking.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestDispatcher rd = request.getRequestDispatcher("template.html");
        rd.include(request, response);
        //print name of item
        out.println("<h1 style=\"padding-left: 20px;\">" + specs.get(0) + " " + specs.get(1) + "</h1>\n");
        //show photos
        out.println("<div style = \"text-align:center;\">\n");
        out.println("<img class=\"product_pics\" src=\"" + specs.get(13) + "\"/>\n");
        out.println("<img class=\"product_pics\" src=\"" + specs.get(14) + "\"/>\n");
        out.println("</div>\n");
        //title
        out.println("<h3 style=\"padding-left: 20px;\">Specifications</h3>");
        //start spec list
        out.println("<ul style=\"padding-left: 60px;\">");
        out.println("<li>Mechanism: " + specs.get(2) + "</li>");
        out.println("<li>Action: " + specs.get(3) + "</li>");
        out.println("<li>Blade Length: " + specs.get(4) + "\"</li>");
        out.println("<li>Blade Thickness: " + specs.get(5) + "\"</li>");
        out.println("<li>Blade Steel: " + specs.get(9) + "</li>");
        out.println("<li>Open Length: " + specs.get(6) + "\"</li>");
        out.println("<li>Closed Length: " + specs.get(7) + "\"</li>");
        out.println("<li>Handle Material: " + specs.get(8) + "</li>");
        out.println("<li>Handle Thickness: " + specs.get(10) + "\"</li>");
        out.println("<li>Weight: " + specs.get(10) + "<oz/li>");
        out.println("<li>Product ID: " + specs.get(12) + "</li>");

        //close list
        out.println("</ul>\n");

        //display price
        out.println("<h3 style=\"padding-left: 20px;\">Price: <i>$" + specs.get(15) + "</i></h3>");

        //add to cart button
        out.println("<form action=\"AddToCart\" class=\"cartbutton\" style=\"padding-left: 3%;\">");
        out.println("<input type = \"hidden\" name = \"product_id\" value =" + specs.get(12) + ">");
        String name = (specs.get(0) + " " + specs.get(1));
        name = name.replaceAll(" ", "_");
        out.println("<input type = \"hidden\" name = \"name\" value =" + name + ">");
        out.println("<input type=\"submit\" value=\"Add to Cart\"/>");
        out.println("</form>");
        //hyperlink back home
        out.println("<div style=\"text-align:center; padding-top: 30px;\">");
        out.println("<form action=\"index.jsp\">");
        out.println("<input type=\"submit\" value=\"Home\" />\n");
        out.println("</form>");
        out.println("</div>\n");
        out.println("</body>\n");
        out.println("</html>\n");
        out.close();
    }
}
