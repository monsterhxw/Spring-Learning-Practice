package geektime.spring.redis.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "springbucks-coffee", timeToLive = 60)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeCache implements Serializable {

    private static final long serialVersionUID = 8414275388082355665L;

    @Id
    private Long id;

    @Indexed
    private String name;

    private Money price;
}
