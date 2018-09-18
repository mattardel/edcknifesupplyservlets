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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Matthew
 */
public class SessionTracking extends HttpServlet {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        ArrayList<String> history = new ArrayList<String>();

        String action = request.getParameter("action");

        //create table to show viewed items
        String title = "Previously Viewed Items";
        out.println("<body bgcolor=\"#f0f0f0\" style=\"align: center;\">\n"
                + "<h2 align=\"center\"><i>" + title + "</i></h2>\n");
        out.println("<table align = \"center\"; cellpadding = 20>\n");
        if (session.isNew()) {
            out.println("<tr><th>Click on the photos to see each item!</th></tr>");
        } else {
            if (session.getAttribute("viewedItems") != null) {
                history = (ArrayList<String>)session.getAttribute("viewedItems");
                DatabaseSetup dbs = new DatabaseSetup();
                int len = history.size();
                ArrayList<String> photos = new ArrayList<>();
                
                for (int i = len-1; i >= 0; i--){
                    String sql;
                    sql = "SELECT photo1 FROM specs WHERE product_id=\""+history.get(i)+"\"";
                    try {
                        try (ResultSet rs = dbs.getStatement().executeQuery(sql)) {
                            while(rs.next()){
                                photos.add(rs.getString("photo1"));
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(SessionTracking.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dbs.close();
                
                out.print("<tr>");
                if (photos.size()>=5){
                    out.println("<th>");
                    for (int x = 0; x < 5; x++){
                        String currPic = photos.get(x);
                        Integer loc = history.size()-x-1;
                        String currID = history.get(loc);
                        out.println("<td><a href=\"ProductDetails?action="+currID+"\"><img src=\""+currPic+"\" style = \"width: 150px;\"/></a></td>");
                    }
                    out.println("</th>\n");
                }
                
                else if (photos.size()<5&&photos.size()>0){
                    int z = 0;
                    while(z < photos.size()){
                        out.println("<th>");
                        String currPic = photos.get(z);
                        Integer loc = history.size()-z-1;
                        String currID = history.get(loc);
                        out.println("<td><a href=\"ProductDetails?action="+currID+"\"><img src=\""+currPic+"\" style = \"width: 150px;\"/></a></td>");
                        z++;
                    }
                    out.println("</th>\n");
                }
                out.println("</tr>\n");
            }
        }
        out.println("</table>");
        out.close();
    }
}