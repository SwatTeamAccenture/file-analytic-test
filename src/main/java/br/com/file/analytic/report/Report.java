package br.com.file.analytic.report;

public class Report {
    private int clientCount;
    private int salesmanCount;
    private long mostExpensiveSaleId;
    private String worstSalesmanName;
    private String worstSalesmanCPF;

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public int getSalesmanCount() {
        return salesmanCount;
    }

    public void setSalesmanCount(int salesmanCount) {
        this.salesmanCount = salesmanCount;
    }

    public long getMostExpensiveSaleId() {
        return mostExpensiveSaleId;
    }

    public void setMostExpensiveSaleId(long mostExpensiveSaleId) {
        this.mostExpensiveSaleId = mostExpensiveSaleId;
    }

    public String getWorstSalesmanName() {
        return worstSalesmanName;
    }

    public void setWorstSalesmanName(String worstSalesmanName) {
        this.worstSalesmanName = worstSalesmanName;
    }

    public String getWorstSalesmanCPF() {
        return worstSalesmanCPF;
    }

    public void setWorstSalesmanCPF(String worstSalesmanCPF) {
        this.worstSalesmanCPF = worstSalesmanCPF;
    }
}
