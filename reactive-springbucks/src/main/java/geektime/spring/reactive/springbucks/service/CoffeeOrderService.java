package geektime.spring.reactive.springbucks.service;

import geektime.spring.reactive.springbucks.model.CoffeeOrder;
import reactor.core.publisher.Mono;

public interface CoffeeOrderService {

    Mono<Long> create(CoffeeOrder order);
}
