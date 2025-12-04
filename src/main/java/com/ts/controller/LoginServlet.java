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

// 1. Map this to the form action in login.jsp
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 2. Inject the EJB Service
    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 3. Database Check
        User user = userService.authenticate(username, password);

        if (user != null) {
            // 4. SUCCESS: Create a Session (The "Memory Box")
            HttpSession session = req.getSession();
            session.setAttribute("loggedUser", user);

            // 5. CRITICAL: Redirect to the 'dashboard' SERVLET, not the JSP
            // This ensures the history list is loaded before the page opens.
            resp.sendRedirect("dashboard");
        } else {
            // 6. FAILURE: Send back to login page with error
            req.setAttribute("error", "Invalid username or password.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}