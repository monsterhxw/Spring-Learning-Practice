package geektime.spring.aspect.annotation.repository;

import geektime.spring.aspect.annotation.aspect.Log;
import geektime.spring.aspect.annotation.model.CoffeeOrder;

@Log
public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {

}
