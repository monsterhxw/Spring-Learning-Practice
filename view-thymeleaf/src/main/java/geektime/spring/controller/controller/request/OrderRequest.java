package geektime.spring.controller.controller.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequest {

    @NotEmpty
    private String customer;

    @NotEmpty
    private List<String> items;
}
