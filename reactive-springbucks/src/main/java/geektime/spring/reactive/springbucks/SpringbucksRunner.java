package geektime.spring.reactive.springbucks;

import geektime.spring.reactive.springbucks.model.Coffee;
import geektime.spring.reactive.springbucks.model.CoffeeOrder;
import geektime.spring.reactive.springbucks.model.OrderState;
import geektime.spring.reactive.springbucks.service.CoffeeOrderService;
import geektime.spring.reactive.springbucks.service.CoffeeService;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringbucksRunner implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeOrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeService.initCache()
            .then(coffeeService.findOneCoffee("mocha")
                .flatMap(c -> {
                    CoffeeOrder order = createOrder("Li Lei", c);
                    return orderService.create(order);
                })
                .doOnError(t -> log.error("error", t)))
            .subscribe(o -> log.info("Create Order: {}", o));
        log.info("After Subscribe");
        Thread.sleep(5000);
    }

    private CoffeeOrder createOrder(String customer, Coffee... coffees) {
        return CoffeeOrder.builder()
            .customer(customer)
            .items(Arrays.asList(coffees))
            .state(OrderState.INIT)
            .createTime(new Date())
            .updateTime(new Date())
            .build();
    }
}
