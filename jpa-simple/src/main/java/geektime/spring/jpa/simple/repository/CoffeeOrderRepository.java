package geektime.spring.jpa.simple.repository;

import geektime.spring.jpa.simple.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

}
