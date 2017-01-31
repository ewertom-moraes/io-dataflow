package layouts.cnab;

import java.util.Calendar;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class HeaderArquivo {

	@FixedField(1)
	public int tipo;
	
	@FixedField(8)
	public Calendar data;
	
}
