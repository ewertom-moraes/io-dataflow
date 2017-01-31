package layouts.cnab;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class TrailerArquivo {

	@FixedField(1)
	public int type;
	
	@FixedField(5)
	public int total;
	
}
