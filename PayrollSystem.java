import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PayrollSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, Employee> employees;
    private PayrollConfig payrollConfig;
    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public PayrollSystem() {
        setTitle("PAYROLL MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        employees = new HashMap<>();
        payrollConfig = new PayrollConfig();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels
        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createDashboardPanel(), "DASHBOARD");
        cardPanel.add(createEmployeePanel(), "EMPLOYEE");
        cardPanel.add(createPayrollPanel(), "PAYROLL");
        cardPanel.add(createConfigPanel(), "CONFIG");

        add(cardPanel);
        cardLayout.show(cardPanel, "LOGIN");
        setVisible(true);
    }

    // ===== LOGIN PANEL =====
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("PAYROLL SYSTEM LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginBtn = new JButton("LOGIN");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        JLabel attemptLabel = new JLabel("Attempts: " + loginAttempts + "/" + MAX_LOGIN_ATTEMPTS);
        gbc.gridy = 4;
        panel.add(attemptLabel, gbc);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("admin") && pass.equals("admin123")) {
                loginAttempts = 0;
                cardLayout.show(cardPanel, "DASHBOARD");
            } else {
                loginAttempts++;
                attemptLabel.setText("Attempts: " + loginAttempts + "/" + MAX_LOGIN_ATTEMPTS);
                JOptionPane.showMessageDialog(panel, "Invalid credentials!");

                if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    JOptionPane.showMessageDialog(panel, "System locked. Restart to try again.");
                    System.exit(0);
                }
            }
        });

        return panel;
    }

    // ===== DASHBOARD PANEL =====
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("MAIN DASHBOARD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JButton empBtn = new JButton("Employee Management");
        empBtn.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(empBtn, gbc);

        JButton payBtn = new JButton("Payroll Processing");
        payBtn.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        panel.add(payBtn, gbc);

        JButton configBtn = new JButton("Payroll Configuration");
        configBtn.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(configBtn, gbc);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(200, 50));
        gbc.gridx = 1;
        panel.add(logoutBtn, gbc);

        empBtn.addActionListener(e -> cardLayout.show(cardPanel, "EMPLOYEE"));
        payBtn.addActionListener(e -> cardLayout.show(cardPanel, "PAYROLL"));
        configBtn.addActionListener(e -> cardLayout.show(cardPanel, "CONFIG"));
        logoutBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN");
            loginAttempts = 0;
        });

        return panel;
    }

    // ===== EMPLOYEE MANAGEMENT PANEL =====
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 240, 230));

        JLabel titleLabel = new JLabel("EMPLOYEE MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField designationField = new JTextField();
        JTextField salaryField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"FullTime", "PartTime"});

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Designation:"));
        inputPanel.add(designationField);
        inputPanel.add(new JLabel("Base Salary:"));
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Employee");
        JButton viewBtn = new JButton("View All");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(backBtn);

        panel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                String designation = designationField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                String type = (String) typeBox.getSelectedItem();

                Employee emp;
                if ("FullTime".equals(type)) {
                    emp = new FullTimeEmployee(id, name, designation, salary);
                } else {
                    emp = new PartTimeEmployee(id, name, designation, salary);
                }

                employees.put(id, emp);
                JOptionPane.showMessageDialog(panel, "Employee added successfully!");
                idField.setText("");
                nameField.setText("");
                designationField.setText("");
                salaryField.setText("");
            } catch (InvalidEmployeeException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid salary format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewBtn.addActionListener(e -> {
            displayArea.setText("");
            if (employees.isEmpty()) {
                displayArea.setText("No employees found.");
            } else {
                for (Employee emp : employees.values()) {
                    displayArea.append(emp.toString() + "\n");
                }
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }

    // ===== PAYROLL PROCESSING PANEL =====
    private JPanel createPayrollPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 250, 240));

        JLabel titleLabel = new JLabel("PAYROLL PROCESSING");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> empBox = new JComboBox<>();
        JTextField hoursField = new JTextField();
        JTextField deductionField = new JTextField();
        JTextField bonusField = new JTextField();

        inputPanel.add(new JLabel("Select Employee:"));
        inputPanel.add(empBox);
        inputPanel.add(new JLabel("Work Hours:"));
        inputPanel.add(hoursField);
        inputPanel.add(new JLabel("Deductions:"));
        inputPanel.add(deductionField);
        inputPanel.add(new JLabel("Bonus:"));
        inputPanel.add(bonusField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton calculateBtn = new JButton("Calculate");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(calculateBtn);
        buttonPanel.add(backBtn);

        panel.add(inputPanel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Update employee list
        empBox.removeAllItems();
        for (String id : employees.keySet()) {
            empBox.addItem(id);
        }

        calculateBtn.addActionListener(e -> {
            try {
                String empId = (String) empBox.getSelectedItem();
                if (empId == null || empId.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Select an employee first!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Employee emp = employees.get(empId);
                double hours = Double.parseDouble(hoursField.getText());
                double deduction = Double.parseDouble(deductionField.getText());
                double bonus = Double.parseDouble(bonusField.getText());

                double salary = emp.calculateSalary(hours, deduction, bonus);

                displayArea.setText("=== PAYROLL SLIP ===\n");
                displayArea.append("Employee: " + emp.getName() + "\n");
                displayArea.append("ID: " + emp.getId() + "\n");
                displayArea.append("Designation: " + emp.getDesignation() + "\n");
                displayArea.append("Work Hours: " + hours + "\n");
                displayArea.append("Deductions: PHP " + deduction + "\n");
                displayArea.append("Bonus: PHP " + bonus + "\n");
                displayArea.append("-------------------\n");
                displayArea.append("NET SALARY: PHP " + String.format("%.2f", salary) + "\n");
            } catch (PayrollCalculationException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }

    // ===== CONFIGURATION PANEL =====
    private JPanel createConfigPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 240, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("PAYROLL CONFIGURATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel taxLabel = new JLabel("Tax Rate (%):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(taxLabel, gbc);

        JTextField taxField = new JTextField(String.valueOf(payrollConfig.getTaxRate() * 100), 15);
        gbc.gridx = 1;
        panel.add(taxField, gbc);

        JLabel sssLabel = new JLabel("SSS Contribution:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(sssLabel, gbc);

        JTextField sssField = new JTextField(String.valueOf(payrollConfig.getSssContribution()), 15);
        gbc.gridx = 1;
        panel.add(sssField, gbc);

        JButton saveBtn = new JButton("Save Configuration");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(saveBtn, gbc);

        JButton backBtn = new JButton("Back");
        gbc.gridy = 4;
        panel.add(backBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                double tax = Double.parseDouble(taxField.getText()) / 100;
                double sss = Double.parseDouble(sssField.getText());
                payrollConfig.setConfig(tax, sss);
                JOptionPane.showMessageDialog(panel, "Configuration saved successfully!");
            } catch (PayrollCalculationException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(), "Configuration Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }
}