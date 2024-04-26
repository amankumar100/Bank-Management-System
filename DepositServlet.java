// This is the servlet made to deposit amount of user

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

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        int num = Integer.parseInt(request.getParameter("num"));
	        int amt = Integer.parseInt(request.getParameter("amt"));
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/html");
	        out.println("<h1>Account Number = " + num + "</h1>");
	        out.println("<h1>Amount = " + amt + "</h1>");

	        String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@localhost:1521:xe";

	        try {
	            Class.forName(driver);
	            Connection con = DriverManager.getConnection(url, "SYSTEM", "Aman");

	            String query = "update account set balance=balance+? where num=?";
	            PreparedStatement ps = con.prepareStatement(query);
	            ps.setInt(1, amt);
	            ps.setInt(2, num);
	            int count = ps.executeUpdate();
	            if (count > 0)
	                out.println("<h1>Deposited</h1>");
	            else
	                out.println("<h1>Deposition failed</h1>");

	            con.close();

	        } catch (ClassNotFoundException | SQLException e) {
	            out.println("<h1>Exception : " + e.getMessage() + "</h1>"); 
	        }
	    }

	}
