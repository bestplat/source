package com.bestplat.framework.startup;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动方法注解
 * 
 * @author lujijiang
 * 
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Startup {
	/**
	 * 优先级大小，越大则越先执行。
	 * 
	 * @return
	 */
	public int value() default 0;

	/**
	 * 是否忽略异常，允许系统继续允许。如果为false，则当发生异常时，系统将停止执行
	 * 
	 * @return
	 */
	boolean ignoreError() default false;
}
