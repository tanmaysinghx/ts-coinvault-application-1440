package com.ts.controller;

import com.ts.ejb.UserService;
import com.ts.model.Transaction;
import com.ts.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// CRITICAL: This mapping "/dashboard" allows the LoginServlet to find it
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        // 1. If not logged in, go back to login page
        if (loggedUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // 2. Refresh User & History
        User freshUser = userService.findUserByName(loggedUser.getUsername());
        session.setAttribute("loggedUser", freshUser);
        List<Transaction> history = userService.getUserHistory(freshUser.getId());
        req.setAttribute("transactions", history);

        // 3. Send data to the JSP
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}