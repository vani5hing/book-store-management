package capybara.bookstoremanagement;

public class FinancialReport {
    private double totalRevenue;
    private double totalCost;
    private double grossProfit;
    private double netProfit;
    private double profitMargin;

    public FinancialReport(double totalRevenue, double totalCost, double operationalCost) {
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.grossProfit = totalRevenue - totalCost;
        this.netProfit = grossProfit - operationalCost;
        this.profitMargin = (netProfit / totalRevenue) * 100;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }
}