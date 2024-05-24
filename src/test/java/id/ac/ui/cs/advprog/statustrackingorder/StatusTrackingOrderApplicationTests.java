package id.ac.ui.cs.advprog.statustrackingorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootTest
public class StatusTrackingOrderApplicationTests {

    @Autowired
    private Executor taskExecutor;

    @Test
    void contextLoads() {
        // This test will simply attempt to start the Spring application context
    }

//    @Test
//    void testMainMethod() {
//        ConfigurableApplicationContext context = SpringApplication.run(StatusTrackingOrderApplication.class, new String[] {});
//        assertNotNull(context);
//        context.close();
//    }

    @Test
    public void testTaskExecutorConfig() {
        assertNotNull(taskExecutor);
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
        assertEquals(2, executor.getCorePoolSize());
        assertEquals(2, executor.getMaxPoolSize());
        assertEquals(500, executor.getQueueCapacity());
        assertEquals("GithubLookup-", executor.getThreadNamePrefix());
    }
}
