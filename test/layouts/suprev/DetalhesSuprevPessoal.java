package layouts.suprev;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

@IODataFlowFixedLayoutConfig(delimiter=";")
public class DetalhesSuprevPessoal {

	
	@FixedField(2)
	public String codigoCliente;
	
	@FixedField(9)
	public int numeroMatricula;
	
	@FixedField(2)
	public String codigoRDP;
	
	@FixedField(11)
	public Long cpf;
	
}
