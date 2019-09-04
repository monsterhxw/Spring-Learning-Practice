package geektime.spring.resttemplate;

import geektime.spring.resttemplate.model.Coffee;
import java.net.URI;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@Slf4j
public class RestTemplateApplication implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
//        SpringApplication.run(RestTemplateApplication.class, args);
        new SpringApplicationBuilder()
            .sources(RestTemplateApplication.class)
            .bannerMode(Mode.OFF)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/coffee?name={name}").build("mocha");
        RequestEntity<Void> req = RequestEntity.get(uri).accept(MediaType.APPLICATION_XML).build();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        log.info("Response Status: {}, Response Headers: {}", resp.getStatusCode(), resp.getHeaders().toString());
        log.info("Coffee: {}", resp.getBody());

        String coffeeUri = "http://localhost:8080/coffee";
        Coffee americano = Coffee.builder()
            .name("Americano")
            .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
            .build();
        Coffee response = restTemplate.postForObject(coffeeUri, americano, Coffee.class);
        log.info("New Coffee: {}", response);

        ParameterizedTypeReference<List<Coffee>> ptr = new ParameterizedTypeReference<List<Coffee>>() {
        };
        ResponseEntity<List<Coffee>> list = restTemplate.exchange(coffeeUri, HttpMethod.GET, null, ptr);
        list.getBody().forEach(coffee -> log.info("Coffee: {}", coffee));
    }
}
