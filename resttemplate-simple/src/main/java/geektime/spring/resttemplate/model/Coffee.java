package geektime.spring.resttemplate.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {

    private static final long serialVersionUID = -1875296056775193409L;

    private Long id;

    private String name;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;
}
