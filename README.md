# 📚 **Bookstore Management**

![Bookstore Management Banner](path/to/banner.png)

## 📖 **Introduction**  
The **Bookstore Management System** is a comprehensive and efficient solution designed to simplify the management of bookstore operations. It offers functionalities for managing books, customers, employees, and sales orders. Built using **JavaFX** and **SQLite**, this system ensures smooth operations with an intuitive user interface and reliable database management.

![App Screenshot](path/to/screenshot.png)

## 📸 **Screenshots**
- **Login Screen:**  
  ![Login Screen](path/to/login.png)
- **Admin Dashboard:**  
  ![Admin Dashboard](path/to/admin_dashboard.png)
- **Book Management:**  
  ![Book Management](path/to/book_management.png)

---

## 🚀 **Overview**
In the modern era of digital transformation, managing a bookstore manually can be tedious and error-prone. Our **Bookstore Management System** eliminates these challenges by:
- Streamlining inventory management.  
- Tracking customer purchase history.  
- Managing employee records efficiently.  
- Providing secure authentication and access control.  
- Generating insightful sales and inventory reports.

---

## 🛠️ **Key Features**
### 📚 **Books Management**
- Add, update, and delete book details.
- Track book stock levels in real-time.
- Monitor sales status for each book.

### 🧑‍🤝‍🧑 **Customer Management**
- Maintain customer profiles with purchase history.
- Track customer contact details and preferences.

### 🛒 **Order Management**
- Create and manage customer orders.
- Update order status dynamically.

### 👥 **Employee Management**
- Add, update, and manage employee records.
- Assign roles and access levels.

### 🔑 **User Authentication**
- Secure login for administrators and employees.
- Role-based access control for different user types.

### 📊 **Reports and Analytics**
- Generate sales performance reports.
- Analyze inventory trends.
- Export data for external use.

---

## 💻 **Technologies Used**
| Technology | Purpose |
|------------|---------|
| ⚙️ **Java 17** | Backend programming language |
| 🖥️ **JavaFX** | GUI framework |
| 🗃️ **SQLite** | Database management |
| 📦 **Maven** | Build and dependency management |
| 📊 **JSON** | Data storage for configurations and samples |

---

## 🚀 **Installation & Setup**
Follow the steps below to set up the project locally:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/respectthanh/book-store-management.git
    ```
2. **Navigate to the project directory:**
    ```bash
    cd bookstore-management
    ```
3. **Build the project:**
    ```bash
    mvn clean install
    ```
4. **Run the application:**
    ```bash
    java -jar target/bookstore-management.jar
    ```
5. **Access the Application:**
    - Default Admin Username: `admin`
    - Default Admin Password: `password`

---

#Bookstore Management – Project Structure
# 📚 **Bookstore Management – Project Structure**

## 📦 **Using Java & SQLite**
- **📁 src**
   - **📁 main**
     - **📁 java**
       - **📂 capybara.bookstoremanagement**
         - 🛠️ **AdminController.java** - Manages admin functionalities  
         - 🚀 **App.java** - Main application entry point  
         - 📚 **Book.java** - Handles book details  
         - 🧑‍🤝‍🧑 **Customer.java** - Manages customer information  
         - 🗃️ **DatabaseUtil.java** - Database connection and utilities  
         - 👥 **Employee.java** - Manages employee details  
         - 📊 **EmployeeController.java** - Controls employee-related actions  
         - 🔑 **LoginController.java** - Handles user login  
         - 🧠 **ManagerController.java** - Oversees system operations  
         - 📖 **ManageBooksController.java** - Manages book operations  
         - 🛡️ **ManageCustomersController.java** - Handles customer operations  
         - 📑 **ManageEmployeesController.java** - Oversees employee management  
         - 🛒 **Order.java** - Tracks order details  
         - 📝 **SignUpController.java** - Handles user registration  
         - 👤 **User.java** - Manages user accounts  

   - **📁 resources**
     - 🖥️ **admin_view.fxml** - Admin panel UI  
     - 🔐 **login.fxml** - Login screen UI  
     - 📚 **manage_books.fxml** - Manage books UI  
     - 🛍️ **manage_customers.fxml** - Manage customers UI  
     - 🧠 **manager_view.fxml** - Manager dashboard UI  
     - 🏠 **menu.fxml** - Main menu UI  
     - 🎯 **primary.fxml** - Primary UI layout  
     - 📝 **signup.fxml** - Signup screen UI  
     - 📊 **books_dataset.json** - Sample dataset for books  

- **📁 test**
   - 🧪 **DatabaseTest.java** - Unit tests for database operations  
   - ⚙️ **ControllerTest.java** - Unit tests for controllers  

- **📄 pom.xml** - Maven dependencies and configuration  
- **📘 README.md** - Project documentation  
- **📜 LICENSE** - License information  
- 🚫 **.gitignore** - Files ignored by version control  
- 🗂️ **bookstore.db** - Primary SQLite database  
- 🗂️ **books.db** - Secondary SQLite database  

## 🛠️ **Build**
- 📁 **target** - Compiled classes and resources  

## 📊 **Dataset**
- 📄 **books_dataset.json** - Sample book dataset  

## 🖼️ **Assets**
- 📊 **BookStoreManagement.png** - Project overview diagram/image  
---

## 📜 **License**
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 📝 **Notes**
- Ensure **Java 17** is installed.  
- Database tables are created automatically on the first run.

---

**✨ Happy Coding! 🚀**
