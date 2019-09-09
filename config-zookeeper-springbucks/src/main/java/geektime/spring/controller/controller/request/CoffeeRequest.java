package geektime.spring.controller.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.money.Money;

@Getter
@Setter
@ToString
public class CoffeeRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Money price;
}
