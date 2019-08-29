package geektime.spring.redis.reactive;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class RedisReactiveApplication implements ApplicationRunner {

    private static final String KEY = "COFFEE_MENU";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisReactiveApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ReactiveHashOperations<String, String, String> hashOps = reactiveRedisTemplate.opsForHash();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        List<Coffee> coffees = jdbcTemplate.query(
            "select * from t_coffee",
            (resultSet, i) -> Coffee.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getLong("price"))
                .build()
        );

        Flux.fromIterable(coffees)
            .publishOn(Schedulers.single())
            .doOnComplete(() -> log.info("CoffeeList OK"))
            .flatMap(coffee -> {
                log.info("try to put {}, {}", coffee.getName(), coffee.getPrice());
                return hashOps.put(KEY, coffee.getName(), coffee.getPrice().toString());
            })
            .doOnComplete(() -> log.info("set OK"))
            .concatWith(reactiveRedisTemplate.expire(KEY, Duration.ofMinutes(1)))
            .doOnComplete(() -> log.info("expire OK"))
            .onErrorResume(e -> {
                log.error("exception {}", e);
                return Mono.just(false);
            })
            .subscribe(
                b -> log.info("Boolean: {}", b),
                e -> log.error("Exception: {}", e),
                () -> countDownLatch.countDown()
            );
        log.info("Waiting");
        countDownLatch.await();
    }
}
