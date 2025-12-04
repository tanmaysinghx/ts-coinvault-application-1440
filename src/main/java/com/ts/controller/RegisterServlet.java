package com.ts.controller;

import com.ts.ejb.UserService;
import com.ts.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. Get Data
            String username = req.getParameter("username");
            double initialBalance = Double.parseDouble(req.getParameter("amount"));

            // 2. Call Logic
            User createdUser = userService.registerUser(username, initialBalance);

            // 3. SUCCESS: Pass the 'user' object to the dashboard JSP
            req.setAttribute("user", createdUser);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            // 4. ERROR: Send them back to the form with a message
            req.setAttribute("errorMessage", "Registration Failed: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}