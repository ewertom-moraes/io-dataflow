package br.com.tomcode.iodataflow.strategy.fixedfield;

import java.lang.reflect.Field;

import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.Util;
import br.com.tomcode.iodataflow.api.DataFlowStrategy;

public class FixedFieldsStrategy implements DataFlowStrategy{
	
	private Class<?> layoutConfig;
	
	public FixedFieldsStrategy(Class<?> layoutConfig) {
		this.layoutConfig = layoutConfig;
	}

	@Override
	public String appendLineSeparator(StringBuilder str) {
		if(str.toString().isEmpty())
			return "";
		if(!layoutConfig.getAnnotation(IODataFlowFixedLayoutConfig.class).lineEnd().isEmpty())
			str.append(layoutConfig.getAnnotation(IODataFlowFixedLayoutConfig.class).lineEnd().isEmpty()); 
		if(!str.toString().endsWith(System.lineSeparator()))
			return System.lineSeparator();
		return "";
	}
	
	@Override
	public StringBuilder format(Field field, Object instance){
		
		FixedField fixedField = field.getAnnotation(FixedField.class);
		if(fixedField == null)
			return new StringBuilder("");
		FixedFieldAlign fixedFieldAlign = field.getAnnotation(FixedFieldAlign.class);
		
		IODataFlowFixedLayoutConfig layoutConfigAnnotation = this.layoutConfig.getAnnotation(IODataFlowFixedLayoutConfig.class);
		
		int length = fixedField.value();
		
		char fillerNumber = fixedFieldAlign !=null ? fixedFieldAlign.filler() : layoutConfigAnnotation.defaultNumberFiller();
		char fillerText = 	fixedFieldAlign !=null  ? fixedFieldAlign.filler() : layoutConfigAnnotation.defaultTextFiller();
		
		Align alignNumber = fixedFieldAlign != null ? fixedFieldAlign.align() : layoutConfigAnnotation.defaultNumberAlign();
		Align alignText = fixedFieldAlign != null ? fixedFieldAlign.align() : layoutConfigAnnotation.defaultTextAlign();
		
		DecimalSeparator decimalSeparator = layoutConfigAnnotation.defaultDecimalSeparator();
		
		DateFormat dateFormat = layoutConfigAnnotation.defaultDateFormat();
		
		StringBuilder formatByType = Util.formatByType(field, instance, dateFormat, decimalSeparator);
		
		if(field.getType().isAssignableFrom(String.class)){
			formatByType = setAlign(formatByType.toString(), length, alignText, fillerText);
		}else{
			formatByType = setAlign(formatByType.toString(), length, alignNumber, fillerNumber);
		}
		
		return formatByType;
	}
	
	
	
	private StringBuilder setAlign(String value, int length, Align align, char filler){
		StringBuilder sb = new StringBuilder(length);
		// Preenche a String um value default
		for (int i = 0; i < length; i++)
			sb.insert(i, filler);
		// Corrige length do campo
		int tam_value = value.length();
		if (value.length() > length)  {
			tam_value = length;
			if (align.equals(Align.RIGHT)) {
				value = value.substring(value.length() - length);
			}
		}

		// Verifica Alinhamento e Insere conteudo do campo na String
		try {
			if (align.equals(Align.LEFT)) {
				sb.delete(0, tam_value);
				sb.insert(0, value.substring(0, tam_value));
			} else if (align.equals(Align.RIGHT)) {
				sb.delete((length - tam_value), length);
				sb.insert((length - tam_value), value.substring(0, tam_value));
			}
		} catch (Exception e) {
			System.out.println("Erro: value:|"+value+"| length:"+length+" Tam_value:"+tam_value);
			System.err.println(e);
		}
		return sb;
		
	}
}
