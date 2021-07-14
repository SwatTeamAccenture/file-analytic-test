package analisedados.model;

public class Relatorio {
    private Integer quantidadeCliente;
    private Integer quantidadedeVendeores;
    private Integer idVenda;
    private String  piorVendedor;

    public Relatorio() {
    }

    public Integer getQuantidadeCliente() {
        return quantidadeCliente;
    }

    public Integer getQuantidadedeVendeores() {
        return quantidadedeVendeores;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public String getPiorVendedor() {
        return piorVendedor;
    }

    public void setQuantidadeCliente(Integer quantidadeCliente) {
        this.quantidadeCliente = quantidadeCliente;
    }

    public void setQuantidadedeVendeores(Integer quantidadedeVendeores) {
        this.quantidadedeVendeores = quantidadedeVendeores;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public void setPiorVendedor(String piorVendedor) {
        this.piorVendedor = piorVendedor;
    }
}
