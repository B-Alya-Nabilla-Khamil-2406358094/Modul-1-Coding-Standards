package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomePageFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private WebDriver driver;
    private String baseUrl;

    @BeforeAll
    static void setDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + serverPort + "/product";
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void pageTitle_isCorrect() throws Exception {
        driver.get("http://localhost:" + serverPort + "/");

        String pageTitle = driver.getTitle();
        assertEquals("ADV Shop - Home", pageTitle);
    }

    @Test
    void welcomeMessage_homePage_isCorrect() throws Exception {
        driver.get("http://localhost:" + serverPort + "/");

        // HTML Baru menggunakan <h1> untuk welcome message
        String welcomeMessage = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Welcome to ADV Shop", welcomeMessage);
    }

    @Test
    void checkButtons_areAvailable() throws Exception {
        driver.get("http://localhost:" + serverPort + "/");

        WebElement viewProductBtn = driver.findElement(By.cssSelector("a.btn-primary"));
        assertTrue(viewProductBtn.getText().contains("View Products"));

        WebElement createProductBtn = driver.findElement(By.cssSelector("a.btn-outline-secondary"));
        assertTrue(createProductBtn.getText().contains("Add New Item"));
    }
}