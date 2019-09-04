package geektime.spring.springbucks.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder {

    private Long id;

    private String customer;

    private List<Coffee> items;

    private OrderState state;

    private Date createTime;

    private Date updateTime;
}
