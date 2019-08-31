package geektime.spring.reactive.springbucks.repository;

import geektime.spring.reactive.springbucks.model.CoffeeOrder;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CoffeeOrderRepository {

    @Autowired
    private DatabaseClient databaseClient;

    public Mono<Long> save(CoffeeOrder order) {
        return databaseClient
            .insert()
            .into("t_order")
            .value("customer", order.getCustomer())
            .value("state", order.getState().ordinal())
            .value("create_time", new Timestamp(order.getCreateTime().getTime()))
            .value("update_time", new Timestamp(order.getUpdateTime().getTime()))
            .fetch()
            .first()
            .flatMap(m -> Mono.just((Long) m.get("ID")))
            .flatMap(id -> Flux.fromIterable(order.getItems())
                .flatMap(c -> databaseClient
                    .insert()
                    .into("t_order_coffee")
                    .value("coffee_order_id", id)
                    .value("items_id", c.getId())
                    .then())
                .then(Mono.just(id)));
    }
}
