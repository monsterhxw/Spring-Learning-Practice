package geektime.spring.cache.service;

import geektime.spring.cache.model.Coffee;
import java.util.List;
import java.util.Optional;

public interface CoffeeService {

    List<Coffee> findAllCoffee();

    Optional<Coffee> findOneCoffee(String name);

    void reloadCoffee();
}
