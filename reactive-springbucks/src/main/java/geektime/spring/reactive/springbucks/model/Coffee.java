package geektime.spring.reactive.springbucks.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import geektime.spring.reactive.springbucks.serializer.MoneyDeserializer;
import geektime.spring.reactive.springbucks.serializer.MoneySerializer;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("t_coffee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coffee implements Serializable {

    private static final long serialVersionUID = 4551179841300532286L;

    @Id
    private Long id;

    private String name;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money price;

    private Date createTime;

    private Date updateTime;
}
