package geektime.spring.controller.service.impl;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.model.CoffeeOrder;
import geektime.spring.controller.model.OrderState;
import geektime.spring.controller.repository.CoffeeOrderRepository;
import geektime.spring.controller.service.CoffeeOrderService;
import geektime.spring.controller.support.OrderProperties;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class CoffeeOrderServiceImpl implements CoffeeOrderService, MeterBinder {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    @Autowired
    private OrderProperties orderProperties;

    private String waiterId = UUID.randomUUID().toString();

    private Counter orderCounter = null;

    @Override
    public CoffeeOrder createOrder(String customer, Coffee... coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
            .customer(customer)
            .items(Arrays.asList(coffees))
            .discount(orderProperties.getDiscount())
            .total(calcTotal(coffees))
            .state(OrderState.INIT)
            .waiter(orderProperties.getWaiterPrefix() + waiterId)
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

    private Money calcTotal(Coffee... coffees) {
        List<Money> items = Stream.of(coffees).map(coffee -> coffee.getPrice())
            .collect(Collectors.toList());
        return Money.total(items).multipliedBy(orderProperties.getDiscount()).dividedBy(100, RoundingMode.HALF_UP);
    }
}
