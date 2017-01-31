package models.subdataflow;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;


public class RecordMain {

	@FixedField(1)
	public int type;
	
	@FixedField(15)
	public String name;
	
	@FixedField(15)
	public String other;

	@DataFlowRecord
	public SubRecord subRecord;
	
	
}
