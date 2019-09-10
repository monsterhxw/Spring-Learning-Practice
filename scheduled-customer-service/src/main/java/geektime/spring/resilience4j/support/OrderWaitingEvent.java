package geektime.spring.resilience4j.support;

import geektime.spring.resilience4j.model.CoffeeOrder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class OrderWaitingEvent extends ApplicationEvent {

    private CoffeeOrder order;

    public OrderWaitingEvent(CoffeeOrder order) {
        super(order);
        this.order = order;
    }


}
