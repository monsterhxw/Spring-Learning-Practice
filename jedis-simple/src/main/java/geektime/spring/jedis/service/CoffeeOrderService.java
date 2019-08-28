package geektime.spring.jedis.service;

import geektime.spring.jedis.model.Coffee;
import geektime.spring.jedis.model.CoffeeOrder;
import geektime.spring.jedis.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
