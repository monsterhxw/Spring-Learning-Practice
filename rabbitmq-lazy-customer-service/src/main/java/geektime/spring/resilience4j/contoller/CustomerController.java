package geektime.spring.resilience4j.contoller;

import geektime.spring.resilience4j.integation.CoffeeOrderSerivce;
import geektime.spring.resilience4j.integation.CoffeeService;
import geektime.spring.resilience4j.model.Coffee;
import geektime.spring.resilience4j.model.CoffeeOrder;
import geektime.spring.resilience4j.model.NewOrderRequest;
import geektime.spring.resilience4j.model.OrderState;
import geektime.spring.resilience4j.model.OrderStateRequest;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private Bulkhead bulkhead;

    @Value("${customer.name}")
    private String customer;

    public CustomerController(CircuitBreakerRegistry circuitBreakerRegistry, BulkheadRegistry bulkheadRegistry) {
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("menu");
        this.bulkhead = bulkheadRegistry.bulkhead("menu");
    }

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        return Try.ofSupplier(
            Bulkhead.decorateSupplier(
                bulkhead,
                CircuitBreaker.decorateSupplier(
                    circuitBreaker,
                    () -> coffeeService.getAll()
                )
            )
        ).
            recover(CircuitBreakerOpenException.class, Collections.emptyList())
            .recover(BulkheadFullException.class, Collections.emptyList())
            .get();
    }

    @PostMapping("/order")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
    @io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "order")
    public CoffeeOrder createAndPayOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
            .customer(customer)
            .items(Arrays.asList("capuccino"))
            .build();
        CoffeeOrder order = coffeeOrderSerivce.create(orderRequest);
        log.info("Create order: {}", order != null ? order.getId() : "-");
        order = coffeeOrderSerivce.updateState(
            order.getId(),
            OrderStateRequest.builder().state(OrderState.PAID).build()
        );
        log.info("Order is PAID: {}", order);
        return order;
    }
}
