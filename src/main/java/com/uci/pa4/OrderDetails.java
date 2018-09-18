/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uci.pa4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class OrderDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        ArrayList<String> custInfo = new ArrayList<>();
        ArrayList<String> cart = (ArrayList<String>) session.getAttribute("cart");
        
        try (PrintWriter out = response.getWriter()) {
            RequestDispatcher rd = request.getRequestDispatcher("template.html");
            rd.include(request, response);
            Enumeration params = request.getParameterNames();
            //get value of each parameter
            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                String[] pVals = request.getParameterValues(param);
                String pVal = (String) pVals[0];
                custInfo.add(pVal);
            }
            //remove submit element of arraylist
            custInfo.remove(custInfo.size() - 1);
            out.println("<h1>Order Details:</h1>");
            out.println("<h3>Items Purchased</h3><ul>");
            for(int i = 0; i < cart.size(); i++){
                out.println("<li"+cart.get(i)+"</li>");
            }
            out.println("</ul>");
            out.println("<h3>Customer Details</h3><ul>");
            for(int z = 0; z < custInfo.size(); z++){
                out.println("<li>"+custInfo.get(z)+"</ul>");
            }
            out.println("</ul>");
            out.println("</body></html>\n");
        }
        
        session.setAttribute("cart",null);
        session.setAttribute("total", 0);
    }
}
