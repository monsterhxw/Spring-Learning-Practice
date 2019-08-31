package geektime.spring.reactive.springbucks.service.impl;

import geektime.spring.reactive.springbucks.model.Coffee;
import geektime.spring.reactive.springbucks.repository.CoffeeRepository;
import geektime.spring.reactive.springbucks.service.CoffeeService;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    private static final String PREFIX = "springbucks-";

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate;

    @Override
    public Flux<Boolean> initCache() {
        return coffeeRepository.findAll()
            .flatMap(c -> reactiveRedisTemplate.opsForValue()
                .set(PREFIX + c.getName(), c)
                .flatMap(b -> reactiveRedisTemplate.expire(PREFIX + c.getName(), Duration.ofMinutes(1)))
                .doOnSuccess(v -> log.info("Loading and caching {}", c)));
    }

    @Override
    public Mono<Coffee> findOneCoffee(String name) {
        return reactiveRedisTemplate.opsForValue()
            .get(PREFIX + name)
            .switchIfEmpty(
                coffeeRepository.findByName(name)
                    .doOnSuccess(s -> log.info("Loading {} from DB.", name))
            );
    }
}
