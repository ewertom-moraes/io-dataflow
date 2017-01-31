package br.com.tomcode.iodataflow.strategy.node;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.Util;
import br.com.tomcode.iodataflow.api.DataFlowRecord;
import br.com.tomcode.iodataflow.api.Generator;
import br.com.tomcode.iodataflow.api.GeneratorCore;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class NodeFieldsGenerator {

	private Object layoutConfig;
	private final StringBuilder _in = new StringBuilder("<");
	private final StringBuilder _out = new StringBuilder(">");
	
	public NodeFieldsGenerator(Object layoutConfig) {
		this.layoutConfig = layoutConfig;
	}

	public StringBuilder writeToStringBuilder(List<DataFlowRecord<?>> dataFlowRecords) {
		
		StringBuilder file = new StringBuilder();

		for (DataFlowRecord<?> record : dataFlowRecords) { // loop de objetos dataFlow que contem os registros a serem exportados

			for (Object obj : record.getObjects()) { // loop de registros a serem exportados
				
				//Field[] fields = obj.getClass().getFields();
				
				List<Object> list = new ArrayList<Object>();
				if(obj instanceof List ){//.getClass().isArray()){ 
					List<Object> subList = (List) obj;
					for(Object o : subList){
						list.add(o);
					}
				}else{
					list.add(obj);
				}
				for(Object subObject : list){ // loop de objetos no atributo (se for um list, senao é o proprio objeto)
					StringBuilder stringObj = new StringBuilder(_in + subObject.getClass().getSimpleName()+ _out);

					for (Field field : subObject.getClass().getFields()) { // loop de fields do registro

						FixedField sequencialField = field.getAnnotation(FixedField.class);
						br.com.tomcode.iodataflow.DataFlowRecord dataFlowRecord = field.getAnnotation(br.com.tomcode.iodataflow.DataFlowRecord.class);

						if(dataFlowRecord!=null){
							try {
								Object subSubObject = field.get(subObject);
								if(field.get(subObject) == null)
									continue;
								stringObj.append(System.lineSeparator());
								stringObj.append(writeToStringBuilder(Arrays.asList(new DataFlowRecord(subSubObject,  record.getLayout() ))));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						
						if (sequencialField != null) {
							stringObj.append(format(field, subObject));
						}
					}
					
					file.append(stringObj);
					file.append(appendLineSeparator(file));
				}
			}
		}
		return file;
		
		
	}

	private String appendLineSeparator(StringBuilder str) {
		if(!str.toString().endsWith(System.lineSeparator()))
			return System.lineSeparator();
		return "";
	}

	protected StringBuilder format(Field field, Object instance){
		
		IODataFlowNodeLayoutConfig layoutConfigAnnotation = this.layoutConfig.getClass().getAnnotation(IODataFlowNodeLayoutConfig.class);
		
		DecimalSeparator decimalSeparator = layoutConfigAnnotation.defaultDecimalSeparator();
		
		DateFormat dateFormart = layoutConfigAnnotation.defaultDateFormat();
		
		String value = "";
		
		StringBuilder valueProcessed = new StringBuilder(value);
		
		Class<?> type = field.getType();
		
		try {
			if(field.get(instance)==null)
				return new StringBuilder();
			
			if(type.isAssignableFrom(String.class)){
				value =  (String) field.get(instance);
				valueProcessed = new StringBuilder(value);
				return valueProcessed;
			}
			
			if(type.isAssignableFrom(Integer.TYPE)){
				value = "" +(Integer) field.get(instance);
			}
			
			if(type.isAssignableFrom(BigDecimal.class)){
				BigDecimal bigDecimal = (BigDecimal) field.get(instance);
				value = bigDecimal.toString().replace(".", decimalSeparator.getValue());
			}
			
			if(type.isAssignableFrom(Double.TYPE)){
				value = "" +(BigDecimal) field.get(instance);
			}
			
			if(type.isAssignableFrom(Calendar.class)){
				Calendar calendar = (Calendar) field.get(instance);
				value = Util.calendarToString(calendar, dateFormart);
			}
			
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		return valueProcessed = new StringBuilder(value);
	}
	
	
//	@Override
//	public void writeToFile(List<DataFlowRecord<?>> layouts, File file) {
//		// TODO Auto-generated method stub
//
//	}

}
