package geektime.spring.redis.service;

import geektime.spring.redis.model.Coffee;
import java.util.List;
import java.util.Optional;

public interface CoffeeService {

    List<Coffee> findAllCoffee();

    Optional<Coffee> findOneCoffee(String name);
}
