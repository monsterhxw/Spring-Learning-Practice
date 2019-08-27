package geektime.spring.mybatis.generator;

import geektime.spring.mybatis.generator.mapper.CoffeeMapper;
import geektime.spring.mybatis.generator.model.Coffee;
import geektime.spring.mybatis.generator.model.CoffeeExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("geektime.spring.mybatis.generator.mapper")
public class MybatisGeneratorApplication implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        generateArtifacts();
        playWithArtifacts();
    }

    private void playWithArtifacts() {
        Coffee espresso = new Coffee()
            .withName("espresso")
            .withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .withCreateTime(new Date())
            .withUpdateTime(new Date());
        coffeeMapper.insert(espresso);

        Coffee latte = new Coffee()
            .withName("latte")
            .withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .withCreateTime(new Date())
            .withUpdateTime(new Date());
        coffeeMapper.insert(latte);

        Coffee coffee = coffeeMapper.selectByPrimaryKey(1L);
        log.info("Coffee {}", coffee);

        CoffeeExample coffeeExample = new CoffeeExample();
        coffeeExample.createCriteria().andNameEqualTo("latte");
        coffeeExample.setOrderByClause("id desc");

        List<Coffee> coffees = coffeeMapper.selectByExample(coffeeExample);
        coffees.forEach(c -> log.info("selectByExample: {}", c));
    }

    private void generateArtifacts() throws Exception {
        List<String> warings = new ArrayList<>();
        ConfigurationParser configurationParser = new ConfigurationParser(warings);
        Configuration configuration = configurationParser.parseConfiguration(this.getClass().getResourceAsStream("/generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warings);
        myBatisGenerator.generate(null);
    }
}
