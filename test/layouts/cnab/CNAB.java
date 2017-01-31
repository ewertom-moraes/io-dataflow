package layouts.cnab;

import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

@IODataFlowFixedLayoutConfig(defaultDecimalSeparator = DecimalSeparator.COMMA)
public class CNAB {

	@DataFlowRecord
	public HeaderArquivo headerArquivo;
	
	@DataFlowRecord
	public HeaderLote headerLote;
	
	@DataFlowRecord
	public List<SegmentoA> segmentosA;
	
	@DataFlowRecord
	public TrailerLote trailerLote;

	@DataFlowRecord
	public TrailerArquivo trailerArquivo;
	
}
