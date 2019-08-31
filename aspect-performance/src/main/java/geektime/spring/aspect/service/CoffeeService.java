package geektime.spring.aspect.service;

import geektime.spring.aspect.model.Coffee;
import java.util.Optional;

public interface CoffeeService {

    Optional<Coffee> findOneCoffee(String name);
}
