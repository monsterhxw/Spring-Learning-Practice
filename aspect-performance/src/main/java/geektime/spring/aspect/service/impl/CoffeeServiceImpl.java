package geektime.spring.aspect.service.impl;

import geektime.spring.aspect.model.Coffee;
import geektime.spring.aspect.repository.CoffeeRepository;
import geektime.spring.aspect.service.CoffeeService;
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

    @Override
    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
            .withMatcher("name", GenericPropertyMatchers.exact().ignoreCase());

        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));

        log.info("Coffee Found: {}",coffee);
        return coffee;
    }
}
