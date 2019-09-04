package geektime.spring.controller.repository;

import geektime.spring.controller.model.Coffee;
import java.util.List;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/coffee")
public interface CoffeeRepository extends BaseReository<Coffee, Long> {

    List<Coffee> findByNameInOrderById(List<String> names);

    Coffee findByName(String name);
}
