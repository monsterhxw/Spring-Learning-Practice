package geektime.spring.resilience4j.scheduler;

import geektime.spring.resilience4j.integation.CoffeeOrderSerivce;
import geektime.spring.resilience4j.model.CoffeeOrder;
import geektime.spring.resilience4j.model.OrderState;
import geektime.spring.resilience4j.model.OrderStateRequest;
import geektime.spring.resilience4j.support.OrderWaitingEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoffeeOrderScheduler {

    @Autowired
    private CoffeeOrderSerivce coffeeOrderSerivce;

    private Map<Long, CoffeeOrder> orderMap = new ConcurrentHashMap<>();

    @EventListener
    public void acceptOrder(OrderWaitingEvent event) {
        orderMap.put(event.getOrder().getId(), event.getOrder());
    }

    @Scheduled(fixedRate = 1000)
    public void waitForCoffee() {
        if (orderMap.isEmpty()) {
            return;
        }
        log.info("I'm waiting for my coffee.");
        orderMap.values().stream()
            .map(order -> coffeeOrderSerivce.getOrder(order.getId()))
            .filter(order -> OrderState.BREWED == order.getState())
            .forEach(order -> {
                log.info("Order [{}] is READY,I'll take it.", order);
                coffeeOrderSerivce.updateState(
                    order.getId(),
                    OrderStateRequest.builder().state(OrderState.TAKEN).build()
                );
                orderMap.remove(order.getId());
            });
    }

}
