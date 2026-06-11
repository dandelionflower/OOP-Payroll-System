import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class PayrollSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<String, Employee> employees;
    private PayrollConfig payrollConfig;
    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    // Professional Color Scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);      // Professional Blue
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);    // Light Blue
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);       // Green
    private static final Color DANGER_COLOR = new Color(231, 76, 60);        // Red
    private static final Color BACKGROUND = new Color(236, 240, 241);        // Light Gray
    private static final Color DARK_TEXT = new Color(44, 62, 80);            // Dark Gray
    private static final Color LIGHT_TEXT = new Color(255, 255, 255);        // White

    public PayrollSystem() {
        setTitle("PAYROLL MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        employees = new HashMap<>();
        payrollConfig = new PayrollConfig();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND);

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

    // Utility: Create styled button
    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(true);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                        Math.max(0, bgColor.getRed() - 30),
                        Math.max(0, bgColor.getGreen() - 30),
                        Math.max(0, bgColor.getBlue() - 30)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // ===== LOGIN PANEL =====
    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setLayout(new GridBagLayout());

        // Center card
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(new LineBorder(PRIMARY_COLOR, 3));
        centerPanel.setPreferredSize(new Dimension(420, 480));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("PAYROLL SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Secure Employee Management");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 15, 25, 15);
        centerPanel.add(subtitleLabel, gbc);

        // Username Label
        gbc.insets = new Insets(15, 15, 5, 15);
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setForeground(DARK_TEXT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(userLabel, gbc);

        // Username Field
        JTextField userField = new JTextField(30);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 15, 15, 15);
        centerPanel.add(userField, gbc);

        // Password Label
        gbc.insets = new Insets(15, 15, 5, 15);
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setForeground(DARK_TEXT);
        gbc.gridy = 4;
        centerPanel.add(passLabel, gbc);

        // Password Field
        JPasswordField passField = new JPasswordField(30);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 15, 20, 15);
        centerPanel.add(passField, gbc);

        // Login Button
        JButton loginBtn = createButton("LOGIN", PRIMARY_COLOR, LIGHT_TEXT);
        loginBtn.setPreferredSize(new Dimension(390, 50));
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 15, 10, 15);
        centerPanel.add(loginBtn, gbc);

        // Attempt Label
        JLabel attemptLabel = new JLabel("Attempts: " + loginAttempts + "/" + MAX_LOGIN_ATTEMPTS);
        attemptLabel.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        attemptLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 15, 15, 15);
        centerPanel.add(attemptLabel, gbc);

        // Add to main
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainPanel.add(centerPanel, mainGbc);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("admin") && pass.equals("admin123")) {
                loginAttempts = 0;
                cardLayout.show(cardPanel, "DASHBOARD");
                userField.setText("");
                passField.setText("");
            } else {
                loginAttempts++;
                attemptLabel.setText("Attempts: " + loginAttempts + "/" + MAX_LOGIN_ATTEMPTS);
                JOptionPane.showMessageDialog(mainPanel, "❌ Invalid credentials!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);

                if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    JOptionPane.showMessageDialog(mainPanel, "🔒 System locked. Please restart.",
                            "Security Alert", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        });

        return mainPanel;
    }

    // ===== DASHBOARD PANEL =====
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        JLabel headerLabel = new JLabel("DASHBOARD");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(LIGHT_TEXT);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Dashboard buttons
        String[] buttonLabels = {"Employee Management", "Payroll Processing",
                "Configuration", "Logout"};
        Color[] buttonColors = {SECONDARY_COLOR, SECONDARY_COLOR, SECONDARY_COLOR, DANGER_COLOR};
        JButton[] buttons = new JButton[4];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                buttons[index] = createDashboardButton(buttonLabels[index], buttonColors[index]);
                gbc.gridx = j;
                gbc.gridy = i;
                contentPanel.add(buttons[index], gbc);
            }
        }

        panel.add(contentPanel, BorderLayout.CENTER);

        // Button actions
        buttons[0].addActionListener(e -> cardLayout.show(cardPanel, "EMPLOYEE"));
        buttons[1].addActionListener(e -> cardLayout.show(cardPanel, "PAYROLL"));
        buttons[2].addActionListener(e -> cardLayout.show(cardPanel, "CONFIG"));
        buttons[3].addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN");
            loginAttempts = 0;
        });

        return panel;
    }

    private JButton createDashboardButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 130));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(LIGHT_TEXT);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(PRIMARY_COLOR, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // ===== EMPLOYEE MANAGEMENT PANEL =====
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        JLabel headerLabel = new JLabel("EMPLOYEE MANAGEMENT");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(LIGHT_TEXT);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input section - FIXED
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        inputPanel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        idLabel.setForeground(DARK_TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        idField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 1;
        inputPanel.add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setForeground(DARK_TEXT);
        gbc.gridy = 2;
        inputPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 3;
        inputPanel.add(nameField, gbc);

        JLabel designationLabel = new JLabel("Designation:");
        designationLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        designationLabel.setForeground(DARK_TEXT);
        gbc.gridy = 4;
        inputPanel.add(designationLabel, gbc);

        JTextField designationField = new JTextField(20);
        designationField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        designationField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 5;
        inputPanel.add(designationField, gbc);

        JLabel salaryLabel = new JLabel("Base Salary:");
        salaryLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        salaryLabel.setForeground(DARK_TEXT);
        gbc.gridy = 6;
        inputPanel.add(salaryLabel, gbc);

        JTextField salaryField = new JTextField(20);
        salaryField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        salaryField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 7;
        inputPanel.add(salaryField, gbc);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        typeLabel.setForeground(DARK_TEXT);
        gbc.gridy = 8;
        inputPanel.add(typeLabel, gbc);

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"FullTime", "PartTime"});
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        gbc.gridy = 9;
        inputPanel.add(typeBox, gbc);

        contentPanel.add(inputPanel, BorderLayout.WEST);

        // Display section
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        displayArea.setBackground(Color.WHITE);
        displayArea.setForeground(DARK_TEXT);
        displayArea.setMargin(new Insets(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND);

        JButton addBtn = createButton("Add Employee", SUCCESS_COLOR, LIGHT_TEXT);
        JButton viewBtn = createButton("View All", PRIMARY_COLOR, LIGHT_TEXT);
        JButton backBtn = createButton("← Back", DANGER_COLOR, LIGHT_TEXT);

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(backBtn);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String designation = designationField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());
                String type = (String) typeBox.getSelectedItem();

                if (id.isEmpty() || name.isEmpty() || designation.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "❌ Please fill all fields!",
                            "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Employee emp;
                if ("FullTime".equals(type)) {
                    emp = new FullTimeEmployee(id, name, designation, salary);
                } else {
                    emp = new PartTimeEmployee(id, name, designation, salary);
                }

                employees.put(id, emp);
                JOptionPane.showMessageDialog(panel, "✓ Employee added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                idField.setText("");
                nameField.setText("");
                designationField.setText("");
                salaryField.setText("");

                // Refresh payroll panel dropdown
                updatePayrollEmployeeList();
            } catch (InvalidEmployeeException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(),
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "❌ Invalid salary format!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewBtn.addActionListener(e -> {
            displayArea.setText("");
            if (employees.isEmpty()) {
                displayArea.setText("╔══════════════════════════════════════╗\n" +
                        "║      No employees found              ║\n" +
                        "╚══════════════════════════════════════╝");
            } else {
                displayArea.setText("╔══════════════════════════════════════════════════════════╗\n");
                displayArea.append("║                    EMPLOYEE LIST                         ║\n");
                displayArea.append("╚══════════════════════════════════════════════════���═══════╝\n\n");
                int index = 1;
                for (Employee emp : employees.values()) {
                    displayArea.append(index + ". " + emp.toString() + "\n");
                    index++;
                }
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }

    // Store reference for updating payroll list
    private JComboBox<String> payrollEmpBox;

    private void updatePayrollEmployeeList() {
        if (payrollEmpBox != null) {
            payrollEmpBox.removeAllItems();
            for (String id : employees.keySet()) {
                payrollEmpBox.addItem(id);
            }
        }
    }

    // ===== PAYROLL PROCESSING PANEL =====
    private JPanel createPayrollPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        JLabel headerLabel = new JLabel("PAYROLL PROCESSING");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(LIGHT_TEXT);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input section
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        inputPanel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel empLabel = new JLabel("Select Employee:");
        empLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        empLabel.setForeground(DARK_TEXT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(empLabel, gbc);

        payrollEmpBox = new JComboBox<>();
        payrollEmpBox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        gbc.gridy = 1;
        inputPanel.add(payrollEmpBox, gbc);

        JLabel hoursLabel = new JLabel("Work Hours:");
        hoursLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        hoursLabel.setForeground(DARK_TEXT);
        gbc.gridy = 2;
        inputPanel.add(hoursLabel, gbc);

        JTextField hoursField = new JTextField(20);
        hoursField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hoursField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 3;
        inputPanel.add(hoursField, gbc);

        JLabel deductionLabel = new JLabel("Deductions:");
        deductionLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        deductionLabel.setForeground(DARK_TEXT);
        gbc.gridy = 4;
        inputPanel.add(deductionLabel, gbc);

        JTextField deductionField = new JTextField(20);
        deductionField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        deductionField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 5;
        inputPanel.add(deductionField, gbc);

        JLabel bonusLabel = new JLabel("Bonus:");
        bonusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        bonusLabel.setForeground(DARK_TEXT);
        gbc.gridy = 6;
        inputPanel.add(bonusLabel, gbc);

        JTextField bonusField = new JTextField(20);
        bonusField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bonusField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        gbc.gridy = 7;
        inputPanel.add(bonusField, gbc);

        contentPanel.add(inputPanel, BorderLayout.WEST);

        // Display section
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        displayArea.setBackground(Color.WHITE);
        displayArea.setForeground(DARK_TEXT);
        displayArea.setMargin(new Insets(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BACKGROUND);

        JButton calculateBtn = createButton("Calculate", SUCCESS_COLOR, LIGHT_TEXT);
        JButton backBtn = createButton("← Back", DANGER_COLOR, LIGHT_TEXT);

        buttonPanel.add(calculateBtn);
        buttonPanel.add(backBtn);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Update employee list
        updatePayrollEmployeeList();

        calculateBtn.addActionListener(e -> {
            try {
                String empId = (String) payrollEmpBox.getSelectedItem();
                if (empId == null || empId.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "❌ Add an employee first!",
                            "No Data", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Employee emp = employees.get(empId);
                double hours = Double.parseDouble(hoursField.getText().trim());
                double deduction = Double.parseDouble(deductionField.getText().trim());
                double bonus = Double.parseDouble(bonusField.getText().trim());

                double salary = emp.calculateSalary(hours, deduction, bonus);

                displayArea.setText("╔═══════════════════════════════════════════╗\n");
                displayArea.append("║           ✓ PAYROLL SLIP                  ║\n");
                displayArea.append("╚═══════════════════════════════════════════╝\n\n");
                displayArea.append("Employee Name    : " + emp.getName() + "\n");
                displayArea.append("Employee ID      : " + emp.getId() + "\n");
                displayArea.append("Designation      : " + emp.getDesignation() + "\n");
                displayArea.append("Base Salary      : PHP " + String.format("%,.2f", emp.getBaseSalary()) + "\n");
                displayArea.append("Work Hours       : " + hours + " hrs\n");
                displayArea.append("Deductions       : PHP " + String.format("%,.2f", deduction) + "\n");
                displayArea.append("Bonus            : PHP " + String.format("%,.2f", bonus) + "\n");
                displayArea.append("═══════════════════════════════════════════\n");
                displayArea.append("💰 NET SALARY    : PHP " + String.format("%,.2f", salary) + "\n");
                displayArea.append("═══════════════════════════════════════════\n");
            } catch (PayrollCalculationException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(),
                        "Calculation Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "❌ Invalid input format!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }

    // ===== CONFIGURATION PANEL =====
    private JPanel createConfigPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 15));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        JLabel headerLabel = new JLabel("⚙️ PAYROLL CONFIGURATION");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(LIGHT_TEXT);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 40, 40, 40);

        // Config card
        JPanel configCard = new JPanel();
        configCard.setBackground(Color.WHITE);
        configCard.setLayout(new GridBagLayout());
        configCard.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        configCard.setPreferredSize(new Dimension(450, 300));

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(15, 15, 15, 15);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel taxLabel = new JLabel("Tax Rate (%):");
        taxLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        taxLabel.setForeground(DARK_TEXT);
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        configCard.add(taxLabel, cardGbc);

        JTextField taxField = new JTextField(18);
        taxField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        taxField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        taxField.setText(String.valueOf(payrollConfig.getTaxRate() * 100));
        cardGbc.gridy = 1;
        configCard.add(taxField, cardGbc);

        JLabel sssLabel = new JLabel("SSS Contribution (PHP):");
        sssLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sssLabel.setForeground(DARK_TEXT);
        cardGbc.gridy = 2;
        configCard.add(sssLabel, cardGbc);

        JTextField sssField = new JTextField(18);
        sssField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sssField.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        sssField.setText(String.valueOf(payrollConfig.getSssContribution()));
        cardGbc.gridy = 3;
        configCard.add(sssField, cardGbc);

        JButton saveBtn = createButton("    Save Changes", SUCCESS_COLOR, LIGHT_TEXT);
        saveBtn.setPreferredSize(new Dimension(420, 50));
        cardGbc.gridy = 4;
        cardGbc.insets = new Insets(30, 15, 15, 15);
        configCard.add(saveBtn, cardGbc);

        JButton backBtn = createButton("← Back", DANGER_COLOR, LIGHT_TEXT);
        backBtn.setPreferredSize(new Dimension(420, 50));
        cardGbc.gridy = 5;
        cardGbc.insets = new Insets(10, 15, 15, 15);
        configCard.add(backBtn, cardGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(configCard, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);

        saveBtn.addActionListener(e -> {
            try {
                double tax = Double.parseDouble(taxField.getText().trim()) / 100;
                double sss = Double.parseDouble(sssField.getText().trim());
                payrollConfig.setConfig(tax, sss);
                JOptionPane.showMessageDialog(panel, "✓ Configuration saved successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (PayrollCalculationException ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage(),
                        "Configuration Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "❌ Invalid input format!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "DASHBOARD"));

        return panel;
    }
}
