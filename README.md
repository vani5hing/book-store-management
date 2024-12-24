#Bookstore Management – Project Structure
📦 **Using Java & SQLite**
├── 📁 **src**
│   ├── 📁 **main**
│   │   ├── 📁 **java**
│   │   │   ├── 📁 **capybara.bookstoremanagement**
│   │   │   │   ├── 🛠️ **AdminController.java**       # Manages admin functionalities
│   │   │   │   ├── 🚀 **App.java**                   # Main application entry point
│   │   │   │   ├── 📚 **Book.java**                  # Handles book details
│   │   │   │   ├── 🧑‍🤝‍🧑 **Customer.java**              # Manages customer information
│   │   │   │   ├── 🗃️ **DatabaseUtil.java**          # Database connection and utilities
│   │   │   │   ├── 👥 **Employee.java**              # Manages employee details
│   │   │   │   ├── 📊 **EmployeeController.java**    # Controls employee-related actions
│   │   │   │   ├── 📦 **Item.java**                  # Manages item details
│   │   │   │   ├── 🔑 **LoginController.java**       # Handles user login
│   │   │   │   ├── 🧠 **ManagerController.java**     # Oversees system operations
│   │   │   │   ├── 📖 **ManageBooksController.java** # Manages book operations
│   │   │   │   ├── 🛡️ **ManageCustomersController.java** # Handles customer operations
│   │   │   │   ├── 📑 **ManageEmployeesController.java** # Oversees employee management
│   │   │   │   ├── 📦 **ManageOrdersController.java** # Manages order operations
│   │   │   │   ├── 🛒 **Order.java**                 # Tracks order details
│   │   │   │   ├── 📝 **SignUpController.java**      # Handles user registration
│   │   │   │   ├── 👤 **User.java**                  # Manages user accounts
│   │   │   ├── 📁 **resources**
│   │   │   │   ├── 🖥️ **admin_view.fxml**                # Admin panel UI
│   │   │   │   ├── 🧑‍💼 **employee_view.fxml**             # Employee dashboard UI
│   │   │   │   ├── 🔐 **login.fxml**                     # Login screen UI
│   │   │   │   ├── 📚 **manage_books.fxml**              # Manage books UI
│   │   │   │   ├── 🛍️ **manage_customers.fxml**          # Manage customers UI
│   │   │   │   ├── 📑 **manage_employees.fxml**          # Manage employees UI
│   │   │   │   ├── 📦 **manage_orders.fxml**             # Manage orders UI
│   │   │   │   ├── 🧠 **manager_view.fxml**              # Manager dashboard UI
│   │   │   │   ├── 🏠 **menu.fxml**                      # Main menu UI
│   │   │   │   ├── 🎯 **primary.fxml**                   # Primary UI layout
│   │   │   │   ├── 🖥️ **secondary.fxml**                 # Secondary UI layout
│   │   │   │   ├── 📝 **signup.fxml**                    # Signup screen UI
│   │   │   │   ├── 📊 **books_dataset.json**             # Sample dataset for books
│   │   ├── 📁 **test**
│   │   │   ├── 🧪 **DatabaseTest.java**                 # Unit tests for database operations
│   │   │   ├── ⚙️ **ControllerTest.java**               # Unit tests for controllers
│   │
├── 📄 **pom.xml**                                   # Maven dependencies and configuration
├── 📘 **README.md**                                 # Project documentation
├── 📜 **LICENSE**                                   # License information
├── 🚫 **.gitignore**                                # Files ignored by version control
├── 🗂️ **bookstore.db**                              # Primary SQLite database
├── 🗂️ **books.db**                                  # Secondary SQLite database
│
🛠️ **Build**
├── 📁 **target**
│   ├── 🏗️ Compiled classes and resources
│
📊 **Dataset**
├── 📄 **books_dataset.json**                        # Sample book dataset
│
🖼️ **BookStoreManagement.png**                       # Project overview diagram/image
