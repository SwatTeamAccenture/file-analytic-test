package br.com.fileanalytictest.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fileanalytictest.comparators.SellerSaleValueComparator;
import br.com.fileanalytictest.model.Client;
import br.com.fileanalytictest.model.IdentifierEnum;
import br.com.fileanalytictest.model.Item;
import br.com.fileanalytictest.model.Sale;
import br.com.fileanalytictest.model.SaleItem;
import br.com.fileanalytictest.model.Seller;

public class FileProcessService {

	public static final String HOME = System.getProperty("user.home") + "/data";

	public static String processFiles(File file) {
		try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
			Pattern pattern;
			Matcher matcher;
			String regexSale = "(\\d*-\\d*-\\d*\\.?\\d*)";

			Sale bestSale = new Sale();

			List<SaleItem> saleItens;

			Set<Client> clients = new HashSet<Client>();
			Set<Seller> sellers = new HashSet<Seller>();
			Map<Seller, BigDecimal> sellerTotalSale = new HashMap<Seller, BigDecimal>();

			Iterator<String> iterator = bf.lines().iterator();
			while (iterator.hasNext()) {
				String line = iterator.next();
				List<String> columns = findValuesOnLine(line);

				IdentifierEnum identifier = IdentifierEnum.getByValue(columns.get(0));
				switch (identifier) {
				case SELLER:
					sellers.add(new Seller(columns.get(1), columns.get(2), new BigDecimal(columns.get(3))));
					break;
				case CLIENT:
					clients.add(new Client(columns.get(1), columns.get(2), columns.get(3)));
					break;
				case SALE:
					Optional<Seller> seller = sellers.stream().filter(s -> s.getName().equals(columns.get(3))).findFirst();
					if (seller.isPresent()) {
						saleItens = new ArrayList<SaleItem>();
						pattern = Pattern.compile(regexSale);
						matcher = pattern.matcher(columns.get(2));
						while (matcher.find()) {
							String[] saleItem = matcher.group().split("-");
							saleItens.add(new SaleItem(Integer.parseInt(saleItem[1]), new Item(Long.parseLong(saleItem[0]), new BigDecimal(saleItem[2]))));
						}
						Sale sale = new Sale(columns.get(1), saleItens, seller.get());
						sellerTotalSale.compute(seller.get(), (k, v) -> (v == null) ? sale.getFinalValue() : v.add(sale.getFinalValue()));
						bestSale = bestSale.getFinalValue().compareTo(sale.getFinalValue()) >= 0 ? bestSale : sale;
					}

					break;
				default:
					break;
				}
			}
			SellerSaleValueComparator sellerSaleComparator = new SellerSaleValueComparator(sellerTotalSale);
			TreeMap<Seller, BigDecimal> treeMap = new TreeMap<Seller, BigDecimal>(sellerSaleComparator);
			treeMap.putAll(sellerTotalSale);

			return String.format("%dç%dç%sç%s", clients.size(), sellers.size(), bestSale.getId(), treeMap.firstEntry().getKey().getName());
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro ao ler arquivo";
		}
	}
	
	public static List<String> findValuesOnLine(String line) {
		List<String> values = new ArrayList<String>();
		String value = "";
		String[] array = line.split("");
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals("ç") && (array[i + 1].matches("\\d") || array[i + 1].matches("[A-Z]") || array[i + 1].matches("\\["))) {
				values.add(value);
				value = "";
			} else {
				value += array[i];
			}
		}
		values.add(value);
		return values;
	}

	public static void moveFile(File file) {
		file.renameTo(new File(HOME + "/out/" + file.getName().split("\\.")[0] + "_entrada.txt"));
	}

	public static void writeOutFile(String result, String fileName) {
		try (FileWriter myWriter = new FileWriter(new File(HOME + "/out/" + fileName.split("\\.")[0] + "_saida.txt"))) {
			myWriter.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
