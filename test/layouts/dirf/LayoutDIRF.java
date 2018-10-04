package layouts.dirf;

import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.delimited.IODataFlowDelimitedLayoutConfig;

@IODataFlowDelimitedLayoutConfig( lineEnd="|", delimiter="|" , withHeadersOf=RESPO.class)
public class LayoutDIRF {

	@DataFlowRecord
	public Dirf dirf;
	
	@DataFlowRecord
	public RESPO respo;
	
	@DataFlowRecord
	public DECPJ decpj;
	
	@DataFlowRecord
	public IDREC idrec;
	
	@DataFlowRecord
	public List<BPFDEC> bpfdec;
	
}
