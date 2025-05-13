import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoardCreationTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Use WebDriverManager to set up geckodriver
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(654, 751));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void newboardcreation() {
        driver.get("http://localhost:4000/");

        // Wait for the add_new_board button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add_new_board"))).click();

        // Wait for the board_name field to be visible and interact
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("board_name"))).sendKeys("Software Security");

        // Wait for the submit button to be clickable
        // Replace cssSelector("button") with a more specific locator if possible
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();

        // Verify the board name
        assertThat(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3"))).getText(), is("Software Security"));
    }
}