package id.ac.ui.cs.advprog.statustrackingorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootTest
class StatusTrackingOrderApplicationTests {

    @Autowired
    private Executor taskExecutor;


    @Test
    void testTaskExecutorConfig() {
        assertNotNull(taskExecutor);
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) taskExecutor;
        assertEquals(2, executor.getCorePoolSize());
        assertEquals(2, executor.getMaxPoolSize());
        assertEquals(500, executor.getQueueCapacity());
        assertEquals("GithubLookup-", executor.getThreadNamePrefix());
    }
}
