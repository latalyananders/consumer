package RabbitMQ;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@EnableAutoConfiguration
@ComponentScan
@Import(RabbitConfiguration.class)
public class Application {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "0"));
        app.run(args);
    }
}