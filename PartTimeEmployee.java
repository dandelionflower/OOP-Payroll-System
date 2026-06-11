// Inheritance: Part-Time Employee Class
public class PartTimeEmployee extends Employee {
    private static final double HOURLY_RATE = 250; // PHP per hour

    public PartTimeEmployee(String id, String name, String designation, double baseSalary) 
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
        double grossSalary = hourlyPay;
        double netSalary = grossSalary - deduction + bonus;

        return Math.max(0, netSalary);
    }
}