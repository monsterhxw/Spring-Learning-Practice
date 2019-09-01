package geektime.spring.context.hierarchy;

import geektime.spring.context.hierarchy.config.FooConfig;
import geektime.spring.context.hierarchy.context.TestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@Slf4j
public class ContextHierarchyApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ContextHierarchyApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 注解加载 fooContext
        ApplicationContext fooContext = new AnnotationConfigApplicationContext(FooConfig.class);

        ClassPathXmlApplicationContext barContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"}, fooContext);

        // 在父上下文查找 testBeanX，命中直接返回 testBeanX
        TestBean bean = fooContext.getBean("testBeanX", TestBean.class);
        bean.hello();

        log.info("---------------------------");

        // 在子上下文查找 testBeanX，命中直接返回 testBeanX
        bean = barContext.getBean("testBeanX", TestBean.class);
        bean.hello();

        // 在子上下文查找 testBeanY，未命中；委托父上下文查
        // 委托机制：在自己的 context 中找不到 bean，会委托父 context 查找该 bean。
        bean = barContext.getBean("testBeanY", TestBean.class);
        bean.hello();

        /*
        场景一：
        父上下文开启 @EnableAspectJAutoProxy 的支持
        子上下文未开启 <aop: aspectj-autoproxy />
            切面 fooAspect 在 FooConfig.java 定义（父上下文增强）

        输出结果：
        testBeanX(foo) 和 testBeanY(foo) 均被增强。
        testBeanX(bar) 未被增强。

        结论：
        在父上下文开启了增强，父的 bean 均被增强，而子的 bean 未被增强。

        场景二：
        父上下文开启 @EnableAspectJAutoProxy 的支持
        子上下文开启 <aop: aspectj-autoproxy />
        切面 fooAspect 在 applicationContext.xml 定义（子上下文增加）

        输出结果：
        testBeanX(foo) 和 testBeanY(foo) 未被增强。
        testBeanX(bar) 被增强。

        结论：
        在子上下文开启增强，父的 bean 未被增强，子的 bean 被增强。

        ----------

        根据场景一和场景二的结果，有结论：
            “各个 context 相互独立，每个 context 的 aop 增强只对本 context 的 bean 生效”。如果想将切面配置成通用的，对父和子上下文的 bean 均支持增强，则：
                1. 切面 fooAspect 定义在父上下文。
                2. 父上下文和子上下文，均要开启 aop 的增加，即 @EnableAspectJAutoProxy 或<aop: aspectj-autoproxy /> 的支持。
        */

    }
}
