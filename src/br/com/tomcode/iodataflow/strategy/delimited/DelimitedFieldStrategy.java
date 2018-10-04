package br.com.tomcode.iodataflow.strategy.delimited;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.Util;
import br.com.tomcode.iodataflow.api.DataFlowStrategy;

public class DelimitedFieldStrategy implements DataFlowStrategy {
	
	private Class<?> layoutConfig;
	private String delimiter;
	
	public DelimitedFieldStrategy(Class<?> layoutConfig) {
		this.layoutConfig = layoutConfig;
		this.delimiter =  ((IODataFlowDelimitedLayoutConfig) layoutConfig.getAnnotation(IODataFlowDelimitedLayoutConfig.class)).delimiter();
	}
	
	@Override
	public StringBuilder format(Field field, Object instance){
			
		IODataFlowDelimitedLayoutConfig layoutConfigAnnotation = this.layoutConfig.getAnnotation(IODataFlowDelimitedLayoutConfig.class);
		DecimalSeparator decimalSeparator = layoutConfigAnnotation.defaultDecimalSeparator();
		DateFormat dateFormat = layoutConfigAnnotation.defaultDateFormat();
		
		StringBuilder formatByType = Util.formatByType(field, instance, dateFormat, decimalSeparator);
		formatByType.append(delimiter);
		
		return formatByType;
	}

	@Override
	public String appendLineSeparator(StringBuilder str) {
		if(str.toString().isEmpty())
			return "";
		
		if(str.toString().endsWith(delimiter) && 
				layoutConfig.getAnnotation(IODataFlowDelimitedLayoutConfig.class).lineEnd().isEmpty())
			str.deleteCharAt(str.length()-1); //remove last delimiter 
		
		if(!str.toString().endsWith(System.lineSeparator())){
			return System.lineSeparator();
		}
		return "";
	}

	@Override
	public StringBuilder finalize(StringBuilder file) {
		IODataFlowDelimitedLayoutConfig layoutConfigAnnotation = this.layoutConfig.getAnnotation(IODataFlowDelimitedLayoutConfig.class);
		String delimiter = layoutConfigAnnotation.delimiter();
		Class<?> withHeadersOf = layoutConfigAnnotation.withHeadersOf();
		String hearders = "";
		String withHeaders = layoutConfigAnnotation.withHeaders();
		if(!withHeaders.isEmpty()){
			hearders = withHeaders;
		}else if (!withHeadersOf.equals(IODataFlowDelimitedLayoutConfig.class)){
			hearders = Arrays.asList(withHeadersOf.getFields()).stream()
				.map(f -> f.getName())
				.collect(Collectors.joining(delimiter));
		}
		if(!hearders.trim().isEmpty()){
			file = new StringBuilder(hearders + System.lineSeparator() + file);
		}
		return file;
	}
	
}
