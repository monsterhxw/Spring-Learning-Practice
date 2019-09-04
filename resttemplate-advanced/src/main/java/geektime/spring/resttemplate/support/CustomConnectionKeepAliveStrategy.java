package geektime.spring.resttemplate.support;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class CustomConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    private final long DEFAULT_SECONDS = 30;

    @Override
    public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return Arrays.asList(httpResponse.getHeaders(HTTP.CONN_KEEP_ALIVE))
            .stream()
            .filter(header -> StringUtils.equalsIgnoreCase(header.getName(), "timeout")
                && StringUtils.isNumeric(header.getValue()))
            .findFirst()
            .map(header -> NumberUtils.toLong(header.getValue(), DEFAULT_SECONDS))
            .orElse(DEFAULT_SECONDS) * 1000;
    }

}
