package geektime.spring.springbucks.service;

import geektime.spring.springbucks.model.Coffee;
import java.util.Optional;

public interface CoffeeService {

    Optional<Coffee> findOneCoffee(String name);
}
