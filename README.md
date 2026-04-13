# Simple E-Commerce Website

An intermediate-level conceptual e-commerce web application built strictly with Core Java, Servlets, HTML, and CSS. This project focuses entirely on backend data management using core Object-Oriented Programming (OOP) concepts, Collections, and HTTP Session handling, completely substituting large frameworks with vanilla technologies. JavaScript is used minimally (only using `fetch`) strictly to request Java Servlet backend data directly into `.html` pages (preventing the need for JSP).

## Features Implemented
- **Home/Products Page**: Dynamically renders predefined products via `ProductServlet`.
- **Shopping Cart**: Fully functional add/remove logic, managed entirely using `HttpSession` state in `CartServlet`.
- **Checkout Processing**: Resets active session Cart and dynamically prints a confirmation via `CheckoutServlet`.
- **Basic Login Mechanism**: Dummy User session stored in `LoginServlet` for OOP demonstration.

## Technologies Used
- **Backend Model**: Core Java (Classes, Encapsulation, ArrayList Collections).
- **Backend Controller**: Java Servlets (`HttpServlet`, `doGet`, `doPost`, `HttpSession`).
- **Frontend View**: HTML5 & Vanilla CSS (Flexbox/Grid layout used, completely responsive with NO CSS frameworks).

## Step-By-Step: Running in VS Code Using Apache Tomcat
1. **Prerequisites**: Ensure you have **Java JDK 11+**, **VS Code**, and the Extension **"Community Server Connectors"** by Red Hat or **"Tomcat for Java"** installed in VS Code.
2. **Setup Tomcat**: Download [Apache Tomcat 9](https://tomcat.apache.org/) (Tomcat 9 is necessary as the models use `javax.servlet`. Tomcat 10+ requires `jakarta.servlet`) and extract it.
3. **Open Project**: Open the `SimpleEcommerce` folder directly in VS Code.
4. **Compile Java Files**:
   - For proper deployment, Java Servlets must be compiled into `webapp/WEB-INF/classes`. You need the Servlet API jar located in your Tomcat's `lib/servlet-api.jar`.
   - Compile via terminal (from the project root):
     ```cmd
     mkdir webapp\WEB-INF\classes
     javac -cp "PATH_TO_TOMCAT\lib\servlet-api.jar" -d webapp\WEB-INF\classes src\model\*.java src\servlet\*.java
     ```
   *(Note: Adjust `PATH_TO_TOMCAT` to where you saved your Apache Tomcat folder. Alternatively, you can use Eclipse/IntelliJ or Maven which automate compilation).*
5. **Run the Server**:
   - Go to your VS Code "Servers" panel.
   - Click "Add New Server" -> Select the extracted Tomcat 9 folder.
   - Right-click the server -> "Start".
   - Right-click the server -> "Add Deployment" -> Select the `webapp` folder in your workspace.
   - Once deployed, open a browser and navigate to: `http://localhost:8080/webapp/index.html` (or whatever mapping the VS Code extension gave it). Explore the cart, checkout, and product mapping logic!
