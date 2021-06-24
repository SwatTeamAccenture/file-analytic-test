package br.com.fileanalytictest.comparators;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

import br.com.fileanalytictest.model.Seller;

public class SellerSaleValueComparator implements Comparator<Seller> {
	private Map<Seller, BigDecimal> base;

	public SellerSaleValueComparator(Map<Seller, BigDecimal> base) {
		this.base = base;
	}

	@Override
	public int compare(Seller a, Seller b) {
		int compare = base.get(a).compareTo(base.get(b));
		if (compare == 0) {
			return 1;
		} else {
			return compare;
		}
	}

}
