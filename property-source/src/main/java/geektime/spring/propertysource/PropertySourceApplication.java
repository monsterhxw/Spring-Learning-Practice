package geektime.spring.propertysource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PropertySourceApplication implements ApplicationRunner {

    @Value("${geektime.greeting}")
    private String greeting;

    public static void main(String[] args) {
        SpringApplication.run(PropertySourceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("{}", greeting);

    }
}
