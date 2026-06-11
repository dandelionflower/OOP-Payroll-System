// Inheritance: Full-Time Employee Class
public class FullTimeEmployee extends Employee {
    private static final double HOURLY_RATE = 500; // PHP per hour
    private static final double MAX_HOURS = 160; // Monthly hours

    public FullTimeEmployee(String id, String name, String designation, double baseSalary) 
            throws InvalidEmployeeException {
        super(id, name, designation, baseSalary);
    }

    @Override
    public double calculateSalary(double workHours, double deduction, double bonus) 
            throws PayrollCalculationException {
        if (workHours < 0 || deduction < 0 || bonus < 0) {
            throw new PayrollCalculationException("Invalid input values");
        }
        
        double hourlyPay = (HOURLY_RATE * workHours);
        double basePay = getBaseSalary();
        double grossSalary = basePay + hourlyPay;
        double netSalary = grossSalary - deduction + bonus;

        return Math.max(0, netSalary);
    }
}