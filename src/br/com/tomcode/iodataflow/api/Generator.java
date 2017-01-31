package br.com.tomcode.iodataflow.api;

import java.io.File;
import java.util.List;

public interface Generator {

	public StringBuilder writeToStringBuilder(List<DataFlowRecord<?>> layouts);
	
//	public void writeToFile(List<DataFlowRecord<?>> layouts, File file);
	
}
