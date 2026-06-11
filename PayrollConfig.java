// Payroll Configuration Class - Encapsulation
public class PayrollConfig {
    private double taxRate = 0.12; // 12% default
    private double sssContribution = 1125; // Default
    private boolean isConfigured = false;

    public void setConfig(double taxRate, double sssContribution) 
            throws PayrollCalculationException {
        if (taxRate < 0 || sssContribution < 0) {
            throw new PayrollCalculationException("Invalid configuration values");
        }
        this.taxRate = taxRate;
        this.sssContribution = sssContribution;
        this.isConfigured = true;
    }

    public double getTaxRate() { 
        return taxRate; 
    }
    
    public double getSssContribution() { 
        return sssContribution; 
    }
    
    public boolean isConfigured() { 
        return isConfigured; 
    }
}