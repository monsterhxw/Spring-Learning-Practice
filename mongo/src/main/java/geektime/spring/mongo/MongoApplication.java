package geektime.spring.mongo;

import com.mongodb.client.result.UpdateResult;
import geektime.spring.mongo.model.Coffee;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootApplication
@Slf4j
public class MongoApplication implements ApplicationRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
            .name("espresso")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .createTime(new Date())
            .updateTime(new Date())
            .build();

        Coffee saved = mongoTemplate.save(espresso);
        log.info("Coffee: {}", saved);

        List<Coffee> coffees = mongoTemplate.find(Query.query(Criteria.where("name").is("espresso")), Coffee.class);
        log.info("Find {} Coffee", coffees.size());
        coffees.forEach(coffee -> log.info("Coffee: {}", coffee));

        // 为了看更新时间
        Thread.sleep(1000);
        UpdateResult result = mongoTemplate.updateFirst(Query.query(
            Criteria.where("name").is("espresso")),
            new Update()
                .set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30))
                .currentDate("updateTime"),
            Coffee.class);
        log.info("Update Result: {}", result.getModifiedCount());
        Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
        log.info("Update Result: {}", updateOne);

        mongoTemplate.remove(updateOne);
    }
}

