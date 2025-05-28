package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.example.pages.SignUpPage;
import org.example.pages.DashboardPage;
import java.util.*;

public class SignUpPageTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private SignUpPage signUpPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        signUpPage = new SignUpPage(driver);
        dashboardPage = new DashboardPage(driver);
        driver.manage().window().setSize(new Dimension(654, 751));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void validSignUp() {
        driver.get("http://localhost:4000/sign_up");
        driver.manage().window().setSize(new Dimension(602, 781));

        signUpPage.enterFirstName("Samdani");
        signUpPage.enterLastName("Mozumder");
        signUpPage.enterEmail("bsse1410@gmail.com");
        signUpPage.enterPassword("iit123");
        signUpPage.enterPasswordConfirmation("iit123");
        signUpPage.clickLoginButton();
        assertTrue(dashboardPage.dashboardPageDisplayed());
    }

    @Test
    public void signUpWithSpecialCharacterInName() {
        driver.get("http://localhost:4000/sign_up");
        driver.manage().window().setSize(new Dimension(602, 781));

        signUpPage.enterFirstName("Samd#ani");
        signUpPage.enterLastName("Mozum$%der");
        signUpPage.enterEmail("bsse1414@iit.du.ac.bd");
        signUpPage.enterPassword("iit123");
        signUpPage.enterPasswordConfirmation("iit123");
        signUpPage.clickLoginButton();
        assertTrue(dashboardPage.dashboardPageDisplayed());
    }

    @Test
    public void signUpWithNumberInName() {
        driver.get("http://localhost:4000/sign_up");
        driver.manage().window().setSize(new Dimension(602, 781));

        signUpPage.enterFirstName("524");
        signUpPage.enterLastName("54343");
        signUpPage.enterEmail("bsse1415@iit.du.ac.bd");
        signUpPage.enterPassword("iit123");
        signUpPage.enterPasswordConfirmation("iit123");
        signUpPage.clickLoginButton();
        assertTrue(dashboardPage.dashboardPageDisplayed());
    }

    @Test
    public void signUpWithInvalidEmail() {
        driver.get("http://localhost:4000/sign_up");
        driver.manage().window().setSize(new Dimension(602, 781));

        signUpPage.enterFirstName("Samdani");
        signUpPage.enterLastName("Mozumder");
        signUpPage.enterEmail("bsse1416@gm.com");
        signUpPage.enterPassword("iit123");
        signUpPage.enterPasswordConfirmation("iit123");
        signUpPage.clickLoginButton();
        assertTrue(dashboardPage.dashboardPageDisplayed());
    }

    @Test
    public void invalidPasswordLength() {
        driver.get("http://localhost:4000/sign_up");

        signUpPage.enterFirstName("Samdani");
        signUpPage.enterLastName("Mozumder");
        signUpPage.enterEmail("bsse1413@iit.du.ac.bd");
        signUpPage.enterPassword("iit");
        signUpPage.enterPasswordConfirmation("iit");
        signUpPage.clickLoginButton();

        assertEquals("should be at least 5 character(s)", signUpPage.getErrorMessage());
    }

    @Test
    public void passwordMismatch() {
        driver.get("http://localhost:4000/sign_up");

        signUpPage.enterFirstName("Samdani");
        signUpPage.enterLastName("Mozumder");
        signUpPage.enterEmail("bsse1415@iit.du.ac.bd");
        signUpPage.enterPassword("123456");
        signUpPage.enterPasswordConfirmation("123457");
        signUpPage.clickLoginButton();

        assertEquals("Password does not match", signUpPage.getErrorMessage());
    }
}