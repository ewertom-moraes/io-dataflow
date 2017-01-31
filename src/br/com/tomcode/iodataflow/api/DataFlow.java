package br.com.tomcode.iodataflow.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.tomcode.iodataflow.strategy.delimited.DelimitedFieldStrategy;
import br.com.tomcode.iodataflow.strategy.delimited.IODataFlowDelimitedLayoutConfig;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedFieldsStrategy;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;
import br.com.tomcode.iodataflow.strategy.node.IODataFlowNodeLayoutConfig;


public class DataFlow {

	private List<DataFlowRecord<?>> dataFlowRecords;
	private GeneratorCore generator;

	public DataFlow(Object layoutConfig) {
		
		if (layoutConfig!=null){
			if(layoutConfig.getClass().getAnnotation(IODataFlowFixedLayoutConfig.class)!=null){
				generator = new GeneratorCore(new FixedFieldsStrategy(layoutConfig.getClass()));
				
			}else if(layoutConfig.getClass().getAnnotation(IODataFlowDelimitedLayoutConfig.class)!=null){
				generator = new GeneratorCore(new DelimitedFieldStrategy(layoutConfig.getClass()));
				
			}else if(layoutConfig.getClass().getAnnotation(IODataFlowNodeLayoutConfig.class)!=null){
				//generator = new NodeFieldsGenerator(layoutConfig);
				throw new RuntimeException("Class of object argument layoutConfig IODataFlowNodeLayoutConfig not yet working :(");
			}else{
				throw new RuntimeException("Class of object argument layoutConfig without Annotation Layout Config");
			}
		}
			
		this.dataFlowRecords = new ArrayList<>();
		
		for(Field field : layoutConfig.getClass().getFields()){
			if(field.getAnnotation(br.com.tomcode.iodataflow.DataFlowRecord.class)!= null){
				// trata o field como um DataFlowRecord.
				try {
					if(field.get(layoutConfig)== null){
						continue;
					}
					DataFlowRecord<?> dataFlowRecord = new DataFlowRecord<>(field.get(layoutConfig), layoutConfig);
					this.dataFlowRecords.add(dataFlowRecord);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public StringBuilder writeToStringBuilder(){
		return generator.writeToStringBuilder(dataFlowRecords);
	}
	
//	public void writeToFile(java.io.File file){
//		generator.writeToFile(dataFlowRecords, file);
//	}

	
}
