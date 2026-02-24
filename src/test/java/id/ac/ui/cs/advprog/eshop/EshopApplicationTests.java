package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
class EshopApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {

        assertNotNull(applicationContext, "The application context should have loaded.");
    }

    @Test
    void testMain() {

        EshopApplication.main(new String[]{});
        assertTrue(true, "Main method executed successfully");
    }
}
