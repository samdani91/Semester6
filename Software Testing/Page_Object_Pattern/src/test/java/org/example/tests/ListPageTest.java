package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.ListPage;
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

public class ListPageTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private SignInPage signInPage;
    private DashboardPage dashboardPage;
    private BoardPage boardPage;
    private ListPage listPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        signInPage = new SignInPage(driver);
        dashboardPage = new DashboardPage(driver);
        boardPage = new BoardPage(driver);
        listPage = new ListPage(driver);
        driver.manage().window().setSize(new Dimension(838, 751));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void boardCreation() {
        dashboardPage.clickAddNewBoard();
        dashboardPage.enterBoardName("testing");
        dashboardPage.clickSubmitButton();

    }

    public void addList() {
        boardPage.clickAddListButton();
        boardPage.enterListName("list1");
        boardPage.clickSubmitButton();
    }

    @Test
    public void addCard() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();

        boardCreation();
        addList();

        listPage.clickAddCardLink();
        listPage.enterCardName("Card 1");
        listPage.clickSubmitButton();
        assertThat(listPage.getCardContent(), is("Card 1"));
    }
}