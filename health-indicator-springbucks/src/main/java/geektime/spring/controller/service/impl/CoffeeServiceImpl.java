package geektime.spring.controller.service.impl;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.repository.CoffeeRepository;
import geektime.spring.controller.service.CoffeeService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    @Override
    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }

    @Override
    public Coffee getCoffee(Long id) {
        return coffeeRepository.getOne(id);
    }

    @Override
    public Coffee getCoffee(String name) {
        return coffeeRepository.findByName(name);
    }

    @Override
    public Coffee saveCoffee(String name, Money price) {
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }

    @Override
    public long getCoffeeCount() {
        return coffeeRepository.count();
    }
}
