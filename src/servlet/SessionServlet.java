package servlet;

import model.User;
import model.UserDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User sessionUser = (User) session.getAttribute("user");
            User dbUser = UserDatabase.getUser(sessionUser.getUsername());
            
            StringBuilder sb = new StringBuilder();
            sb.append("{\"loggedIn\": true, \"username\": \"").append(dbUser.getUsername()).append("\", \"orders\": [");
            List<String> orders = dbUser.getOrderHistory();
            for (int i = 0; i < orders.size(); i++) {
                sb.append("\"").append(orders.get(i).replace("\"", "\\\"")).append("\"");
                if (i < orders.size() - 1) sb.append(",");
            }
            sb.append("]}");
            out.print(sb.toString());
        } else {
            out.print("{\"loggedIn\": false}");
        }
        out.flush();
    }
}
