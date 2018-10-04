package br.com.tomcode.iodataflow.strategy.delimited;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.tomcode.iodataflow.DateFormat;
import br.com.tomcode.iodataflow.DecimalSeparator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IODataFlowDelimitedLayoutConfig {

	DateFormat defaultDateFormat() default DateFormat.AAAAMMDD;
	DecimalSeparator defaultDecimalSeparator() default DecimalSeparator.NONE;
	String lineEnd() default "";
	Class<?> withHeadersOf() default IODataFlowDelimitedLayoutConfig.class;
	String withHeaders() default "";
	
	String delimiter();
}
