package br.com.tomcode.iodataflow.strategy.delimited;

import java.lang.reflect.Field;

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
	
}
