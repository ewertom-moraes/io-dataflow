package test.importtest;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.tomcode.iodataflow.api.DataFlow;

public class TestSimpleImport {

	private String file = "Ewertom mozart moraes                             Descricao do ewertom                                                                               ";
	
	@Test
	public void test() {
		
		ImportSimpleLayout layout = new ImportSimpleLayout();
		
		DataFlow dataflow = new DataFlow(layout, file);
		ImportSimpleLayout importToLayout = (ImportSimpleLayout) dataflow.importToLayout();
		
		assertNotNull(importToLayout.details.get(0));
		
	}

}
