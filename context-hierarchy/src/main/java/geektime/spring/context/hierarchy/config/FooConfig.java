package geektime.spring.context.hierarchy.config;

import geektime.spring.context.hierarchy.aspect.FooAspect;
import geektime.spring.context.hierarchy.context.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 父上下文
 */
@Configuration
@EnableAspectJAutoProxy
public class FooConfig {

    @Bean
    public TestBean testBeanX() {
        return new TestBean("parentX");
    }

    @Bean
    public TestBean testBeanY() {
        return new TestBean("parentY");
    }

    @Bean
    public FooAspect fooAspect() {
        return new FooAspect();
    }
}
