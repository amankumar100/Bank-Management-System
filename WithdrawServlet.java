// This is the servlet code made to withdraw amount of user.

package com.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        int num = Integer.parseInt(request.getParameter("num"));
	        String name = request.getParameter("name");
	        int amt = Integer.parseInt(request.getParameter("amt")); // corrected parameter name to "amt"
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/html");
	        out.println("<h1>Account Number = " + num + "</h1>");
	        out.println("<h1>Amount = " + amt + "</h1>");

	        String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@localhost:1521:xe";

	        try {
	            Class.forName(driver);
	            Connection con = DriverManager.getConnection(url, "SYSTEM", "Aman");

	            String query = "select balance from account where num=?";
	            PreparedStatement ps = con.prepareStatement(query);
	            ps.setInt(1, num);
	            ResultSet rs = ps.executeQuery();
	            int bal = 0;
	            if (rs.next()) {
	                bal = rs.getInt("balance");
	                if (bal >= amt) {
	                    query = "update account set balance=balance-? where num=?";
	                    ps = con.prepareStatement(query);
	                    ps.setInt(1, amt); // corrected parameter index to 1
	                    ps.setInt(2, num);
	                    int count = ps.executeUpdate();
	                    if (count > 0)
	                        out.println("<h1>Withdraw successful</h1>");
	                } else {
	                    out.println("<h1>Insufficient balance</h1>");
	                }
	            } else {
	                out.println("<h1>Account not found</h1>");
	            }
	            con.close(); // Close connection after usage

	        } catch (ClassNotFoundException | SQLException e) {
	            out.println("<h1>Exception : " + e.getMessage() + "</h1>");
	        }
	    }
	}
