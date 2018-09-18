/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.pa4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class AddToCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
        if(cart==null){
            cart = new ArrayList<>();
        }

        try (PrintWriter out = response.getWriter()) {
            //add item to cart and modify attribute
            String product = request.getParameter("product_id");
            cart.add(product);
            session.setAttribute("cart", cart);
            //import header and HTML
            RequestDispatcher rd = request.getRequestDispatcher("template.html");
            rd.include(request, response);
            //show added item
            String item = request.getParameter("name");
            item = item.replaceAll("_", " ");
            out.println("<h2 style=\"text-align:center; padding-top: 50px;\">"+item+" added to cart!</h2>");
            out.println("<div style=\"text-align:center; padding-top: 10px;\">");
            out.println("<a href=\"ProductDisplay\">");
            out.println("<input type=\"button\" value=\"Keep Shopping\" style = \"padding:10px;font-size:1.5em;margin-right:6%;\"/>\n");
            out.println("</a>");
            out.println("<a href=\"Checkout\">");
            out.println("<input type=\"button\" value=\"Checkout\" style = \"padding:10px;font-size:1.5em;\"/>\n");
            out.println("</a>");
            out.println("</div>\n");
            out.println("</body>\n");
            out.println("</html>\n");
            out.close();
        }
    }
}