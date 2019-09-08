package geektime.spring.discoveryclient.support;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@ConfigurationProperties("springbucks")
@Setter
public class FixedDiscoveryClient implements DiscoveryClient {

    public static final String SERVER_ID = "springbucks";

    private List<String> services;

    @Override
    public String description() {
        return "DiscoveryClient that uses service.list from application.yml";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        if (!SERVER_ID.equalsIgnoreCase(serviceId)) {
            return Collections.emptyList();
        }
        // 这里忽略了很多边界条件判断，认为就是 HOST:PORT 形式
        return services.stream()
            .map(s ->
                new DefaultServiceInstance(
                    s,
                    SERVER_ID,
                    s.split(":")[0],
                    Integer.parseInt(s.split(":")[1]),
                    false)
            )
            .collect(Collectors.toList());

    }

    @Override
    public List<String> getServices() {
        return Collections.singletonList(SERVER_ID);
    }

}
