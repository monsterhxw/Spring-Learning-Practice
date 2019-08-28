package geektime.spring.mongo.repository;

import geektime.spring.mongo.model.Coffee;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoffeeRepository extends MongoRepository<Coffee, String> {

    List<Coffee> findByName(String name);
}
