// Abstract Base Class demonstrating Abstraction & Encapsulation
public abstract class Employee {
    private String id;
    private String name;
    private String designation;
    private double baseSalary;

    public Employee(String id, String name, String designation, double baseSalary) 
            throws InvalidEmployeeException {
        if (baseSalary < 0) {
            throw new InvalidEmployeeException("Salary cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.baseSalary = baseSalary;
    }

    // Getters - Data Hiding (Encapsulation)
    public String getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getDesignation() { 
        return designation; 
    }
    
    public double getBaseSalary() { 
        return baseSalary; 
    }

    // Abstract method - Polymorphism
    public abstract double calculateSalary(double workHours, double deduction, double bonus) 
            throws PayrollCalculationException;

    @Override
    public String toString() {
        return id + " | " + name + " | " + designation + " | PHP " + baseSalary;
    }
}