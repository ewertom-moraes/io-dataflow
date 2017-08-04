package br.com.tomcode.iodataflow.api;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import br.com.tomcode.iodataflow.Util;

public class GeneratorCore implements Generator{

	private DataFlowStrategy dataFlowStrategy;
	
	public GeneratorCore(DataFlowStrategy dataFlowStrategy) {
		this.dataFlowStrategy = dataFlowStrategy;
	}
	
	public StringBuilder writeToStringBuilder(List<DataFlowRecord<?>> dataFlowRecords) {
		StringBuilder file = new StringBuilder();

		for (DataFlowRecord<?> record : dataFlowRecords) { // loop de objetos dataFlow que contem os registros a serem exportados

			for (Object obj : record.getObjects()) { // loop de registros a serem exportados
				
				for(Object subObject : Util.objToList(obj)){ // loop de objetos no atributo (se for um list, senao é o proprio objeto)
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
							 List<Object> list = Util.objToList(value);
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
	
	
	
}
