package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.example.pages.SignInPage;
import org.example.pages.DashboardPage;
import org.example.pages.BoardPage;
import java.util.*;

public class AddListTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private SignInPage signInPage;
    private DashboardPage dashboardPage;
    private BoardPage boardPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        signInPage = new SignInPage(driver);
        dashboardPage = new DashboardPage(driver);
        boardPage = new BoardPage(driver);
        driver.manage().window().setSize(new Dimension(838, 751));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addList() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        boardPage.clickAddListButton();
        boardPage.enterListName("123");
        boardPage.clickSubmitButton();
        assertThat(boardPage.getListTitle(), is("123"));
    }
}