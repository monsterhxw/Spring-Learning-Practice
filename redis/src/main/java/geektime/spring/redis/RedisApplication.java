package geektime.spring.redis;

import geektime.spring.redis.model.Coffee;
import geektime.spring.redis.service.CoffeeService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@Slf4j
public class RedisApplication implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Coffee> mocha = coffeeService.findOneCoffee("mocha");
        log.info("Coffee: {}", mocha);

        for (int i = 0; i < 5; i++) {
            mocha = coffeeService.findOneCoffee("mocha");
        }

        log.info("Value from Redis: {}", mocha);
    }
}
