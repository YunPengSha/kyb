package net.sinyoo.cooperation.core.annotation;

import java.lang.annotation.*;

/**
 * 不允许为空字符窜，前提是notNull
 * 
 * File: NotEmpty.java<br/>
 * Description: <br/>
 *
 * Copyright: Copyright (c) 2012 ecbox.com<br/>
 * Company: ECBOX,Inc.<br/>
 *
 * @author WangHui
 * @date 2013-4-22
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented
public @interface NotEmpty {

	/**
	 * 返回错误信息
	 * 
	 * @return
	 */
	String message() default "字符串不允许为空";
}
