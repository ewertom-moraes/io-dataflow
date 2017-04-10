package layouts.suprev;

import java.util.List;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class SuprevFinanceiroDetalhe {

	@FixedField(value=30)
	public String nome;
	
	@FixedField(value=11)
	public Long cpf;
	 
	public List<Pagamento> pagamentos;

	public SuprevFinanceiroDetalhe(String nome, Long cpf, List<Pagamento> pagamentos) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.pagamentos = pagamentos;
	}
	
	
	
}
