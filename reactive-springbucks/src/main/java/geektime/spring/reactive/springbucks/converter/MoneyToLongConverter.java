package geektime.spring.reactive.springbucks.converter;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyToLongConverter implements Converter<Money,Long> {

    @Override
    public Long convert(Money source) {
        return source.getAmountMinorLong();
    }
}
