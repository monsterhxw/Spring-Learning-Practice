package geektime.spring.zk.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NewOrderRequest {

    private String customer;

    private List<String> items;
}