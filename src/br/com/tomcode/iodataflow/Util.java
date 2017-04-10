package br.com.tomcode.iodataflow;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

	public static String calendarToString(Calendar date, DateFormat dateFormat){
		return new SimpleDateFormat(dateFormat.getPattern()).format(date.getTime());
	}
	
	public static String bigDecimalToString(BigDecimal bigDecimal, DecimalSeparator decimalSeparator){
		return bigDecimal.toString().replace(".", decimalSeparator.getValue());
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
				value = bigDecimalToString((BigDecimal) field.get(instance), decimalSeparator);
			}
			
			if(type.isAssignableFrom(Double.TYPE)|| type.isAssignableFrom(Double.class)){
				value = "" +(BigDecimal) field.get(instance);
			}
			
			if(type.isAssignableFrom(Calendar.class)){
				value = calendarToString((Calendar) field.get(instance), dateFormat);
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		return new StringBuilder(value);
	}
	
}
