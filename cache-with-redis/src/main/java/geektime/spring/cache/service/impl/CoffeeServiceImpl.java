package geektime.spring.cache.service.impl;

import geektime.spring.cache.model.Coffee;
import geektime.spring.cache.repository.CoffeeRepository;
import geektime.spring.cache.service.CoffeeService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "coffee")
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Cacheable
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

    @CacheEvict
    @Override
    public void reloadCoffee() {

    }
}
