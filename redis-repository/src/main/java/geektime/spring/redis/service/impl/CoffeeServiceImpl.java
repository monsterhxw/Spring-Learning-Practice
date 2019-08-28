package geektime.spring.redis.service.impl;

import geektime.spring.redis.model.Coffee;
import geektime.spring.redis.model.CoffeeCache;
import geektime.spring.redis.repository.CoffeeCacheRepository;
import geektime.spring.redis.repository.CoffeeRepository;
import geektime.spring.redis.service.CoffeeService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeCacheRepository cacheRepository;

    @Override
    public List<Coffee> findAllCoffee() {
        return coffeeRepository.findAll();
    }

    @Override
    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name", GenericPropertyMatchers.exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    @Override
    public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
        Optional<CoffeeCache> cached = cacheRepository.findOneByName(name);
        if (cached.isPresent()) {
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder()
                .name(coffeeCache.getName())
                .price(coffeeCache.getPrice())
                .build();
            log.info("Coffee {} Found in Cache.", coffeeCache);
            return Optional.of(coffee);
        } else {
            Optional<Coffee> coffee = findOneCoffee(name);
            coffee.ifPresent(c -> {
                CoffeeCache coffeeCache = CoffeeCache.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .price(c.getPrice())
                    .build();
                log.info("Saved Coffee {} to cache.", coffeeCache);
                cacheRepository.save(coffeeCache);
            });
            return coffee;
        }
    }
}
