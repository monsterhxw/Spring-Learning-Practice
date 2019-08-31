package geektime.spring.controller.service;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.model.CoffeeOrder;
import geektime.spring.controller.model.OrderState;

public interface CoffeeOrderService {

    CoffeeOrder createOrder(String customer, Coffee... coffees);

    boolean updateState(CoffeeOrder order, OrderState state);
}
