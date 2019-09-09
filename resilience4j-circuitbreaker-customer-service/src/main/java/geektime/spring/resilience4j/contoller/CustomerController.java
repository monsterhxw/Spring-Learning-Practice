package geektime.spring.resilience4j.contoller;

import geektime.spring.resilience4j.integation.CoffeeOrderSerivce;
import geektime.spring.resilience4j.integation.CoffeeService;
import geektime.spring.resilience4j.model.Coffee;
import geektime.spring.resilience4j.model.CoffeeOrder;
import geektime.spring.resilience4j.model.NewOrderRequest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
@Slf4j
//@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class CustomerController {

    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderSerivce coffeeOrderSerivce;

    private CircuitBreaker circuitBreaker;

    public CustomerController(CircuitBreakerRegistry registry) {
        this.circuitBreaker = registry.circuitBreaker("menu");
    }

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        return Try.ofSupplier(
            CircuitBreaker.decorateSupplier(circuitBreaker, () -> coffeeService.getAll())
        )
            .recover(CircuitBreakerOpenException.class, Collections.emptyList())
            .get();
    }

    @PostMapping("/order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    public CoffeeOrder createOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
            .customer("Li Lei")
            .items(Arrays.asList("capuccino"))
            .build();
        CoffeeOrder order = coffeeOrderSerivce.create(orderRequest);
        log.info("Order ID: {}", order != null ? order.getId() : "-");
        return order;
    }

}
