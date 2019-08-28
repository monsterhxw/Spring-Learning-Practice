package geektime.spring.redis.service;

import geektime.spring.redis.model.Coffee;
import geektime.spring.redis.model.CoffeeOrder;
import geektime.spring.redis.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
