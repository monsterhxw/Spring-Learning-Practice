package geektime.spring.aspect.annotation.service.impl;

import geektime.spring.aspect.annotation.aspect.Log;
import geektime.spring.aspect.annotation.model.Coffee;
import geektime.spring.aspect.annotation.model.CoffeeOrder;
import geektime.spring.aspect.annotation.model.OrderState;
import geektime.spring.aspect.annotation.repository.CoffeeOrderRepository;
import geektime.spring.aspect.annotation.service.CoffeeOrderService;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    @Override
    @Log
    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
            .customer(customer)
            .items(new ArrayList<>(Arrays.asList(coffees)))
            .state(OrderState.INIT)
            .build();
        CoffeeOrder coffeeOrder = orderRepository.save(order);
        log.info("New Order: {}", coffeeOrder);
        return coffeeOrder;
    }

    @Override
    @Log
    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }
}
