package geektime.spring.jpa.simple;

import geektime.spring.jpa.simple.model.Coffee;
import geektime.spring.jpa.simple.model.CoffeeOrder;
import geektime.spring.jpa.simple.repository.CoffeeOrderRepository;
import geektime.spring.jpa.simple.repository.CoffeeRepository;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class JpaSimpleApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaSimpleApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
    }

    private void initOrders() {
        Coffee espresso = Coffee.builder()
            .name("espresso")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .build();
        coffeeRepository.save(espresso);
        log.info("Coffee: {}", espresso);

        Coffee latte = Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .build();
        coffeeRepository.save(latte);
        log.info("Coffee: {}", latte);

        CoffeeOrder order = CoffeeOrder.builder()
            .customer("Li Lei")
            .items(Collections.singletonList(espresso))
            .state(0)
            .build();
        orderRepository.save(order);
        log.info("Order: {}", order);

        order = CoffeeOrder.builder()
            .customer("Li lei")
            .items(Arrays.asList(espresso, latte))
            .state(0)
            .build();
        orderRepository.save(order);
        log.info("Order: {}", order);
    }
}
