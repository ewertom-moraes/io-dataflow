package layouts.cnab;

import java.math.BigDecimal;
import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class SegmentoA {

	
	@FixedField(1)
	public int type;
	
	@FixedField(20)
	public String name;
	
	@FixedField(10)
	public BigDecimal sal;
	
	@DataFlowRecord
	public SegmentoB segmentoB;
	//public List<SegmentoB> segmentosB;
	
}
