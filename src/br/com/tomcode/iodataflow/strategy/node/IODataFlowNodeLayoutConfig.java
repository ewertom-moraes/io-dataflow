package br.com.tomcode.iodataflow.strategy.node;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;
import br.com.tomcode.iodataflow.strategy.fixedfield.Align;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IODataFlowNodeLayoutConfig {
	
		DateFormat defaultDateFormat() default DateFormat.AAAAMMDD;
		DecimalSeparator defaultDecimalSeparator() default DecimalSeparator.NONE;
		
		int spacesIdendet() default 4;
		
}