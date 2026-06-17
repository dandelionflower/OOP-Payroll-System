import java.util.*;

public class PayrollSystemCLI {
    private Map<String, Employee> employees;
    private PayrollConfig payrollConfig;
    private Scanner scanner;

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public PayrollSystemCLI() {
        employees = new HashMap<>();
        payrollConfig = new PayrollConfig();
        scanner = new Scanner(System.in);
    }

    // ===== ENTRY POINT =====
    public void start() {
        printBanner();
        if (login()) {
            showDashboard();
        } else {
            System.out.println("\n[!] System locked due to too many failed attempts. Exiting...");
        }
        scanner.close();
    }

    //LOGIN BANNER
    private void printBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║         PAYROLL MANAGEMENT SYSTEM            ║");
        System.out.println("║         Secure Employee Management           ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    //LOGIN
    private boolean login() {
        int attempts = 0;
        while (attempts < MAX_LOGIN_ATTEMPTS) {
            System.out.println("\n--- LOGIN ---");
            System.out.print("Username: ");
            String user = scanner.nextLine().trim();
            System.out.print("Password: ");
            String pass = scanner.nextLine().trim();

            if (user.equals("admin") && pass.equals("admin123")) {
                System.out.println("\n[✓] Login successful! Welcome, Admin.");
                return true;
            } else {
                attempts++;
                System.out.println("[✗] Invalid credentials! Attempt " + attempts + "/" + MAX_LOGIN_ATTEMPTS);
            }
        }
        return false;
    }

    //DASHBOARD
    private void showDashboard() {
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║            DASHBOARD             ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Employee Management         ║");
            System.out.println("║  [2] Payroll Processing          ║");
            System.out.println("║  [3] Configuration               ║");
            System.out.println("║  [4] Logout                      ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": showEmployeeManagement(); break;
                case "2": showPayrollProcessing();  break;
                case "3": showConfiguration();      break;
                case "4":
                    System.out.println("\n[✓] Logged out successfully.");
                    running = false;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please enter 1-4.");
            }
        }
    }

    //EMPLOYEE MANAGEMENT
    private void showEmployeeManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║       EMPLOYEE MANAGEMENT        ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  [1] Add Employee                ║");
            System.out.println("║  [2] View All Employees          ║");
            System.out.println("║  [3] Back                        ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addEmployee();       break;
                case "2": viewAllEmployees();  break;
                case "3": back = true;         break;
                default:
                    System.out.println("[!] Invalid choice. Please enter 1-3.");
            }
        }
    }

    private void addEmployee() {
        System.out.println("\n--- ADD EMPLOYEE ---");
        try {
            System.out.print("Employee ID    : ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) { System.out.println("[!] ID cannot be empty."); return; }

            System.out.print("Name           : ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) { System.out.println("[!] Name cannot be empty."); return; }

            System.out.print("Designation    : ");
            String designation = scanner.nextLine().trim();
            if (designation.isEmpty()) { System.out.println("[!] Designation cannot be empty."); return; }

            System.out.print("Base Salary    : PHP ");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Type [1=FullTime / 2=PartTime]: ");
            String typeChoice = scanner.nextLine().trim();

            Employee emp;
            if ("1".equals(typeChoice)) {
                emp = new FullTimeEmployee(id, name, designation, salary);
            } else if ("2".equals(typeChoice)) {
                emp = new PartTimeEmployee(id, name, designation, salary);
            } else {
                System.out.println("[!] Invalid type selected.");
                return;
            }

            employees.put(id, emp);
            System.out.println("\n[✓] Employee added successfully!");

        } catch (InvalidEmployeeException e) {
            System.out.println("[✗] Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[✗] Invalid salary format.");
        }
    }

    private void viewAllEmployees() {
        System.out.println("\n--- EMPLOYEE LIST ---");
        if (employees.isEmpty()) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       No employees found.            ║");
            System.out.println("╚══════════════════════════════════════╝");
        } else {
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                    EMPLOYEE LIST                         ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            int index = 1;
            for (Employee emp : employees.values()) {
                System.out.println(index + ". " + emp.toString());
                index++;
            }
        }
    }

    //PAYROLL PROCESSING
    private void showPayrollProcessing() {
        System.out.println("\n--- PAYROLL PROCESSING ---");
        if (employees.isEmpty()) {
            System.out.println("[!] No employees found. Please add an employee first.");
            return;
        }

        System.out.println("Available Employees:");
        int i = 1;
        List<String> ids = new ArrayList<>(employees.keySet());
        for (String id : ids) {
            Employee emp = employees.get(id);
            System.out.println("  [" + i + "] " + id + " - " + emp.getName());
            i++;
        }

        System.out.print("Select Employee (number): ");
        try {
            int selection = Integer.parseInt(scanner.nextLine().trim());
            if (selection < 1 || selection > ids.size()) {
                System.out.println("[!] Invalid selection.");
                return;
            }

            Employee emp = employees.get(ids.get(selection - 1));

            System.out.print("Work Hours  : ");
            double hours = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Deductions  : PHP ");
            double deduction = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Bonus       : PHP ");
            double bonus = Double.parseDouble(scanner.nextLine().trim());

            double salary = emp.calculateSalary(hours, deduction, bonus);

            System.out.println("\n╔═══════════════════════════════════════════╗");
            System.out.println("║              ✓ PAYROLL SLIP               ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.printf("  Employee Name    : %s%n",   emp.getName());
            System.out.printf("  Employee ID      : %s%n",   emp.getId());
            System.out.printf("  Designation      : %s%n",   emp.getDesignation());
            System.out.printf("  Base Salary      : PHP %,.2f%n", emp.getBaseSalary());
            System.out.printf("  Work Hours       : %.2f hrs%n", hours);
            System.out.printf("  Deductions       : PHP %,.2f%n", deduction);
            System.out.printf("  Bonus            : PHP %,.2f%n", bonus);
            System.out.println("  ═══════════════════════════════════════════");
            System.out.printf("  NET SALARY       : PHP %,.2f%n", salary);
            System.out.println("  ═══════════════════════════════════════════");

        } catch (PayrollCalculationException e) {
            System.out.println("[✗] Calculation Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[✗] Invalid input format.");
        }
    }

    //CONFIG
    private void showConfiguration() {
        System.out.println("\n--- PAYROLL CONFIGURATION ---");
        System.out.printf("Current Tax Rate      : %.0f%%%n", payrollConfig.getTaxRate() * 100);
        System.out.printf("Current SSS           : PHP %.2f%n", payrollConfig.getSssContribution());
        System.out.println();

        System.out.print("New Tax Rate (%)      : ");
        try {
            double tax = Double.parseDouble(scanner.nextLine().trim()) / 100;

            System.out.print("New SSS Contribution  : PHP ");
            double sss = Double.parseDouble(scanner.nextLine().trim());

            payrollConfig.setConfig(tax, sss);
            System.out.println("[✓] Configuration saved successfully!");

        } catch (PayrollCalculationException e) {
            System.out.println("[✗] Configuration Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("[✗] Invalid input format.");
        }
    }
}
