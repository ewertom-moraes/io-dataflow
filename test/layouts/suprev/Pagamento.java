package layouts.suprev;

import java.math.BigDecimal;

import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;

public class Pagamento {

	@FixedField(value=30)
	public String nomePagamento;
	
	@FixedField(value=15)
	public BigDecimal valor;
	
	public Pagamento(String nomePagamento, BigDecimal valor) {
		super();
		this.nomePagamento = nomePagamento;
		this.valor = valor;
	}
	
	
	
}
