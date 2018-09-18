package com.uci.pa4;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ProductDisplay extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            DatabaseSetup dbs = new DatabaseSetup();
            String sql;
            sql = "SELECT make,model,product_id,photo1 FROM specs";     
            ResultSet rs = dbs.getStatement().executeQuery(sql);
            ArrayList<ArrayList<String>> items = new ArrayList<>();
            
            // Extract data from result set
            while (rs.next()) {
                ArrayList<String> itemData = new ArrayList<>();
                itemData.add(rs.getString("make"));
                itemData.add(rs.getString("model"));
                itemData.add(rs.getString("product_id"));
                itemData.add(rs.getString("photo1"));
                items.add(itemData);
            }

            //RequestDispatcher rd = request.getRequestDispatcher("template.html");
            //rd.include(request, response);

            String title = "Product Catalog";
            out.println(
                     "<h1 align=\"center\">" + title + "</h1>\n"
                    + "<table align = \"center\"; cellspacing = 15>\n <tr>");
            for(int i = 0; i < items.size()-1; i+=2){
                String name1 = items.get(i).get(0)+ " " + items.get(i).get(1);
                String name2 = items.get(i+1).get(0)+ " " + items.get(i+1).get(1);
                String id1 = items.get(i).get(2);
                String id2 = items.get(i+1).get(2);
                String pic1 = items.get(i).get(3);
                String pic2 = items.get(i+1).get(3);
                out.print("<th>" + name1 + "</th>"+"<td><a href=\"ProductDetails?action="+id1+"\"><img src=\""+pic1+"\" style = \"width: 250px;\"/></a></td></th>\n" + 
                        "<th>" + name2 + "</th>"+"<td><a href=\"ProductDetails?action="+id2+"\"><img src=\""+pic2+"\" style = \"width: 250px;\"/></a></td>" + "</tr>\n");
            }
            if(items.size()%2 == 1){
                String name = items.get(items.size()-1).get(0) + " " + items.get(items.size()-1).get(1);
                String id = items.get(items.size()-1).get(2);
                String pic = items.get(items.size()-1).get(3);
                out.print("<tr><th>" + name + "</th>"+"<td><a href=\"ProductDetails?action="+id+"\"><img src=\""+pic+"\" style = \"width: 250px;\"/></a></td></th></tr>\n");
            }
            out.print("</table>\n");
            
            RequestDispatcher rd2 = request.getRequestDispatcher("/SessionTracking");
            rd2.include(request, response);
            
            //out.print("</body>\n</html>");
            rs.close();
            out.close();
            dbs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
}