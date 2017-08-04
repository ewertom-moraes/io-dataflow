package br.com.tomcode.iodataflow.api;

import java.util.List;

public interface ImportCore {


	public Object process(Object objectLayout, List<DataFlowRecord<?>> dataFlowRecords);

	
}
