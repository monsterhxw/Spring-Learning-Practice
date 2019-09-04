package geektime.spring.webclient;

import geektime.spring.webclient.model.Coffee;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class WebclientSimpleApplication implements ApplicationRunner {

    @Autowired
    private WebClient webClient;

    public static void main(String[] args) {
//        SpringApplication.run(WebclientSimpleApplication.class, args);
        new SpringApplicationBuilder(WebclientSimpleApplication.class)
            .web(WebApplicationType.NONE)
            .bannerMode(Banner.Mode.OFF)
            .run(args);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(2);

        webClient.get()
            .uri("/coffee/{id}", 1)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .retrieve()
            .bodyToMono(Coffee.class)
            .doOnError(throwable -> log.info("Error: {}", throwable))
            .doFinally(signalType -> cdl.countDown())
            .subscribeOn(Schedulers.single())
            .subscribe(coffee -> log.info("Coffee 1: {}", coffee));

        Mono<Coffee> americano = Mono.just(
            Coffee.builder()
                .name("Americano")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                .build()
        );
        webClient.post()
            .uri("/coffee")
            .body(americano, Coffee.class)
            .retrieve()
            .bodyToMono(Coffee.class)
            .doFinally(signalType -> cdl.countDown())
            .subscribeOn(Schedulers.single())
            .subscribe(coffee -> log.info("Coffee Created: {}", coffee));

        cdl.await();

        webClient.get()
            .uri("/coffee")
            .retrieve()
            .bodyToFlux(Coffee.class)
            .toStream()
            .forEach(coffee -> log.info("Coffee in List: {}", coffee));
    }
}
