package geektime.spring.hystrix.integation;

import geektime.spring.hystrix.model.Coffee;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "springbucks-service", contextId = "coffee", path = "/coffee", qualifier = "coffeeService", fallback = FallbackCoffeeService.class)
// 如果用了Fallback，不要在接口上加@RequestMapping，path可以用在这里
public interface CoffeeService {

    @GetMapping(params = "!name")
    List<Coffee> getAll();

    @GetMapping(path = "/{id}")
    Coffee getById(@PathVariable Long id);

    @GetMapping(params = "name")
    Coffee getByName(@RequestParam String name);
}
