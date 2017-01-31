package layouts.dirf;

import br.com.tomcode.iodataflow.DataFlowRecord;

public class INFPA extends InformacoesBPFDEC{
	
	@DataFlowRecord
	public ValoresMensais rtpa = new ValoresMensais(RegistroDirf.RTPA);
}
