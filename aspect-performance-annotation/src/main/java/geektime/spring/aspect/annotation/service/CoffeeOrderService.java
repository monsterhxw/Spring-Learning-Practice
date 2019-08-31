package geektime.spring.aspect.annotation.service;

import geektime.spring.aspect.annotation.model.Coffee;
import geektime.spring.aspect.annotation.model.CoffeeOrder;
import geektime.spring.aspect.annotation.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
