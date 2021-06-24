package br.com.fileanalytictest.model;

import java.util.stream.Stream;

public enum IdentifierEnum {
	SELLER("001"), 
	CLIENT("002"), 
	SALE("003");

	private String value;

	private IdentifierEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static IdentifierEnum getByValue(String value) {
		return Stream.of(IdentifierEnum.values()).filter(i -> i.value.equals(value)).findFirst().orElse(null);
	}
}
