// This the servlet code made to register user details.

package com.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        String cnfm = request.getParameter("cnfm");
        PrintWriter out = response.getWriter();

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";

        if (pwd.equals(cnfm)) {
            try {
                Class.forName(driver);
                Connection con = DriverManager.getConnection(url, "SYSTEM", "Aman");
                String query = "insert into register values(?,?)"; 
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, uname);
                ps.setString(2, pwd);

                int count = ps.executeUpdate();

                if (count > 0) {
                    response.sendRedirect("Login.html");
                } else {
                    out.println("Not Registered");
                }

            } catch (Exception e) {
                out.println("exception :: " + e);
            }
        } else {
            out.println("Invalid confirm password"); 
        }
    }
}
