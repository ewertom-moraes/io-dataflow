package layouts.dirf;

import br.com.tomcode.iodataflow.DataFlowRecord;

public class INFPC extends InformacoesBPFDEC{

	public final String idRegistro = "INFPC";
	public String cnpj;
	public String nomeEmpresarial;
	
	@DataFlowRecord
	public ValoresMensais rtpp = new ValoresMensais(RegistroDirf.RTPP);
	
}
