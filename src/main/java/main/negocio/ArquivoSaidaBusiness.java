package main.negocio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.entidade.Cliente;
import main.entidade.CsvMapper;
import main.entidade.Item;
import main.entidade.Venda;
import main.entidade.Vendedor;

public class ArquivoSaidaBusiness {

	private static String PATH_OUT = System.getProperty("user.home") + "\\data\\out\\";
	private static String CONTEUDO_SAIDA_TEMPLATE =  "Quantidade de clientes no arquivo de entrada: %s\r\n" + 
										"Quantidade de vendedores no arquivo de entrada: %s\r\n" + 
										"ID da venda mais cara: %s\r\n" + 
										"O pior vendedor: %s";
	
	private static List<String> arquivoSaidaList = new ArrayList<String>();
	
	//variável que guarda as linhas do arquivo de entrada lidas pelo spring batch
	private List<CsvMapper> csvMapperList;
	
	private List<Vendedor> listaVendedor = new ArrayList<Vendedor>();
	private List<Cliente> listaCliente = new ArrayList<Cliente>();
	private List<Venda> listaVenda = new ArrayList<Venda>();
	
	//variáveis para preencher arquivo de saída
	private Integer quantidadeClientes = 0;
	private Integer quantidadeVendedores = 0;
	private Double idVendaMaisCara = 0.0;
	private String piorVendedor = "";
	
	public ArquivoSaidaBusiness(List<CsvMapper> csvMapperList) {
		super();
		this.csvMapperList = csvMapperList;
	}

	//método principal para chamar os métodos para gerar o arquivo de saída após as regras de negócio
	public void gerarSaida() throws IOException {
		
		extrairInformacaoCsvMapper();
		
		gerarInformacoesSaida();
		
		verificarPastaSaida();
		
		salvarTextoSaida();
	}
	
	//preenche as listas com as entidades de acordo com o identificador lido na linha
	private void extrairInformacaoCsvMapper() {
		for (CsvMapper csvMapper : csvMapperList) {
			switch(csvMapper.getIdentificador()) {
			case "001":
				listaVendedor.add(new Vendedor(csvMapper.getColuna1(), csvMapper.getColuna2(), Double.valueOf(csvMapper.getColuna1())));
				break;
			case "002":
				listaCliente.add(new Cliente(csvMapper.getColuna1(), csvMapper.getColuna2(), csvMapper.getColuna1()));
				break;
			case "003":
				List<Item> dadosItens = processarDadosItens(csvMapper.getColuna2());
				listaVenda.add(new Venda(Double.valueOf(csvMapper.getColuna1()),dadosItens,csvMapper.getColuna3()));
				break;
			}
		}
	}

	//obtém as informações de saída e assinala nas variáveis
	private void gerarInformacoesSaida() {
		this.quantidadeClientes = listaCliente.size();
		this.quantidadeVendedores = listaVendedor.size();
		
		ordenarVendas();
		
		this.idVendaMaisCara = listaVenda.get(0).getId();
		this.piorVendedor = listaVenda.get(listaVenda.size()-1).getNomeVendedor();
		
	}
	

	//salva o arquivo de saída a partir do template
	private void salvarTextoSaida() throws IOException {
		String fileName = "saida_" + (arquivoSaidaList.size() + 1);
		arquivoSaidaList.add(fileName);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_OUT + fileName));
		
		String conteudoSaida = String.format(CONTEUDO_SAIDA_TEMPLATE, this.quantidadeClientes, this.quantidadeVendedores, this.idVendaMaisCara, this.piorVendedor);
		
	    writer.write(conteudoSaida);
	    writer.close();
	}
	

	//método auxiliar para calcular e ordenar crescentemente os valores das vendas
	private void ordenarVendas() {
		Collections.sort(this.listaVenda, new Comparator<Venda>() {
			@Override
			public int compare(Venda b1, Venda b2) {
				b1.calcularValorTotalVenda();
				b2.calcularValorTotalVenda();
				int cmp = b2.valorTotalVenda.compareTo(b1.valorTotalVenda);

				return cmp;
			}        
		});
		
	}

	
	//método auxiliar para preencher as entidades de Itens.
	//Transforma 1-10-100,2-30-2.50,3-40-3.10
	//em uma lista itens
	//item id = 1;				item id = 2;				item id = 3;
	//item quantidade = 10		item quantidade = 30		item quantidade = 40
	//item preço = 100			item preço = 2.50			item preço = 3.10
	private List<Item> processarDadosItens(String coluna2) {
		String[] itens = coluna2.split(",");
		List<Item> itensVenda = new ArrayList<Item>();
		
		for (int i = 0; i < itens.length; i++) {
			Item item = new Item();
			
			Double id = Double.valueOf(itens[i].split("-")[0]);
			Integer quantidade = Integer.valueOf(itens[i].split("-")[1]);
			Double preco = Double.valueOf(itens[i].split("-")[2]);
			
			item.setId(id);
			item.setQuantidade(quantidade);
			item.setPreco(preco);
			
			itensVenda.add(item);
		}
		
		return itensVenda;
	}

	private void verificarPastaSaida() throws IOException {
		Path pathOut = Paths.get(PATH_OUT);
		if(Files.isDirectory(pathOut)) {
			
		} else {
			Files.createDirectories(pathOut);
		}
	}
	

	//getter and setter
	public List<CsvMapper> getCsvMapperList() {
		return csvMapperList;
	}
	
	public void setCsvMapperList(List<CsvMapper> csvMapperList) {
		this.csvMapperList = csvMapperList;
	}
}
