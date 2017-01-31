package layouts.cnab;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class SegmentoB {

	@FixedField(1)
	public int type;
	
	@FixedField(30)
	public String email;
	
}
