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

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. Get Form Data
            String fromUser = req.getParameter("fromUser");
            String toUser = req.getParameter("toUser");
            double amount = Double.parseDouble(req.getParameter("amount"));

            // 2. Call the EJB (The Transaction happens here!)
            userService.transfer(fromUser, toUser, amount);

            // 3. Fetch updated sender info to display on Dashboard
            User updatedSender = userService.findUserByName(fromUser);

            // 4. Success: Forward to Dashboard
            req.setAttribute("user", updatedSender);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            // 5. Failure: Reload page with error message
            req.setAttribute("message", "Error: " + e.getMessage());
            req.getRequestDispatcher("transfer.jsp").forward(req, resp);
        }
    }
}