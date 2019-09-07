package geektime.spring.ribbon;

import geektime.spring.ribbon.support.CustomConnectionKeepAliveStrategy;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
public class FeignCustomerServiceApplication {

    public static void main(String[] args) {
//        new SpringApplicationBuilder()
//            .sources(FeignCustomerServiceApplication.class)
//            .bannerMode(Banner.Mode.OFF)
//            .web(WebApplicationType.NONE)
//            .run(args);
        SpringApplication.run(FeignCustomerServiceApplication.class, args);
    }

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClients.custom()
            .setConnectionTimeToLive(30, TimeUnit.SECONDS)
            .evictIdleConnections(30, TimeUnit.SECONDS)
            .setMaxConnTotal(200)
            .setMaxConnPerRoute(20)
            .disableAutomaticRetries()
            .setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
            .build();
    }
}
