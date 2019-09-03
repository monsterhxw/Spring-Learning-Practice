package geektime.spring.controller.service;

import geektime.spring.controller.model.Coffee;
import java.util.List;
import org.joda.money.Money;

public interface CoffeeService {

    List<Coffee> getAllCoffee();

    List<Coffee> getCoffeeByName(List<String> names);

    Coffee getCoffee(Long id);

    Coffee getCoffee(String name);

    Coffee saveCoffee(String name, Money price);
}