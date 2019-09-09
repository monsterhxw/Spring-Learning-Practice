package geektime.spring.hystrix.model;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder {

    private Long id;

    private String customer;

    private List<Coffee> items;

    private OrderState state;

    private Date createTime;

    private Date updateTime;
}