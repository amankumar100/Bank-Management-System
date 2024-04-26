// // This is the servlet made to delete user details.

package com.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int num = Integer.parseInt(request.getParameter("num"));
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/html");

	        String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@localhost:1521:xe";

	        try {
	            Class.forName(driver);
	            Connection con = DriverManager.getConnection(url, "SYSTEM", "Aman");

	            String query = "delete from account where num=?";
	            PreparedStatement ps = con.prepareStatement(query);

	            ps.setInt(1, num);

	            int count = ps.executeUpdate();
	            if (count > 0)
	                out.println("<h1>Account Deleted</h1>");
	            else
	                out.println("<h1>Deletion failed</h1>");

	            con.close();

	        } catch (ClassNotFoundException | SQLException e) {
	            out.println("<h1>Exception : " + e.getMessage() + "</h1>");
	        }
	    }
	}

