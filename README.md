# PAYROLL MANAGEMENT SYSTEM

A Java-based GUI application demonstrating Object-Oriented Programming (OOP) concepts through a complete payroll management system.

---

## 📋 Features

- **User Authentication**: Secure login with 3-attempt limit
- **Employee Management**: Add and manage full-time and part-time employees
- **Payroll Processing**: Calculate salaries based on work hours, deductions, and bonuses
- **Payroll Configuration**: Configure tax rates and SSS contributions
- **Responsive GUI**: Simple, clean, and user-friendly interface

---

## 🎯 OOP Concepts Demonstrated

### 1. **Encapsulation & Data Hiding**
   - Private fields in all classes
   - Public getter methods only (no direct access)
   - Example: `Employee` class with private `id`, `name`, `designation`, `baseSalary`

### 2. **Inheritance**
   - `Employee` abstract class as parent
   - `FullTimeEmployee` and `PartTimeEmployee` extend `Employee`
   - Child classes inherit common properties and implement specific behavior

### 3. **Polymorphism & Abstraction**
   - Abstract method `calculateSalary()` in `Employee` class
   - Each employee type implements its own salary calculation logic
   - Different hourly rates for FullTime (PHP 500/hr) and PartTime (PHP 250/hr)

### 4. **Exception Handling**
   - `InvalidEmployeeException`: Thrown when invalid employee data is provided
   - `PayrollCalculationException`: Thrown when payroll calculation fails
   - Try-catch blocks for safe error handling

---

## 📁 File Structure

```
OOP-Payroll-System/
├── Main.java                           # Entry point with main() method
├── PayrollSystem.java                  # Main GUI application class
├── Employee.java                       # Abstract base class
├── FullTimeEmployee.java               # Full-time employee implementation
├── PartTimeEmployee.java               # Part-time employee implementation
├── PayrollConfig.java                  # Configuration management
├── InvalidEmployeeException.java       # Custom exception
├── PayrollCalculationException.java    # Custom exception
└── README.md                           # This file
```

---

## 🚀 How to Compile & Run

### **Step 1: Compile all Java files**
```bash
javac *.java
```

### **Step 2: Run the application**
```bash
java Main
```

The GUI window will open with the login screen.

---

## 🔐 Login Credentials

- **Username**: `admin`
- **Password**: `admin123`

**Note**: Failed login attempts are limited to 3. After 3 failed attempts, the system will lock.
**Note**: You can change the login credential in line 170 in the **_PayrollSystem.java_**

---

## 📖 Usage Guide

### **1. Login Panel**
   - Enter username and password
   - System tracks login attempts
   - Locked after 3 failed attempts

### **2. Main Dashboard**
   Four main options:
   - **Employee Management** → Add and view employees
   - **Payroll Processing** → Calculate employee salaries
   - **Payroll Configuration** → Set tax rates and SSS contributions
   - **Logout** → Return to login screen

### **3. Employee Management**
   - Enter employee details (ID, Name, Designation, Base Salary)
   - Select employee type (FullTime or PartTime)
   - Click "Add Employee" to store
   - Click "View All" to display all employees

### **4. Payroll Processing**
   - Select an employee from dropdown
   - Enter work hours, deductions, and bonuses
   - Click "Calculate" to generate payroll slip
   - Displays detailed salary breakdown

### **5. Payroll Configuration**
   - Set tax rate (in percentage)
   - Set SSS contribution amount
   - Click "Save Configuration"

---

## 💰 Salary Calculation Logic

### **Full-Time Employee**
```
Net Salary = (Base Salary + Hourly Pay) - Deductions + Bonus
Where: Hourly Pay = 500 PHP/hour × Work Hours
```

### **Part-Time Employee**
```
Net Salary = (Hourly Pay) - Deductions + Bonus
Where: Hourly Pay = 250 PHP/hour × Work Hours
```

---

## 🛡️ Exception Handling Examples

### **InvalidEmployeeException**
Thrown when:
- Negative base salary is provided
- Invalid employee data

### **PayrollCalculationException**
Thrown when:
- Negative values for work hours, deductions, or bonus
- Invalid configuration values

---

## 🎓 Learning Outcomes

After studying this code, you will understand:
- How to design classes with proper encapsulation
- How to use inheritance and polymorphism effectively
- How to implement abstract classes and methods
- How to create custom exceptions
- How to build a GUI application with Java Swing
- How to separate concerns (Main class, Model classes, GUI class)

---

## 💡 Sample Data

You can test with these sample employees:

| ID | Name | Designation | Base Salary | Type |
|---|---|---|---|---|
| E001 | John Doe | Software Engineer | 50000 | FullTime |
| E002 | Jane Smith | Project Manager | 60000 | FullTime |
| E003 | Bob Johnson | Intern | 0 | PartTime |

---

## ✨ Key Classes Overview

### **Employee (Abstract)**
- Base class for all employee types
- Defines common properties and abstract methods
- Ensures all employees have a `calculateSalary()` implementation

### **FullTimeEmployee & PartTimeEmployee**
- Concrete implementations of `Employee`
- Override `calculateSalary()` with type-specific logic
- Different hourly rates and salary structures

### **PayrollConfig**
- Manages payroll system settings
- Encapsulates configuration data
- Validates inputs before setting

### **PayrollSystem**
- Main GUI application
- Manages all UI panels (login, dashboard, employee, payroll, config)
- Handles user interactions and navigation

---

## 🔧 Requirements

- **Java Version**: Java 8 or higher
- **IDE**: Any Java IDE (NetBeans, Eclipse, IntelliJ) or command line
- **Libraries**: Java Swing (included in JDK)

---

## 📝 Notes

- All data is stored in memory (HashMap)
- Data is lost when the application closes
- For persistent storage, integrate a database
- The system uses PHP currency as example (easily modifiable)

---

## 👨‍💻 Author

Created as an educational project to demonstrate OOP principles in Java.

---

## 📄 License

This project is free to use for educational purposes.

---
