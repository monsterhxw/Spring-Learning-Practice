package geektime.spring.cache.service;

import geektime.spring.cache.model.Coffee;
import geektime.spring.cache.model.CoffeeOrder;
import geektime.spring.cache.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
