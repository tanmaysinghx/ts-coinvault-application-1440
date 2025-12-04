package com.ts.controller;

import com.ts.ejb.UserService;
import com.ts.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");
        double amount = Double.parseDouble(req.getParameter("amount"));

        try {
            if ("deposit".equals(action)) {
                userService.deposit(loggedUser.getId(), amount);
            } else if ("withdraw".equals(action)) {
                userService.withdraw(loggedUser.getId(), amount);
            }
            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        }
    }
}
