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
            String password = req.getParameter("password");
            double initialBalance = Double.parseDouble(req.getParameter("amount"));

            // 2. Call Logic
            User createdUser = userService.registerUser(username, password, initialBalance);

            // 3. SUCCESS: Create Session & Redirect
            jakarta.servlet.http.HttpSession session = req.getSession();
            session.setAttribute("loggedUser", createdUser);
            resp.sendRedirect("dashboard");

        } catch (Exception e) {
            // 4. ERROR: Send them back to the form with a message
            req.setAttribute("errorMessage", "Registration Failed: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}