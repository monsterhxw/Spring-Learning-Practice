package geektime.spring.aspect.annotation.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 运行时使用该注解
@Retention(RetentionPolicy.RUNTIME)
// 注解作用于方法上
@Target({ElementType.TYPE, ElementType.METHOD})
// 注解包含在 JavaDoc 中
@Documented
public @interface Log {

    /**
     * 日志描述信息
     */
    String description() default "";
}
