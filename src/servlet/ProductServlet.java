package servlet;

import model.Product;
import model.ProductDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String categoryParam = request.getParameter("category");
        List<Product> products = ProductDatabase.getAllProducts();
        
        StringBuilder json = new StringBuilder("[");
        int count = 0;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (categoryParam != null && !categoryParam.isEmpty() && !"All".equalsIgnoreCase(categoryParam)) {
                if (!p.getCategory().equalsIgnoreCase(categoryParam)) {
                    continue;
                }
            }
            if (count > 0) json.append(",");
            json.append("{")
                .append("\"id\":").append(p.getId()).append(",")
                .append("\"name\":\"").append(p.getName().replace("\"", "\\\"")).append("\",")
                .append("\"price\":").append(p.getPrice()).append(",")
                .append("\"description\":\"").append(p.getDescription().replace("\"", "\\\"")).append("\",")
                .append("\"imageUrl\":\"").append(p.getImageUrl()).append("\",")
                .append("\"rating\":").append(p.getRating()).append(",")
                .append("\"category\":\"").append(p.getCategory()).append("\",")
                .append("\"stock\":").append(p.getStock()).append(",")
                .append("\"unit\":\"").append(p.getUnit()).append("\"")
                .append("}");
            count++;
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
