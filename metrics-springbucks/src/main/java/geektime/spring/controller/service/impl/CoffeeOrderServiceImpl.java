package geektime.spring.controller.service.impl;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.model.CoffeeOrder;
import geektime.spring.controller.model.OrderState;
import geektime.spring.controller.repository.CoffeeOrderRepository;
import geektime.spring.controller.service.CoffeeOrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CoffeeOrderServiceImpl implements CoffeeOrderService, MeterBinder {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    private Counter orderCounter = null;

    @Override
    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
            .customer(customer)
            .items(Arrays.asList(coffees))
            .state(OrderState.INIT)
            .build();
        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order: {}", saved);
        orderCounter.increment();
        return saved;
    }

    @Override
    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            log.warn("Wrong State Order: {}, {}", state, order.getState());
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }

    @Override
    public CoffeeOrder get(Long id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.orderCounter = meterRegistry.counter("order.count");
    }
}
