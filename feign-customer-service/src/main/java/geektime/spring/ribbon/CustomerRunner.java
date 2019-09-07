package geektime.spring.ribbon;

import geektime.spring.ribbon.integation.CoffeeOrderSerivce;
import geektime.spring.ribbon.integation.CoffeeService;
import geektime.spring.ribbon.model.Coffee;
import geektime.spring.ribbon.model.CoffeeOrder;
import geektime.spring.ribbon.model.NewOrderRequest;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerRunner implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderSerivce coffeeOrderSerivce;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        readMenu();
        Long id = orderCoffee();
        queryOrder(id);
    }

    private void readMenu() {
        List<Coffee> coffees = coffeeService.getAll();
        coffees.forEach(c -> log.info("Coffee: {}", c));
    }

    private Long orderCoffee() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
            .customer("Li Lei")
            .items(Arrays.asList("capuccino"))
            .build();
        CoffeeOrder order = coffeeOrderSerivce.create(orderRequest);
        log.info("Order ID: {}", order.getId());
        return order.getId();
    }

    private void queryOrder(Long id) {
        CoffeeOrder order = coffeeOrderSerivce.getOrder(id);
        log.info("Order: {}", order);
    }
}