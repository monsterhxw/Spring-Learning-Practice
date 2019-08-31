package geektime.spring.reactive.springbucks.service;

import geektime.spring.reactive.springbucks.model.Coffee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoffeeService {

    Flux<Boolean> initCache();

    Mono<Coffee> findOneCoffee(String name);

}
