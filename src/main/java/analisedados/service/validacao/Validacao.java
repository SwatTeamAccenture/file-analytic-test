package analisedados.service.validacao;

import analisedados.model.Cliente;
import analisedados.model.Item;
import analisedados.model.Venda;
import analisedados.model.Vendedor;

public class Validacao {
    public Validacao() {
    }
    public static boolean validaCamposCliente(Cliente cliente) {
        if (cliente.getCnpj() == null || cliente.getCnpj().isBlank()) {
            System.out.println("O campo cnpj está incorreto");
            return false;
        }
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            System.out.println("O campo nome está incorreto");
            return false;
        }
        if (cliente.getBusinessArea() == null || cliente.getBusinessArea().isBlank()) {
            System.out.println("O campo business area está incorreto");
            return false;
        }
        return true;
    }
    public static boolean validaCamposItem(Item item) {
        if (item.getId() == null) {
            System.out.println("O campo id item está incorreto");
            return false;
        }
        if (item.getQuantidade() == null) {
            System.out.println("O campo Quantidade item está incorreto");
            return false;
        }
        if (item.getPreco() == null) {
            System.out.println("O campo Preço item está incorreto");
            return false;
        }
        return true;
    }
    public static boolean validaCamposVenda(Venda venda) {
        if (venda.getId() == null) {
            System.out.println("O campo Id venda está incorreto");
            return false;
        }
        if (venda.getNome() == null || venda.getNome().isBlank()) {
            System.out.println("O campo Nome venda está incorreto");
            return false;
        }

        return true;
    }
    public static boolean validaCamposVendedor(Vendedor vendedor) {
        if (vendedor.getCpf() == null || vendedor.getCpf().isBlank()) {
            System.out.println("O campo Cpf vendedor está incorreto");
            return false;
        }
        if (vendedor.getNome() == null || vendedor.getNome().isBlank()) {
            System.out.println("O campo Nome vendedor está incorreto");
            return false;
        }
        if (vendedor.getSalario() == null) {
            System.out.println("O campo Salario vendedor está incorreto");
            return false;
        }
        return true;
    }


}
