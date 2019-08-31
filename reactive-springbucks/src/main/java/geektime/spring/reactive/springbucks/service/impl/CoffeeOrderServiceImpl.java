package geektime.spring.reactive.springbucks.service.impl;

import geektime.spring.reactive.springbucks.model.CoffeeOrder;
import geektime.spring.reactive.springbucks.repository.CoffeeOrderRepository;
import geektime.spring.reactive.springbucks.service.CoffeeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<Long> create(CoffeeOrder order) {
        return coffeeOrderRepository.save(order);
    }
}
