// This the servlet code made to login user.

package com.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";

        try {
            Class.forName(driver);
            try (Connection con = DriverManager.getConnection(url, "SYSTEM", "Aman")) {
                String query = "select * from register where uname=? and pwd=?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, uname);
                    ps.setString(2, pwd);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            response.sendRedirect("input.html");
                        } else {
                            request.setAttribute("errorMessage", "Login Failed - Try Again");
                            request.getRequestDispatcher("Login.html").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception e) {
            out.println("<h4 style='color:red'> Error: " + e.getMessage() + "</h4>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the POST request to doGet()
        doGet(request, response);
    }
}
