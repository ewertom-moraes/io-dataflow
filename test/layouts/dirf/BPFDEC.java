package layouts.dirf;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;

public class BPFDEC {

	public final String idRegistro = "BPFDEC";
	public String cpf;
	public String nome;
	public Calendar dataMolestiaGrave;
	
	@DataFlowRecord
	public List<InformacoesBPFDEC> informacoesBPFDEC = new ArrayList<InformacoesBPFDEC>();
}
