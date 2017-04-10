package layouts.suprev;

import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

@IODataFlowFixedLayoutConfig(delimiter=";")
public class SuprevPessoal {

	@DataFlowRecord
	public List<DetalhesSuprevPessoal> detalhes;
	
}
