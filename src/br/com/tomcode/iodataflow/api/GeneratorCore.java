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
					StringBuilder stringObj = new StringBuilder("");

					if(subObject == null)
						continue;
					
					for (Field field : subObject.getClass().getFields()) { // loop de fields do registro

						br.com.tomcode.iodataflow.DataFlowRecord dataFlowRecord = field.getAnnotation(br.com.tomcode.iodataflow.DataFlowRecord.class);

						if(dataFlowRecord!=null){
							try {
								Object subSubObject = field.get(subObject);
								if(field.get(subObject) == null)
									continue;
								if(stringObj.length() > 0)
									stringObj.append(System.lineSeparator());
								StringBuilder recurse = writeToStringBuilder(Arrays.asList(new DataFlowRecord(subSubObject,  record.getLayout() )));
								
								boolean endsWithLineSeparator = recurse.toString().endsWith(System.lineSeparator());
								if(endsWithLineSeparator)
									recurse = recurse.delete(recurse.lastIndexOf(System.lineSeparator()), recurse.length());
								stringObj.append(recurse);
								
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
							// se o campo for dataflow, recursivamente será gerado os valores dos atributos deste
							// logo, nao deve continuar para gerar o valor dele proprio com format.
							// format é apenas para campos que possuem valor proprio como string, int, etc...
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
