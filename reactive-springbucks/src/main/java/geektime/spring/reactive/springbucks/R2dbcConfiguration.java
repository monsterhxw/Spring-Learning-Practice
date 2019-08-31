package geektime.spring.reactive.springbucks;

import geektime.spring.reactive.springbucks.converter.LongToMoneyConverter;
import geektime.spring.reactive.springbucks.converter.MoneyToLongConverter;
import geektime.spring.reactive.springbucks.model.Coffee;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.dialect.Dialect;
import org.springframework.data.r2dbc.function.convert.R2dbcCustomConversions;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .inMemory("testdb")
                .username("sa")
                .build()
        );
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        Dialect dialect = getDialect(connectionFactory());
        CustomConversions.StoreConversions storeConversions =
            CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder());
        return new R2dbcCustomConversions(storeConversions,
            Arrays.asList(new MoneyToLongConverter(), new LongToMoneyConverter()));
    }

    @Bean
    public ReactiveRedisTemplate<String, Coffee> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Coffee> valueSerializer = new Jackson2JsonRedisSerializer<>(Coffee.class);

        RedisSerializationContextBuilder<String, Coffee> builder = RedisSerializationContext
            .newSerializationContext(keySerializer);

        RedisSerializationContext<String, Coffee> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
