package geektime.spring.druid;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectionLogFilter extends FilterEventAdapter {

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.info("Before Connection!");
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.info("After Connection!");
    }
}
