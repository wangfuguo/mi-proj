package com.hoau.miser.module.common.server.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *因为已经存在 CookieNonCheckRequired 但在 sys-base 引用时发生循环依赖
 *添加此注解 在 ValidatorCookieInterceptor 里添加对此判断 
 *以过滤掉对登录的校验
 * @author dengyin
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CookieNonCheckRequired {

}
