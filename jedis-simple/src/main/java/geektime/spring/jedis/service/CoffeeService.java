package geektime.spring.jedis.service;

import geektime.spring.jedis.model.Coffee;
import java.util.List;
import java.util.Optional;

public interface CoffeeService {

    List<Coffee> findAllCoffee();

    Optional<Coffee> findOneCoffee(String name);
}
