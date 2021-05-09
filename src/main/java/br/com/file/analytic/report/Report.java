package br.com.file.analytic.report;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A sales repport serializabçe to JSON
 */
public class Report {
    private int clientCount;
    private int salesmanCount;
    private long mostExpensiveSaleId;
    private BigDecimal mostExpensiveSaleValue = BigDecimal.ZERO;
    private String worstSalesmanName;
    private String worstSalesmanCPF;

    @JsonIgnore
    private Map<String, Salesman> salesmen = new HashMap<>();

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

    public void addSalesman(String cpf, String name) throws Exception {
        Salesman salesman = salesmen.get(name);

        if(salesman != null && !salesman.getCpf().equals(cpf)) {
            throw new Exception("Dois vendedores distintos com o mesmo nome. Impossível atribuir vendas.");
        }

        if(salesman == null) {
            salesman = new Salesman();
            salesman.setCpf(cpf);
            salesman.setName(name);
            salesman.setTotalSales(BigDecimal.ZERO);
            salesmen.put(name, salesman);
        }

        salesmanCount++;
    }

    public void addClient() {
        clientCount++;
    }

    public void addSale(int id, BigDecimal value, String salesmanName) throws Exception {
        if(value.compareTo(mostExpensiveSaleValue) > 0) {
            mostExpensiveSaleId = id;
            mostExpensiveSaleValue = value;
        }

        Salesman salesman = salesmen.get(salesmanName);
        if(salesman == null) {
            throw new Exception("Vendedor não encontrado");
        }

        salesman.setTotalSales(salesman.getTotalSales().add(value));
    }

    public void setWorstSalesmanProperties() {
        Salesman salesman = salesmen.values().stream().min(Comparator.comparing(Salesman::getTotalSales))
                .orElse(null);

        if(salesman != null) {
            worstSalesmanCPF = salesman.getCpf();
            worstSalesmanName = salesman.getName();
        }
    }
}
