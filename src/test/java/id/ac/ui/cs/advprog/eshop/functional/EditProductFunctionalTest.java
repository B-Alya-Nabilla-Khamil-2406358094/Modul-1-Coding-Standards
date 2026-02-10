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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EditProductFunctionalTest {

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
    void testEditProduct_isSuccessful() {

        driver.get(baseUrl + "/create");
        driver.findElement(By.id("nameInput")).sendKeys("Mouse Rusak");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();


        WebElement editButton = driver.findElement(By.xpath("//td[contains(text(), 'Mouse Rusak')]/following-sibling::td//a[contains(text(), 'Edit')]"));
        editButton.click();

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Mouse Logitech Bagus");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Mouse Logitech Bagus"));
        assertTrue(!pageSource.contains("Mouse Rusak"));
    }
}