package br.com.tomcode.iodataflow;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tomcode.iodataflow.strategy.fixedfield.DecimalField;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedFieldAlign;

public class Util {

	public static String calendarToString(Calendar date, DateFormat dateFormat){
		return new SimpleDateFormat(dateFormat.getPattern()).format(date.getTime());
	}
	
	private static String setDecimalDigits(String string, int decimalDigitis){
		String[] split = string.split("\\.");
		int decimaisAtuais = 0;
		if(split.length > 1){
			decimaisAtuais = split[1].length();
		}
		while(decimaisAtuais < decimalDigitis){
			string = string + 0;
			decimaisAtuais++;
		}
		return string;
	}
	
	public static String bigDecimalToString(BigDecimal bigDecimal, DecimalSeparator decimalSeparator, int decimalDigitis){
		String string = setDecimalDigits(bigDecimal.toString(), decimalDigitis);
		return string.replace(".", decimalSeparator.getValue());
	}
	
	public static String doubleToString(Double doubl, DecimalSeparator decimalSeparator, int decimalDigitis){
		String string = setDecimalDigits( doubl.toString(), decimalDigitis);
		return string.replace(".", decimalSeparator.getValue());
	}
	
	public static String integerToString(Integer integer){
		return integer+"";
	}
	
	public static String longToString(Long lng){
		return new Long(lng).toString();
	}
	
	public static StringBuilder formatByType(Field field, Object instance, DateFormat dateFormat, DecimalSeparator decimalSeparator){
		
		String value = "";
		Class<?> type = field.getType();
		
		int decimals = 2;
		DecimalField decimalField = field.getAnnotation(DecimalField.class);
		if(decimalField!= null){
			decimals = decimalField.value();
		}
		
		try {
			if(field.get(instance)==null)
				return new StringBuilder();
			
			if(type.isAssignableFrom(String.class)){
				value =  (String) field.get(instance);
			}
			
			if(type.isAssignableFrom(Integer.TYPE) || type.isAssignableFrom(Integer.class)){
				value = integerToString((Integer) field.get(instance));
			}
			if(type.isAssignableFrom(Long.TYPE) || type.isAssignableFrom(Long.class)){
				value = longToString((Long) field.get(instance));
			}
			
			if(type.isAssignableFrom(BigDecimal.class)){
				value = bigDecimalToString((BigDecimal) field.get(instance), decimalSeparator, decimals);
			}
			
			if(type.isAssignableFrom(Double.TYPE)|| type.isAssignableFrom(Double.class)){
				value = doubleToString((Double) field.get(instance), decimalSeparator, decimals);
			}
			
			if(type.isAssignableFrom(Calendar.class)){
				value = calendarToString((Calendar) field.get(instance), dateFormat);
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		return new StringBuilder(value);
	}
	
	public static List<Object> objToList(Object obj){
		List<Object> list = new ArrayList<Object>();
		if(obj instanceof List ){//.getClass().isArray()){ 
			List<Object> subList = (List) obj;
			for(Object o : subList){
				list.add(o);
			}
		}else{
			list.add(obj);
		}
		return list;
	}
	
}
