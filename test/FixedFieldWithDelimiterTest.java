import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*; 

import br.com.tomcode.iodataflow.api.DataFlow;
import layouts.suprev.DetalhesSuprevPessoal;
import layouts.suprev.SuprevPessoal;

public class FixedFieldWithDelimiterTest {

	private static final int NUMBER_OF_ATTRS = 4;
	private static final String DELIMITER = ";"; // used in SuprevPessoal Layout

	@Test
	public void test() {
		
		SuprevPessoal suprevPessoal = new SuprevPessoal();
		
		List<DetalhesSuprevPessoal> detalhes = new ArrayList<DetalhesSuprevPessoal>();
		
		for(int i=0; i < 10; i++){
			DetalhesSuprevPessoal detalhe = new DetalhesSuprevPessoal();
			detalhe.codigoCliente = "85";
			detalhe.numeroMatricula = i + 10;
			detalhe.codigoRDP = "00";
			detalhe.cpf = 1568544227L;
			detalhes.add(detalhe);
		}
		
		suprevPessoal.detalhes = detalhes;
		
		DataFlow dataflow = new DataFlow(suprevPessoal);
		String[] file = dataflow.writeToStringBuilder().toString().split(System.lineSeparator());
		for(int i=0; i <file.length ; i++){
			String line = file[i];
			String[] fields = line.split(DELIMITER);
			assertEquals(NUMBER_OF_ATTRS, fields.length);
			
		}
		System.out.println(dataflow.writeToStringBuilder());
	}

}
