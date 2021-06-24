package br.com.fileanalytictest.model;

import java.math.BigDecimal;

public class Item {

	private Long productId;
	private BigDecimal productValue;

	public Item(Long productId, BigDecimal productValue) {
		super();
		this.productId = productId;
		this.productValue = productValue;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getProductValue() {
		return productValue;
	}

	public void setProductValue(BigDecimal productValue) {
		this.productValue = productValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productValue == null) ? 0 : productValue.hashCode());
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
		Item other = (Item) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productValue == null) {
			if (other.productValue != null)
				return false;
		} else if (!productValue.equals(other.productValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [productId=" + productId + ", productValue=" + productValue + "]";
	}

}
