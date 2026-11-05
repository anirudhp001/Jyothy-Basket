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
            if (dbUser == null) {
                out.print("{\"loggedIn\": false}");
                out.flush();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("{\"loggedIn\": true,");
            sb.append("\"username\": \"").append(esc(dbUser.getUsername())).append("\",");
            sb.append("\"role\": \"").append(esc(dbUser.getRole())).append("\",");
            sb.append("\"email\": \"").append(esc(dbUser.getEmail())).append("\",");
            sb.append("\"orders\": [");
            List<String> orders = dbUser.getOrderHistory();
            for (int i = 0; i < orders.size(); i++) {
                sb.append("\"").append(esc(orders.get(i))).append("\"");
                if (i < orders.size() - 1) sb.append(",");
            }
            sb.append("]}");
            out.print(sb.toString());
        } else {
            out.print("{\"loggedIn\": false}");
        }
        out.flush();
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
