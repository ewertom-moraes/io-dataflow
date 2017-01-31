import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.tomcode.iodataflow.api.DataFlow;
import layouts.igeprev.IGEPREV;
import layouts.igeprev.IGEPREVEmpregado;

public class DlimitedLayoutTest {

	DataFlow dataFlow;
	
	@Before
	public void init(){
		IGEPREVEmpregado emp = new IGEPREVEmpregado();
		emp.id = 1;
		emp.name = "Joao da Silva";
		emp.sal = BigDecimal.valueOf(17123.5);
		
		IGEPREV igeprev = new IGEPREV();
		igeprev.empregados = Arrays.asList(emp);
		
		dataFlow = new DataFlow(igeprev);
	}
	
	@Test
	public void testOneLine() {
		
		String[] linesProcessed = dataFlow.writeToStringBuilder().toString().split(System.lineSeparator());
		String[] linesExpected = {"1|Joao da Silva|171235"}; 
		
		
		for(int i=0; i < linesProcessed.length ; i++){
			System.out.println(linesProcessed[i]);
			assertEquals(linesExpected[i], linesProcessed[i]);
			//System.out.println(linesExpected[i]);
			
		}
	}
	
	
}
