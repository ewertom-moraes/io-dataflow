package layouts.igeprev;

import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.delimited.IODataFlowDelimitedLayoutConfig;

@IODataFlowDelimitedLayoutConfig(delimiter = "|")
public class IGEPREV {

	@DataFlowRecord
	public List<IGEPREVEmpregado> empregados;
	
}
