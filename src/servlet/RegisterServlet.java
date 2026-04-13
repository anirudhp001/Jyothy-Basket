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
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }
        
        boolean success = UserDatabase.registerUser(username, password);
        if (success) {
            response.sendRedirect("login.html?success=registered");
        } else {
            response.sendRedirect("register.html?error=exists");
        }
    }
}
