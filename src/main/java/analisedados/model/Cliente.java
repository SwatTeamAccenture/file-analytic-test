package analisedados.model;

public class Cliente {
    private String cnpj;
    private String nome;
    private String businessArea;

    public Cliente() {
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }
}
