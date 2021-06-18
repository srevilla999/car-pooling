package dev.carpooling.planner;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "planner.stream")
public class PlannerProperties {

    private int consumers;
    private int threadPool;
    private long pollMillis;

}
