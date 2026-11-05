package servlet;

import model.User;
import model.UserDatabase;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirect = request.getParameter("redirect");
        
        User user = UserDatabase.authenticateParams(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            if (redirect != null && !redirect.trim().isEmpty()) {
                response.sendRedirect(redirect.trim());
            } else if (user.isAdmin()) {
                response.sendRedirect("admin.html");
            } else {
                response.sendRedirect("index.html");
            }
        } else {
            response.sendRedirect("login.html?error=true");
        }
    }
}
