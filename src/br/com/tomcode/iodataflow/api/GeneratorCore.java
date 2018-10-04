package br.com.tomcode.iodataflow.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

public class GeneratorCore implements Generator{

	private DataFlowStrategy dataFlowStrategy;
	
	public GeneratorCore(DataFlowStrategy dataFlowStrategy) {
		this.dataFlowStrategy = dataFlowStrategy;
	}
	
	public StringBuilder writeToStringBuilder(List<DataFlowRecord<?>> dataFlowRecords) {
		StringBuilder strBuilder = writeToStringBuilderExecute(dataFlowRecords);
		strBuilder = dataFlowStrategy.finalize(strBuilder);
		return strBuilder;
	}
	
	private StringBuilder writeToStringBuilderExecute(List<DataFlowRecord<?>> dataFlowRecords) {
		StringBuilder file = new StringBuilder();

		for (DataFlowRecord<?> record : dataFlowRecords) { // loop de objetos dataFlow que contem os registros a serem exportados

			for (Object obj : record.getObjects()) { // loop de registros a serem exportados
				
				for(Object subObject : objToList(obj)){ // loop de objetos no atributo (se for um list, senao é o proprio objeto)
					StringBuilder stringObj = new StringBuilder("");

					if(subObject == null)
						continue;
					
					for (Field field : subObject.getClass().getFields()) { // loop de fields do registro

						br.com.tomcode.iodataflow.DataFlowRecord dataFlowRecord = field.getAnnotation(br.com.tomcode.iodataflow.DataFlowRecord.class);
						
						Object value = null;
						try {
							value = field.get(subObject);
						} catch (IllegalArgumentException | IllegalAccessException e1) {
							e1.printStackTrace();
						}
						
						if(dataFlowRecord!=null){
							try {
								if(value == null)
									continue;
								if(stringObj.length() > 0)
									stringObj.append(System.lineSeparator());
								StringBuilder recurse = writeToStringBuilder(Arrays.asList(new DataFlowRecord(value,  record.getLayout() )));
								
								boolean endsWithLineSeparator = recurse.toString().endsWith(System.lineSeparator());
								if(endsWithLineSeparator)
									recurse = recurse.delete(recurse.lastIndexOf(System.lineSeparator()), recurse.length());
								stringObj.append(recurse);
								
							} catch (IllegalArgumentException  e) {
								e.printStackTrace();
							}
							// se o campo for dataflow, recursivamente será gerado os valores dos atributos deste
							// logo, nao deve continuar para gerar o valor dele proprio com format.
							// format é apenas para campos que possuem valor proprio como string, int, etc...
							continue; 
						}else if(value instanceof List){
							 List<Object> list = objToList(value);
							 for(Object sub : list){
								for(Field subfield : sub.getClass().getFields()){
									stringObj.append(dataFlowStrategy.format(subfield, sub));
								}
							 }
							 continue;
						}
						stringObj.append(dataFlowStrategy.format(field, subObject));
					}
					file.append(stringObj);
					file.append(dataFlowStrategy.appendLineSeparator(file));
				}
			}
		}
		return file;
	}
	
	private List<Object> objToList(Object obj){
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
