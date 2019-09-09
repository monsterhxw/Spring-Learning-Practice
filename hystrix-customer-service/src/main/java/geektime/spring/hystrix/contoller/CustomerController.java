package geektime.spring.hystrix.contoller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import geektime.spring.hystrix.integation.CoffeeOrderSerivce;
import geektime.spring.hystrix.integation.CoffeeService;
import geektime.spring.hystrix.model.Coffee;
import geektime.spring.hystrix.model.CoffeeOrder;
import geektime.spring.hystrix.model.NewOrderRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
@Slf4j
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class CustomerController {

    private final CoffeeService coffeeService;
    private final CoffeeOrderSerivce coffeeOrderSerivce;

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        List<Coffee> coffees = coffeeService.getAll();
        return coffees == null ? Collections.emptyList() : coffees;
    }

    @PostMapping("/order")
    @HystrixCommand(fallbackMethod = "fallbackCreateOrder")
    public CoffeeOrder createOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
            .customer("Li Lei")
            .items(Arrays.asList("capuccino"))
            .build();
        CoffeeOrder order = coffeeOrderSerivce.create(orderRequest);
        log.info("Order ID: {}", order != null ? order.getId() : "-");
        return order;
    }

    public CoffeeOrder fallbackCreateOrder() {
        log.warn("Fallback to null order.");
        return null;
    }

}
