package geektime.spring.controller;

import geektime.spring.controller.controller.interceptor.PerformanceInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableCaching
@EnableDiscoveryClient
public class SpringbucksInterceptorApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbucksInterceptorApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PerformanceInterceptor()).addPathPatterns("/coffee/**").addPathPatterns("/order/**");
    }
}
