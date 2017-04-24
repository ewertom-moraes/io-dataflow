import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import br.com.tomcode.iodataflow.DataFlowRecord;
import br.com.tomcode.iodataflow.api.DataFlow;
import br.com.tomcode.iodataflow.strategy.fixedfield.DecimalField;
import br.com.tomcode.iodataflow.strategy.fixedfield.FixedField;
import br.com.tomcode.iodataflow.strategy.fixedfield.IODataFlowFixedLayoutConfig;

public class FieldDecimalTest {

	@IODataFlowFixedLayoutConfig
	public class LayoutModel{
		
		@DataFlowRecord
		public Detail detail;
	}
	
	public class Detail{
		
		@FixedField(10)
		public String name;
		
		@FixedField(10)
		@DecimalField(4)
		public BigDecimal value;
		public Detail(String name, BigDecimal value) {
			super();
			this.name = name;
			this.value = value;
		}
	}
	
	@Test
	public void testDecimalfrom1() {
		
		LayoutModel layout = new LayoutModel();
		layout.detail = new Detail("name", BigDecimal.valueOf(150.1)); 
		
		StringBuilder stringBuilder = new DataFlow(layout).writeToStringBuilder();
		
		Assert.assertTrue(stringBuilder.toString().contains("1501000"));
	}
	
	@Test
	public void testDecimalfrom01() {
		
		LayoutModel layout = new LayoutModel();
		layout.detail = new Detail("name", BigDecimal.valueOf(150.01)); 
		
		StringBuilder stringBuilder = new DataFlow(layout).writeToStringBuilder();
		
		Assert.assertTrue(stringBuilder.toString().contains("1500100"));
	}
	
	@Test
	public void testDecimalfrom001() {
		
		LayoutModel layout = new LayoutModel();
		layout.detail = new Detail("name", BigDecimal.valueOf(150.001)); 
		
		StringBuilder stringBuilder = new DataFlow(layout).writeToStringBuilder();
		
		Assert.assertTrue(stringBuilder.toString().contains("1500010"));
	}
	
	@Test
	public void testDecimalfrom1234() {
		
		LayoutModel layout = new LayoutModel();
		layout.detail = new Detail("name", BigDecimal.valueOf(150.1234)); 
		
		StringBuilder stringBuilder = new DataFlow(layout).writeToStringBuilder();
		
		Assert.assertTrue(stringBuilder.toString().contains("1501234"));
	}
	
	@Test
	public void testWithoutDecimal() {
		
		LayoutModel layout = new LayoutModel();
		layout.detail = new Detail("name", BigDecimal.valueOf(150)); 
		
		StringBuilder stringBuilder = new DataFlow(layout).writeToStringBuilder();
		
		Assert.assertTrue(stringBuilder.toString().contains("1500000"));
	}

	
}
