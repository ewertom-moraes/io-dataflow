package br.com.tomcode.iodataflow.strategy.fixedfield;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IODataFlowFixedLayoutConfig {
	
		DateFormat defaultDateFormat() default DateFormat.AAAAMMDD;
		DecimalSeparator defaultDecimalSeparator() default DecimalSeparator.NONE;
		
		Align defaultTextAlign() default Align.LEFT;
		Align defaultNumberAlign() default Align.RIGHT;
		
		char defaultTextFiller() default ' ';
		char defaultNumberFiller() default '0';
		
		String lineEnd() default "";
}
