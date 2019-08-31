package geektime.spring.aspect.service;

import geektime.spring.aspect.model.Coffee;
import geektime.spring.aspect.model.CoffeeOrder;
import geektime.spring.aspect.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
