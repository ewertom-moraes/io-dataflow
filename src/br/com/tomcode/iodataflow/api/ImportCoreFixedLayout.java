package br.com.tomcode.iodataflow.api;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import br.com.tomcode.iodataflow.Util;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

public class ImportCoreFixedLayout implements ImportCore{

	private Object objectLayout;
	private List<DataFlowRecord<?>> dataFlowRecords;
	private String file;

	public ImportCoreFixedLayout(String file) {
		this.file = file;
	}

	// proximos passos:
	/*
	 * deve haver uma identificação de cada "dataflowrecord" na raiz da classe layout sobre como identificar os tipos de linhas no arquivo.
	 * exemplo: a linha começa com 00, então é header. Logo poderia ser assim: @DataFlowIdLine(idLine="00", position="2")
	 * 
	 * */
	
	public Object process(Object objectLayout, List<DataFlowRecord<?>> dataFlowRecords){
		
		IODataFlowFixedLayoutConfig layoutAnot = objectLayout.getClass().getAnnotation(IODataFlowFixedLayoutConfig.class);
		
		try {
			Object result = objectLayout.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		String[] lines = file.split(System.lineSeparator());
		int indiceLinha = -1;
		
		StringBuilder file = new StringBuilder();

		for (DataFlowRecord<?> record : dataFlowRecords) { // loop de objetos dataFlow que contem os registros a serem exportados

			for (Object obj : record.getObjects()) { // loop de registros a serem exportados
				
				indiceLinha++; // pula de linha do arquivo
				String linha = lines[indiceLinha];
				int posicao = 0;
				
				for(Object subObject : Util.objToList(obj)){ // loop de objetos no atributo (se for um list, senao é o proprio objeto)

					if(subObject == null)
						continue;
					
					for (Field field : subObject.getClass().getFields()) { // loop de fields do registro

						System.out.println(field.getType());
						System.out.println(field.getGenericType());
						
						
						br.com.tomcode.iodataflow.DataFlowRecord dataFlowRecord = field.getAnnotation(br.com.tomcode.iodataflow.DataFlowRecord.class);
						FixedField fixedField = field.getAnnotation(FixedField.class);
						
						String value = linha.substring(posicao, fixedField.value());
						posicao += fixedField.value();
						
						if(dataFlowRecord!=null){
							try {
								if(value == null)
									continue;
								Object recurse = process(objectLayout, Arrays.asList(new DataFlowRecord(value,  record.getLayout() )));
								field.set(subObject, recurse); // se o valor no atributo/field. 
								
							} catch (IllegalArgumentException  e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continue; 
						}
						
						try {
							field.set(subObject, value);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		
		return objectLayout;
		
	}
	
}
