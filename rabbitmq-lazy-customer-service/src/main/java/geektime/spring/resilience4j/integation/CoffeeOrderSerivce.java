package geektime.spring.resilience4j.integation;

import geektime.spring.resilience4j.model.CoffeeOrder;
import geektime.spring.resilience4j.model.NewOrderRequest;
import geektime.spring.resilience4j.model.OrderStateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "springbucks-service", contextId = "coffeeOrder")
public interface CoffeeOrderSerivce {

    @GetMapping("/order/{id}")
    CoffeeOrder getOrder(@PathVariable("id") Long id);

    @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CoffeeOrder create(@RequestBody NewOrderRequest newOrder);

    @PutMapping(path = "/order/{id}")
    CoffeeOrder updateState(@PathVariable("id") Long id, @RequestBody OrderStateRequest orderState);
}
