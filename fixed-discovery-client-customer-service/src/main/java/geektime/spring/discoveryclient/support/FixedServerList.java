package geektime.spring.discoveryclient.support;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class FixedServerList implements ServerList<Server> {

    @Autowired
    private FixedDiscoveryClient discoveryClient;

    @Override
    public List<Server> getInitialListOfServers() {
        return getServers();
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return getServers();
    }

    private List<Server> getServers() {
        return discoveryClient.getInstances(FixedDiscoveryClient.SERVER_ID)
            .stream()
            .map(serviceInstance -> new Server(serviceInstance.getHost(), serviceInstance.getPort()))
            .collect(Collectors.toList());

    }
}
