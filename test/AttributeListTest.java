import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import br.com.tomcode.iodataflow.api.DataFlow;
import layouts.suprev.Pagamento;
import layouts.suprev.SuprevFinanceiro;
import layouts.suprev.SuprevFinanceiroDetalhe;

public class AttributeListTest {

	@Test
	public void testListAttribute() {
		
		SuprevFinanceiro suprev = new SuprevFinanceiro();
		suprev.detalhes = new ArrayList<>();
		
		suprev.detalhes.add(new SuprevFinanceiroDetalhe("joao da silva", 131515815L, 
				Arrays.asList(	new Pagamento("vale alimentação", BigDecimal.valueOf(150.0)),
								new Pagamento("auxilio saude", BigDecimal.valueOf(110.0)),
								new Pagamento("emprestimo", BigDecimal.valueOf(300.0)) )));
		suprev.detalhes.add(new SuprevFinanceiroDetalhe("maria da silva", 123456789L, 
				Arrays.asList(new Pagamento("vale alimentação", BigDecimal.valueOf(150.0)),
						new Pagamento("auxilio saude", BigDecimal.valueOf(110.0)),
						new Pagamento("emprestimo", BigDecimal.valueOf(300.0))  )));
		suprev.detalhes.add(new SuprevFinanceiroDetalhe("fulan de souza", 1134648L, 
				Arrays.asList(new Pagamento("vale alimentação", BigDecimal.valueOf(150.0)),
						new Pagamento("auxilio saude", BigDecimal.valueOf(110.0)),
						new Pagamento("emprestimo", BigDecimal.valueOf(300.0))  )));
		
		
		DataFlow dataflow = new DataFlow(suprev);
		
		StringBuilder stringBuilder = dataflow.writeToStringBuilder();
		
		System.out.println(stringBuilder);
		
		String[] lines = stringBuilder.toString().split(System.lineSeparator());
		
		assertEquals(suprev.detalhes.size(), lines.length);
		int numberFieldsExpected = 8; // nome, cpf + (3 x campos de pagamento)
		assertEquals(numberFieldsExpected, lines[0].split(";").length);
		assertTrue(stringBuilder.toString().contains(suprev.detalhes.get(0).pagamentos.get(0).nomePagamento));
		
	}

}
