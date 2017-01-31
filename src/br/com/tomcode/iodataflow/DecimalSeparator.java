package br.com.tomcode.iodataflow;

public enum DecimalSeparator {

	COMMA(","), POINT("."), NONE("");
	
	String value;
	
	private DecimalSeparator(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
	
}
