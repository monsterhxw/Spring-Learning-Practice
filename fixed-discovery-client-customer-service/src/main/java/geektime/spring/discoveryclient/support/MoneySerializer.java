package geektime.spring.discoveryclient.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.joda.money.Money;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class MoneySerializer extends StdSerializer<Money> {

    protected MoneySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money money, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(money.getAmount());
    }
}
