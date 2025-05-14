import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ValidSignUpTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(654, 751));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void validSignUp() {
        driver.get("http://localhost:4000/sign_in");
        driver.manage().window().setSize(new Dimension(602, 781));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create new account"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_first_name")))
                .sendKeys("Samdani");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_last_name")))
                .sendKeys("Mozumder");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_email")))
                .sendKeys("bsse1412@iit.du.ac.bd");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password")))
                .sendKeys("iit123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password_confirmation")))
                .sendKeys("iit123");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button"))).click();
        assertThat(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span:nth-child(3)")))
                        .getText(),
                is("Samdani Mozumder")
        );
    }
}