package servlet;

import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDatabase;
import model.OrderItem;
import model.Product;
import model.ProductDatabase;
import model.User;
import model.UserDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User sessionUser = (User) session.getAttribute("user");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.html");
            return;
        }

        // Validate stock availability for all items before confirming
        for (CartItem ci : cart.getItems()) {
            Product currentProduct = ProductDatabase.getProductById(ci.getProduct().getId());
            if (currentProduct == null || currentProduct.getStock() < ci.getQuantity()) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>");
                out.println("<title>Stock Unavailable | Jyothy Basket</title><link rel='stylesheet' href='css/style.css'></head><body>");
                out.println("<header><h1>Jyothy Basket</h1></header><main><div class='checkout-form' style='text-align:center;'>");
                out.println("<div style='font-size:3rem;margin-bottom:1rem;'>⚠️</div>");
                out.println("<h2 style='color:#ef4444;'>Item Unavailable</h2>");
                out.println("<p style='color:var(--text-secondary);'>Sorry, <strong>" + (currentProduct != null ? currentProduct.getName() : "an item") + "</strong> does not have enough stock remaining.</p>");
                out.println("<a href='cart.html' class='btn' style='margin-top:1.5rem;'><span>Return to Cart</span></a>");
                out.println("</div></main></body></html>");
                return;
            }
        }

        String customerName = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        if (customerName == null || customerName.trim().isEmpty()) {
            customerName = (sessionUser != null ? sessionUser.getUsername() : "Guest Customer");
        }
        if (address == null || address.trim().isEmpty()) {
            address = "Local Store Pickup";
        }
        if (phone == null) phone = "";

        // Build order items and deduct stock from inventory
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            Product p = ProductDatabase.getProductById(ci.getProduct().getId());
            int qty = ci.getQuantity();
            if (p != null) {
                p.reduceStock(qty); // Decrement inventory
                orderItems.add(new OrderItem(p.getId(), p.getName(), qty, p.getUnit(), p.getPrice()));
            }
        }

        // Generate timestamps
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 2);
        String estimatedDelivery = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());

        // Create order
        String orderId = OrderDatabase.generateNextOrderId();
        Order order = new Order(
            orderId,
            sessionUser != null ? sessionUser.getUsername() : "guest",
            customerName.trim(),
            address.trim(),
            phone.trim(),
            orderItems,
            cart.getTotal(),
            now,
            estimatedDelivery,
            "Order Placed"
        );
        OrderDatabase.saveOrder(order);

        // Record in user history
        if (sessionUser != null) {
            User dbUser = UserDatabase.getUser(sessionUser.getUsername());
            if (dbUser != null) {
                String summary = String.format("[%s] %s — ₹%,.2f — %d item(s)", orderId, now, cart.getTotal(), cart.getTotalItemCount());
                dbUser.addOrder(summary);
            }
        }

        // Clear cart
        session.removeAttribute("cart");

        // Render confirmation page
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Order Confirmed | Jyothy Basket</title>");
        out.println("<link rel='stylesheet' href='css/style.css'><script src='js/main.js'></script></head><body>");
        out.println("<header><h1>Jyothy Basket</h1><nav id='nav-links'></nav></header>");
        out.println("<main><div class='checkout-form' style='text-align:center; max-width:620px; margin: 0 auto;'>");
        out.println("<div style='font-size:4rem; margin-bottom:1rem;'>✅</div>");
        out.println("<h2 style='color:#22c55e; margin-top:0; font-size:1.75rem;'>Order Placed Successfully!</h2>");
        out.println("<p style='color:var(--text-secondary); font-size:1.05rem;'>Thank you, <strong style='color:var(--text-primary);'>" + esc(customerName) + "</strong>! Your grocery order is confirmed.</p>");
        out.println("<div style='background:var(--bg-surface); border:1px solid var(--border-color); border-radius:12px; padding:1.5rem; margin:1.5rem 0; text-align:left;'>");
        out.println("<p style='margin:0.4rem 0;'><strong>🧾 Order ID:</strong> <span style='font-family:monospace; font-size:1.15rem; color:var(--accent); font-weight:700;'>" + esc(orderId) + "</span></p>");
        out.println("<p style='margin:0.4rem 0;'><strong>💰 Total Paid:</strong> ₹" + String.format("%,.2f", order.getTotalAmount()) + "</p>");
        out.println("<p style='margin:0.4rem 0;'><strong>📍 Shipping To:</strong> " + esc(address) + "</p>");
        out.println("<p style='margin:0.4rem 0;'><strong>📞 Phone:</strong> " + esc(phone) + "</p>");
        out.println("<p style='margin:0.4rem 0;'><strong>🚚 Estimated Delivery:</strong> " + esc(estimatedDelivery) + "</p>");
        out.println("</div>");
        out.println("<div style='display:flex; gap:1rem; justify-content:center; flex-wrap:wrap;'>");
        out.println("<a href='track-order.html?orderId=" + orderId + "' class='btn'><span>🔍 Live Order Tracking</span></a>");
        out.println("<a href='index.html' class='btn' style='background:transparent; border:1px solid var(--border-color); color:var(--text-primary);'><span>Continue Shopping</span></a>");
        out.println("</div></div></main></body></html>");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
