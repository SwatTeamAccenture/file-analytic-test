package analisedados.model;

import java.util.List;

public class Venda {
    private Integer Id;
    private List<Item> item;
    private String nome;
    private Double vendaTotal;

    public Venda() {
    }

    public Integer getId() {
        return Id;
    }

    public List<Item> getItem() {
        return item;
    }

    public String getNome() {
        return nome;
    }

    public Double getVendaTotal() {
        return vendaTotal;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setVendaTotal(Double vendaTotal) {
        this.vendaTotal = vendaTotal;
    }
}
