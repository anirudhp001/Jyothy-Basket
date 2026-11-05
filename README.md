# Jyothy Basket — Grocery E-Commerce Platform

[![Deploy with Vercel](https://vercel.com/button)](https://vercel.com/new/clone?repository-url=https%3A%2F%2Fgithub.com%2Fanirudhp001%2FJyothy-Basket)

A full-featured grocery e-commerce web platform modeled after quick-commerce leaders like **BigBasket**. Built for Software Engineering & Project Management (SEPM), this system delivers an end-to-end grocery shopping, order fulfillment, real-time delivery tracking, and operations management experience.

---

## 🚀 Live 1-Click Deployment on Vercel

You can deploy this project instantly to Vercel with zero server setup:

[![Deploy with Vercel](https://vercel.com/button)](https://vercel.com/new/clone?repository-url=https%3A%2F%2Fgithub.com%2Fanirudhp001%2FJyothy-Basket)

1. Click the **Deploy with Vercel** button above (or open [vercel.com/new](https://vercel.com/new)).
2. Connect your GitHub account and select `anirudhp001/Jyothy-Basket`.
3. Click **Deploy**. Vercel will automatically build the static assets and serverless API functions to generate a live public URL (e.g. `https://jyothy-basket.vercel.app`).

---

## ✨ Features Implemented

- 🥦 **Grocery Catalog & Category Filters**: 21 authentic grocery items across 4 categories (*Fresh Produce*, *Dairy & Bakery*, *Pantry & Staples*, *Beverages & Snacks*) with metric units (kg, L, pack, dozen).
- 🛒 **Interactive Shopping Cart**: Quantity adjustment (`+` / `−`), pre-stock limits, and instant subtotal re-calculation via non-blocking AJAX toast notifications.
- 💳 **Checkout with Order Preview**: Live itemized receipt and total calculation, capturing customer delivery address and phone number.
- 🚚 **Real-Time 4-Stage Delivery Tracking**: Visual delivery timeline (*Order Placed ➔ Processing ➔ Out for Delivery ➔ Delivered*) with order search and direct receipt links (`track-order.html?orderId=ORD-1001`).
- 📦 **Inventory Management & Restock**: Stock auto-decrements upon purchase; staff can restock items with 1 click.
- ⚙️ **Staff & Admin Management Portal**: Dedicated dashboard (`admin.html`) to monitor revenue, live in-progress orders, stock levels, advance order statuses, and view customer accounts.
- 👤 **Customer Account Management**: Sign in, register, and review past order histories.

---

## 🔑 Demo Credentials

| Role | Username | Password | Access Privileges |
| :--- | :--- | :--- | :--- |
| **Customer** | `anirudh` | `ani123` | Shopping, cart, checkout, order tracking, order history |
| **Admin / Staff** | `admin` | `admin123` | Staff portal, order delivery updates, inventory restocking, customer list |

---

## 💻 Local Setup (Apache Tomcat)

1. **Prerequisites**: JDK 11+, Apache Tomcat 9.
2. **Compile Java Servlets**:
   ```powershell
   javac -cp "lib/javax.servlet-api-4.0.1.jar;src" -d "webapp/WEB-INF/classes" src/model/*.java src/servlet/*.java
   ```
3. **Run in Tomcat**:
   Set `docBase` to the `webapp` folder and launch Tomcat. Access at `http://localhost:8080/SimpleEcommerce/`.
