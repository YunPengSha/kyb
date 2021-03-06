package net.sinyoo.cooperation.core.annotation;

import java.lang.annotation.*;

/**
 * 电话号码
 * 
 * File: Telephone.java<br/>
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
public @interface Telephone {

	/**
	 * 返回错误信息
	 * 
	 * @return
	 */
	String message() default "电话号码格式不正确";
}
