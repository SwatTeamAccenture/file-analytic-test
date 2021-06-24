package br.com.fileanalytictest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale {

	private String id;
	private List<SaleItem> saleItens;
	private Seller seller;

	public Sale() {}

	public Sale(String id, List<SaleItem> saleItens, Seller seller) {
		this.id = id;
		this.saleItens = saleItens;
		this.seller = seller;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SaleItem> getSaleItens() {
		if ( Objects.isNull(this.saleItens) ) {
			this.saleItens = new ArrayList<SaleItem>();
		}
		return saleItens;
	}

	public void setSaleItens(List<SaleItem> saleItens) {
		this.saleItens = saleItens;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public BigDecimal getFinalValue() {
		if ( this.getSaleItens().isEmpty() ) {
			return BigDecimal.ZERO;
		}
		return this.saleItens.stream().map(si -> si.getItem().getProductValue().multiply(new BigDecimal(si.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((saleItens == null) ? 0 : saleItens.hashCode());
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (saleItens == null) {
			if (other.saleItens != null)
				return false;
		} else if (!saleItens.equals(other.saleItens))
			return false;
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", saleItens=" + saleItens + ", seller=" + seller + "]";
	}

}
