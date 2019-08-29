package geektime.spring.mongodb.reactive;

import geektime.spring.mongodb.reactive.model.Coffee;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class MongodbReactiveApplication implements ApplicationRunner {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

//    protected CountDownLatch cdl = new CountDownLatch(2);

    public static void main(String[] args) {
        SpringApplication.run(MongodbReactiveApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        startFromInsertion(() -> log.info("Runnable"));
        startFromInsertion(() -> {
            log.info("Runnable");
            decreaseHighPrice();
        });

        log.info("after starting");

//        decreaseHighPrice();

//        cdl.wait();
    }

    private void startFromInsertion(Runnable runnable) {
        mongoTemplate.insertAll(initCoffee())
            .publishOn(Schedulers.elastic())
            .doOnNext(c -> log.info("Next: {}", c))
            .doOnComplete(runnable)
            .doFinally(s -> {
//                cdl.countDown();
                log.info("Finally 1, {}", s);
            })
            .count()
            .subscribe(c -> log.info("Insert {} records", c));
    }

    private void decreaseHighPrice() {
        mongoTemplate.updateMulti(Query.query(Criteria.where("price").gte(3000L)),
            new Update().inc("price", -500L)
                .currentDate("updateTime"), Coffee.class)
            .doFinally(s -> {
//                cdl.countDown();
                log.info("Finally 2, {}", s);
            })
            .subscribe(r -> log.info("Result is {}", r));
    }

    private List<Coffee> initCoffee() {
        Coffee espresso = Coffee.builder()
            .name("espresso")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .createTime(new Date())
            .updateTime(new Date())
            .build();

        Coffee latte = Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .createTime(new Date())
            .updateTime(new Date())
            .build();

        return Arrays.asList(espresso, latte);
    }

}
