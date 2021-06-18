package dev.carpooling.planner;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class PlannerConfiguration {

    @Bean(destroyMethod = "shutdownNow")
    public ScheduledExecutorService scheduledExecutorService(PlannerProperties plannerProperties) {

        return Executors.newScheduledThreadPool(plannerProperties.getThreadPool(),
                new ThreadFactoryBuilder().setNameFormat("planner-service-thread-%d").build());
    }
}
