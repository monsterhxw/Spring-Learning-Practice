package geektime.spring.jpa.simple.repository;

import geektime.spring.jpa.simple.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

}
