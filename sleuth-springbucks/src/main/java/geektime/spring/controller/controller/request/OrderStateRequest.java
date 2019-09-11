package geektime.spring.controller.controller.request;

import geektime.spring.controller.model.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStateRequest {

    private OrderState state;
}
