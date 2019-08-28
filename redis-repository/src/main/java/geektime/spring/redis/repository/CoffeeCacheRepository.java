package geektime.spring.redis.repository;

import geektime.spring.redis.model.CoffeeCache;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {

    Optional<CoffeeCache> findOneByName(String name);
}
