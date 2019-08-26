package geektime.spring.jpa.complex;

import geektime.spring.jpa.complex.model.Coffee;
import geektime.spring.jpa.complex.model.CoffeeOrder;
import geektime.spring.jpa.complex.model.OrderState;
import geektime.spring.jpa.complex.repository.CoffeeOrderRepository;
import geektime.spring.jpa.complex.repository.CoffeeRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@Slf4j
public class JpaComplexApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaComplexApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrders();
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
            .state(OrderState.INIT)
            .build();
        orderRepository.save(order);
        log.info("Order: {}", order);

        order = CoffeeOrder.builder()
            .customer("Li lei")
            .items(Arrays.asList(espresso, latte))
            .state(OrderState.INIT)
            .build();
        orderRepository.save(order);
        log.info("Order: {}", order);
    }

    private void findOrders() {
        coffeeRepository
            .findAll(Sort.by(Direction.DESC, "id"))
            .forEach(coffee -> log.info("Loading: {}", coffee));

        List<CoffeeOrder> coffeeOrders = orderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(coffeeOrders));

        coffeeOrders = orderRepository.findByCustomerOrderById("Li Lei");
        log.info("findByCustomerOrderById: {}", getJoinedOrderId(coffeeOrders));

        // 不开启事务会因为没 Session 而报 LazyInitializationException
        coffeeOrders.forEach(order ->{
            log.info("Order: {}", order.getId());
            order.getItems().forEach(item -> log.info("   Item: {}", item));
        });

        coffeeOrders = orderRepository.findByItems_Name("latte");
        log.info("findByItems_Name: {}", getJoinedOrderId(coffeeOrders));
    }

    private String getJoinedOrderId(List<CoffeeOrder> coffeeOrders) {
        return coffeeOrders.stream().map(order -> order.getId().toString()).collect(Collectors.joining(","));
    }
}
