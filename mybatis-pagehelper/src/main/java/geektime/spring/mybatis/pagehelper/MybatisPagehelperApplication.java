package geektime.spring.mybatis.pagehelper;

import com.github.pagehelper.PageInfo;
import geektime.spring.mybatis.pagehelper.mapper.CoffeeMapper;
import geektime.spring.mybatis.pagehelper.model.Coffee;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("geektime.spring.mybatis.pagehelper.mapper")
@Slf4j
public class MybatisPagehelperApplication implements ApplicationRunner {

    @Autowired
    private CoffeeMapper coffeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisPagehelperApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeMapper.findAllWithRowBounds(new RowBounds(1, 3))
            .forEach(coffee -> log.info("Page(1) Coffee: {}", coffee));
        coffeeMapper.findAllWithRowBounds(new RowBounds(2, 3))
            .forEach(coffee -> log.info("Page(2) Coffee: {}", coffee));

        log.info("----------------------------------------------------");
        coffeeMapper.findAllWithRowBounds(new RowBounds(1, 0))
            .forEach(coffee -> log.info("Page(1) Coffee: {}", coffee));
        log.info("----------------------------------------------------");

        coffeeMapper.findAllWithParam(1, 3)
            .forEach(coffee -> log.info("Page(1) Coffee: {}", coffee));
        List<Coffee> coffees = coffeeMapper.findAllWithParam(2, 3);
        PageInfo pageInfo = new PageInfo(coffees);
        log.info("PageInfo: {}", pageInfo);
    }
}
