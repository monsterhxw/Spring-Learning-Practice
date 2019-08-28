package geektime.spring.mongo;

import geektime.spring.mongo.model.Coffee;
import geektime.spring.mongo.repository.CoffeeRepository;
import java.util.Arrays;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@Slf4j
public class MongoRepositoryApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoRepositoryApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
            .name("espresso")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .createTime(new Date())
            .updateTime((new Date()))
            .build();
        Coffee latte = Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .createTime(new Date())
            .updateTime((new Date()))
            .build();

        coffeeRepository.insert(Arrays.asList(espresso, latte));

        coffeeRepository.findAll(Sort.by("name"))
            .forEach(coffee -> log.info("Saved Coffee: {}", coffee));

        // 为了看更新时间
        Thread.sleep(1000);
        latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
        latte.setUpdateTime(new Date());
        coffeeRepository.save(latte);
        coffeeRepository.findByName("latte")
            .forEach(coffee -> log.info("Updated Coffee: {}", coffee));

        coffeeRepository.deleteAll();
    }
}
