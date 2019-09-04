package geektime.spring.webclient.model;

import java.io.Serializable;
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
public class Coffee implements Serializable {

    private static final long serialVersionUID = -1875296056775193409L;

    private Long id;

    private String name;

    private Money price;

    private Date createTime;

    private Date updateTime;
}
