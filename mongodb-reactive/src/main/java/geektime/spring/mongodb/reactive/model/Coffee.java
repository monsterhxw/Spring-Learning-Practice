package geektime.spring.mongodb.reactive.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {

    private String id;

    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;
}
