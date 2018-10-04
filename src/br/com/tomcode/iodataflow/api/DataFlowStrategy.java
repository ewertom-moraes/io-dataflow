package br.com.tomcode.iodataflow.api;

import java.lang.reflect.Field;

public interface DataFlowStrategy {

	/**
	 * Formata o valor do campo/field de maneira apropriada.
	 * @return valor do campo/field em StringBuilder
	 * 
	 * */
	public StringBuilder format(Field field, Object instance);
	
	/**
	 * Define a saida ao separar linhas entre registros.
	 * Caso queira separadores de linhas entre os registros, retornar isto!
	 * Caso queira algo mais, retornar junto.
	 * */
	public String appendLineSeparator(StringBuilder str);
	
	
	public StringBuilder finalize(StringBuilder file);
	
}
