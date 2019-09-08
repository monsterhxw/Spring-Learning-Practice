package geektime.spring.controller.controller;

import geektime.spring.controller.controller.request.OrderRequest;
import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.model.CoffeeOrder;
import geektime.spring.controller.service.CoffeeOrderService;
import geektime.spring.controller.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
@Slf4j
public class CofferOrderController {

    private final CoffeeOrderService orderService;
    private final CoffeeService coffeeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody OrderRequest newOrder) {
        log.info("Receive new Order: {}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(), coffees);
    }

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        CoffeeOrder order = orderService.get(id);
        log.info("Get Order: {}", order);
        return order;
    }
}
