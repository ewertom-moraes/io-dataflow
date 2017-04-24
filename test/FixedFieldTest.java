import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.api.DataFlow;
import br.com.tomcode.iodataflow.api.TypeFile;
import layouts.cnab.CNAB;
import layouts.cnab.HeaderArquivo;
import layouts.cnab.SegmentoA;
import layouts.cnab.SegmentoB;
import layouts.cnab.TrailerArquivo;

public class FixedFieldTest {
	
	@Test
	public void testIfIsOkLayout(){
		
		CNAB cnab = new CNAB();
		cnab.headerArquivo = new HeaderArquivo();
		cnab.headerArquivo.tipo = 1;
		cnab.headerArquivo.data = Calendar.getInstance();
		cnab.headerArquivo.data.set(2016, 4-1, 5);
		
		SegmentoA a1 = new SegmentoA();
		a1.type = 2;
		a1.name = "Jose da Silva";
		a1.sal = BigDecimal.valueOf(17123.50);
		
		SegmentoB a1b1 = new SegmentoB();
		a1b1.type = 2;
		a1b1.email = "josedasilva@email.com";
		a1.segmentoB = a1b1;
		
		SegmentoA a2 = new SegmentoA();
		a2.type = 2;
		a2.name = "Maria da Silva";
		
		cnab.segmentosA = Arrays.asList(a1,a2);; 
		
		cnab.trailerArquivo = new TrailerArquivo();
		cnab.trailerArquivo.type = 9;
		cnab.trailerArquivo.total = 2;
		
		DataFlow exportCNAB = new DataFlow(cnab);
		
		StringBuilder expected = new StringBuilder("");
		expected.append(cnab.headerArquivo.tipo);
		expected.append("20160405");
		expected.append(System.lineSeparator());
		
		expected.append(cnab.segmentosA.get(0).type);
		expected.append(cnab.segmentosA.get(0).name+"       ");
		expected.append("00"+cnab.segmentosA.get(0).sal.toString().replace(".", DecimalSeparator.COMMA.getValue()) + "0");
		expected.append(System.lineSeparator());
		expected.append(cnab.segmentosA.get(0).segmentoB.type);
		expected.append(cnab.segmentosA.get(0).segmentoB.email+"         ");
		expected.append(System.lineSeparator());
		expected.append(cnab.segmentosA.get(1).type);
		expected.append(cnab.segmentosA.get(1).name+"      ");
		expected.append("0000000000");
		expected.append(System.lineSeparator());
		
		expected.append(cnab.trailerArquivo.type);
		expected.append("0000"+cnab.trailerArquivo.total);
		expected.append(System.lineSeparator());
		
		String[] linesExpected = expected.toString().split(System.lineSeparator());
		String[] linesProcessed = exportCNAB.writeToStringBuilder().toString().split(System.lineSeparator());
		
		for(int i=0; i < linesExpected.length ; i++){
			System.out.println(i);
			assertEquals(linesExpected[i], linesProcessed[i]);
			//System.out.println(linesExpected[i]);
			System.out.println(linesProcessed[i]);
		}
	}
	
}
