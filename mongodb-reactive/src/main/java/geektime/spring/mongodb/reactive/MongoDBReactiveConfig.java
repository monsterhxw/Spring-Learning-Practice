package geektime.spring.mongodb.reactive;

import geektime.spring.mongodb.reactive.converter.MoneyReadConverter;
import geektime.spring.mongodb.reactive.converter.MoneyWriteConverter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoDBReactiveConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter(), new MoneyWriteConverter()));
    }
}
