package servlet;

import model.Order;
import model.OrderDatabase;
import model.OrderItem;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        // action=myOrders → return all orders for logged-in user
        if ("myOrders".equalsIgnoreCase(action)) {
            if (session == null || session.getAttribute("user") == null) {
                out.print("{\"error\":\"Not logged in\"}");
                out.flush();
                return;
            }
            User sessionUser = (User) session.getAttribute("user");
            List<Order> orders = OrderDatabase.getOrdersByUser(sessionUser.getUsername());
            out.print(serializeOrderList(orders));
            out.flush();
            return;
        }

        // action=track (or default) → return single order by orderId
        String orderId = request.getParameter("orderId");
        if (orderId == null || orderId.trim().isEmpty()) {
            out.print("{\"error\":\"Order ID required\"}");
            out.flush();
            return;
        }

        Order order = OrderDatabase.getOrder(orderId.trim().toUpperCase());
        if (order == null) {
            out.print("{\"error\":\"Order not found\"}");
            out.flush();
            return;
        }

        out.print(serializeOrder(order));
        out.flush();
    }

    private String serializeOrder(Order o) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"orderId\":\"").append(esc(o.getOrderId())).append("\",");
        sb.append("\"customerName\":\"").append(esc(o.getCustomerName())).append("\",");
        sb.append("\"deliveryAddress\":\"").append(esc(o.getDeliveryAddress())).append("\",");
        sb.append("\"phone\":\"").append(esc(o.getPhone())).append("\",");
        sb.append("\"orderDate\":\"").append(esc(o.getOrderDate())).append("\",");
        sb.append("\"estimatedDelivery\":\"").append(esc(o.getEstimatedDelivery())).append("\",");
        sb.append("\"status\":\"").append(esc(o.getStatus())).append("\",");
        sb.append("\"stageIndex\":").append(o.getStageIndex()).append(",");
        sb.append("\"totalAmount\":").append(o.getTotalAmount()).append(",");
        sb.append("\"items\":[");
        List<OrderItem> items = o.getItems();
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            sb.append("{")
              .append("\"productId\":").append(item.getProductId()).append(",")
              .append("\"productName\":\"").append(esc(item.getProductName())).append("\",")
              .append("\"quantity\":").append(item.getQuantity()).append(",")
              .append("\"unit\":\"").append(esc(item.getUnit())).append("\",")
              .append("\"price\":").append(item.getPrice()).append(",")
              .append("\"subtotal\":").append(item.getSubtotal())
              .append("}");
            if (i < items.size() - 1) sb.append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String serializeOrderList(List<Order> orders) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < orders.size(); i++) {
            sb.append(serializeOrder(orders.get(i)));
            if (i < orders.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
