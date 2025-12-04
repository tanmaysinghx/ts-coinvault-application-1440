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
            // 1. Get Session User (The Sender)
            jakarta.servlet.http.HttpSession session = req.getSession();
            User loggedUser = (User) session.getAttribute("loggedUser");

            if (loggedUser == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            // 2. Get Form Data
            String fromUser = loggedUser.getUsername();
            String toUser = req.getParameter("toUser");
            double amount = Double.parseDouble(req.getParameter("amount"));
            String note = req.getParameter("note");

            // 3. Call the EJB (The Transaction happens here!)
            userService.transfer(fromUser, toUser, amount, note);

            // 4. Success: Redirect to Dashboard
            resp.sendRedirect("dashboard");

        } catch (Exception e) {
            // 5. Failure: Reload page with error message
            req.setAttribute("message", "Error: " + e.getMessage());
            req.getRequestDispatcher("transfer.jsp").forward(req, resp);
        }
    }
}