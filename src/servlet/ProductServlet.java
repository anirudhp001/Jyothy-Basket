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
        
        List<Product> products = ProductDatabase.getAllProducts();
        
        // Manual JSON generation to avoid third-party libraries and keep it strictly core Java
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            json.append("{")
                .append("\"id\":").append(p.getId()).append(",")
                .append("\"name\":\"").append(p.getName().replace("\"", "\\\"")).append("\",")
                .append("\"price\":").append(p.getPrice()).append(",")
                .append("\"description\":\"").append(p.getDescription().replace("\"", "\\\"")).append("\",")
                .append("\"imageUrl\":\"").append(p.getImageUrl()).append("\",")
                .append("\"rating\":").append(p.getRating()).append(",")
                .append("\"category\":\"").append(p.getCategory()).append("\"")
                .append("}");
            if (i < products.size() - 1) json.append(",");
        }
        json.append("]");
        
        out.print(json.toString());
        out.flush();
    }
}
