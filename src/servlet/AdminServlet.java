package servlet;

import model.Order;
import model.OrderDatabase;
import model.OrderItem;
import model.Product;
import model.ProductDatabase;
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

public class AdminServlet extends HttpServlet {

    // Guard: only ADMIN role can use this servlet
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User u = (User) session.getAttribute("user");
        return u != null && u.isAdmin();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (!isAdmin(request)) {
            out.print("{\"error\":\"Access denied\"}");
            out.flush();
            return;
        }

        String action = request.getParameter("action");
        if ("summary".equalsIgnoreCase(action)) {
            out.print(buildSummaryJson());
        } else if ("orders".equalsIgnoreCase(action)) {
            out.print(buildOrdersJson());
        } else if ("inventory".equalsIgnoreCase(action)) {
            out.print(buildInventoryJson());
        } else if ("customers".equalsIgnoreCase(action)) {
            out.print(buildCustomersJson());
        } else {
            out.print("{\"error\":\"Unknown action\"}");
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (!isAdmin(request)) {
            out.print("{\"success\":false,\"message\":\"Access denied\"}");
            out.flush();
            return;
        }

        String action = request.getParameter("action");
        if ("updateStatus".equalsIgnoreCase(action)) {
            String orderId = request.getParameter("orderId");
            String newStatus = request.getParameter("status");
            boolean ok = OrderDatabase.updateOrderStatus(orderId, newStatus);
            out.print(ok ? "{\"success\":true}" : "{\"success\":false,\"message\":\"Order not found\"}");

        } else if ("updateStock".equalsIgnoreCase(action)) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int newStock = Integer.parseInt(request.getParameter("stock"));
                boolean ok = ProductDatabase.updateProductStock(productId, newStock);
                out.print(ok ? "{\"success\":true}" : "{\"success\":false,\"message\":\"Product not found\"}");
            } catch (NumberFormatException e) {
                out.print("{\"success\":false,\"message\":\"Invalid input\"}");
            }

        } else if ("addStock".equalsIgnoreCase(action)) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int addQty = Integer.parseInt(request.getParameter("quantity"));
                boolean ok = ProductDatabase.addProductStock(productId, addQty);
                Product p = ProductDatabase.getProductById(productId);
                String newStockStr = (p != null) ? String.valueOf(p.getStock()) : "?";
                out.print(ok ? "{\"success\":true,\"newStock\":" + newStockStr + "}" : "{\"success\":false,\"message\":\"Product not found\"}");
            } catch (NumberFormatException e) {
                out.print("{\"success\":false,\"message\":\"Invalid input\"}");
            }
        } else {
            out.print("{\"success\":false,\"message\":\"Unknown action\"}");
        }
        out.flush();
    }

    private String buildSummaryJson() {
        List<Order> orders = OrderDatabase.getAllOrders();
        List<User> users = UserDatabase.getAllUsers();
        List<Product> products = ProductDatabase.getAllProducts();
        long lowStock = products.stream().filter(p -> p.getStock() < 5).count();
        long outOfStock = products.stream().filter(p -> !p.isInStock()).count();
        long pending = orders.stream().filter(o -> !"Delivered".equals(o.getStatus()) && !"Cancelled".equals(o.getStatus())).count();
        double revenue = orders.stream().filter(o -> "Delivered".equals(o.getStatus())).mapToDouble(Order::getTotalAmount).sum();

        return "{\"totalOrders\":" + orders.size() +
               ",\"pendingOrders\":" + pending +
               ",\"totalCustomers\":" + users.size() +
               ",\"totalProducts\":" + products.size() +
               ",\"lowStockProducts\":" + lowStock +
               ",\"outOfStockProducts\":" + outOfStock +
               ",\"totalRevenue\":" + revenue + "}";
    }

    private String buildOrdersJson() {
        List<Order> orders = OrderDatabase.getAllOrders();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            sb.append("{")
              .append("\"orderId\":\"").append(esc(o.getOrderId())).append("\",")
              .append("\"customerUsername\":\"").append(esc(o.getCustomerUsername())).append("\",")
              .append("\"customerName\":\"").append(esc(o.getCustomerName())).append("\",")
              .append("\"orderDate\":\"").append(esc(o.getOrderDate())).append("\",")
              .append("\"totalAmount\":").append(o.getTotalAmount()).append(",")
              .append("\"status\":\"").append(esc(o.getStatus())).append("\",")
              .append("\"itemCount\":").append(o.getItems().size())
              .append("}");
            if (i < orders.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String buildInventoryJson() {
        List<Product> products = ProductDatabase.getAllProducts();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            sb.append("{")
              .append("\"id\":").append(p.getId()).append(",")
              .append("\"name\":\"").append(esc(p.getName())).append("\",")
              .append("\"category\":\"").append(esc(p.getCategory())).append("\",")
              .append("\"unit\":\"").append(esc(p.getUnit())).append("\",")
              .append("\"price\":").append(p.getPrice()).append(",")
              .append("\"stock\":").append(p.getStock()).append(",")
              .append("\"inStock\":").append(p.isInStock())
              .append("}");
            if (i < products.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String buildCustomersJson() {
        List<User> users = UserDatabase.getAllUsers();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            List<Order> userOrders = OrderDatabase.getOrdersByUser(u.getUsername());
            sb.append("{")
              .append("\"username\":\"").append(esc(u.getUsername())).append("\",")
              .append("\"email\":\"").append(esc(u.getEmail())).append("\",")
              .append("\"phone\":\"").append(esc(u.getPhone())).append("\",")
              .append("\"role\":\"").append(esc(u.getRole())).append("\",")
              .append("\"registeredDate\":\"").append(esc(u.getRegisteredDate())).append("\",")
              .append("\"totalOrders\":").append(userOrders.size())
              .append("}");
            if (i < users.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
