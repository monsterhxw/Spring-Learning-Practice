package geektime.spring.controller.repository;

import geektime.spring.controller.model.Coffee;
import java.util.List;

public interface CoffeeRepository extends BaseReository<Coffee, Long> {

    List<Coffee> findByNameInOrderById(List<String> names);

    Coffee findByName(String name);
}
