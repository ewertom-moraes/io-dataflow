package test.importtest;

import java.util.ArrayList;
import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

@IODataFlowFixedLayoutConfig()
public class ImportSimpleLayout {

	@DataFlowRecord
	public List<DetailSimpleLayout> details = new ArrayList<DetailSimpleLayout>();
	
}


