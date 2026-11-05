package servlet;

import model.UserDatabase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            email = username.trim() + "@example.com";
        }
        if (phone == null || phone.trim().isEmpty()) {
            phone = "+91 98450 00000";
        }
        
        boolean success = UserDatabase.registerUser(username.trim(), password, email.trim(), phone.trim());
        if (success) {
            response.sendRedirect("login.html?success=registered");
        } else {
            response.sendRedirect("register.html?error=exists");
        }
    }
}
