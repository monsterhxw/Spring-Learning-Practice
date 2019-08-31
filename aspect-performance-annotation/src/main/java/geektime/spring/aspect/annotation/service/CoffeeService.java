package geektime.spring.aspect.annotation.service;

import geektime.spring.aspect.annotation.model.Coffee;
import java.util.Optional;

public interface CoffeeService {

    Optional<Coffee> findOneCoffee(String name);
}
