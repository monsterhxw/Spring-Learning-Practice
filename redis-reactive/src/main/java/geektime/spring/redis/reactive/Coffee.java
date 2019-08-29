package geektime.spring.redis.reactive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coffee {

    private Long id;

    private String name;

    private Long price;
}
