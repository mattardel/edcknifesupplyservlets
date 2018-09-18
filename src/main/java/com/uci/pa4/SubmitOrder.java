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
import java.util.Enumeration;
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
public class SubmitOrder extends HttpServlet {

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
            DatabaseSetup dbs = new DatabaseSetup();
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
            //get all items in cart
            String allItems = "";
            for(int z = 0; z < cart.size(); z++){
                allItems = allItems + cart.get(z) + "; ";
            }
            String sql;
            sql = "INSERT INTO cx VALUES(";
            for(int i = 0; i < custInfo.size(); i++){
                sql = sql+custInfo.get(i)+", ";
            }
            sql = sql+allItems+session.getAttribute("total")+");";
            
            try {
                dbs.getStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(SessionTracking.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<h1 style=\"padding-left: 20px;\">Order Details</h1>");
            out.println("<h3 style=\"padding-left: 20px;\">Items Purchased:</h3><ul>");
            out.println("<li>"+allItems+"</li>");
            out.println("</ul>");
            out.println("<h3 style=\"padding-left: 20px;\">Customer Details</h3><ul>");
            out.println("<li>Name: "+custInfo.get(0)+" "+custInfo.get(1)+"</li>");
            out.println("<li>Email: "+custInfo.get(2)+"</li>");
            out.println("<li>Address: "+custInfo.get(3)+", "+custInfo.get(4)+", "+custInfo.get(5)+" "+custInfo.get(6)+"</li>");
            out.println("</ul>");
            out.println("<h3 style=\"padding-left: 20px;\">Total Cost:</h3><ul><li>$");
            out.println(session.getAttribute("total")+"</li></ul>");
            
            dbs.close();
            out.close();
            session.setAttribute("cart",null);
            session.setAttribute("total", 0);
            session.invalidate();
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderDetails");
        dispatcher.forward(request, response);
        
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
