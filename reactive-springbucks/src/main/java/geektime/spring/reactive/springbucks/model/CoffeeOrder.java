package geektime.spring.reactive.springbucks.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeOrder implements Serializable {

    private static final long serialVersionUID = -5689636820965662361L;

    private Long id;

    private String customer;

    private OrderState state;

    private List<Coffee> items;

    private Date createTime;

    private Date updateTime;
}
