package geektime.spring.resilience4j.integation;

import geektime.spring.resilience4j.model.CoffeeOrder;
import geektime.spring.resilience4j.model.OrderState;
import geektime.spring.resilience4j.model.OrderStateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @Autowired
    private CoffeeOrderSerivce orderSerivce;

    @Value("${customer.name}")
    private String customer;

    @StreamListener(Waiter.NOTIFY_ORDERS)
    public void takeOrder(@Payload Long id) {
        CoffeeOrder order = orderSerivce.getOrder(id);
        if (OrderState.BREWED == order.getState()) {
            log.info("Order: {} is READY,I'll take it.", id);
            orderSerivce.updateState(
                id,
                OrderStateRequest.builder().state(OrderState.TAKEN).build()
            );
        } else {
            log.warn("Order: {} is NOT READY.why are you notify me?", id);
        }
    }

}
