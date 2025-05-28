package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class CardPageTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private SignInPage signInPage;
    private BoardPage boardPage;
    private CardPage cardPage;
    private ListPage listPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        signInPage = new SignInPage(driver);
        boardPage = new BoardPage(driver);
        cardPage = new CardPage(driver);
        listPage = new ListPage(driver);
        dashboardPage = new DashboardPage(driver);
        driver.manage().window().setSize(new Dimension(918, 751));

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

    public void addCard() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardCreation();
        addList();
        listPage.clickAddCardLink();
        listPage.enterCardName("Card 1");
        listPage.clickSubmitButton();
        dashboardPage.clickSignoutButton();
    }

    @Test
    public void addComment() {
        addCard();
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        cardPage.clickCardContent("1");
        cardPage.enterComment("Comment in card 1");
        cardPage.clickSubmitButton();
        assertThat(cardPage.isCommentPresent(), is(true));
    }

    @Test
    public void checkCommentTime() {
        addComment();
        assertThat(cardPage.getCommentTime(), is("6 hours ago"));
    }



    @Test
    public void editCardTitle() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        cardPage.clickCardContent("1");
        cardPage.clickEditLink();
        cardPage.enterNewTitle("Card 1 edited");
        cardPage.clickSaveButton();
        assertThat(cardPage.getUpdatedTitle(), is("Card 1 edited"));
    }

    @Test
    public void editDescription() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        cardPage.clickCardContent("1");
        cardPage.clickEditLink();
        cardPage.enterDescription("card 1 description");
        cardPage.clickSaveButton();
        assertThat(cardPage.getUpdatedDescription(), is("card 1 description"));
    }

    @Test
    public void addTag() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        cardPage.clickCardContent("1");
        cardPage.clickAddTagButton();
        cardPage.hoverBody();
        cardPage.clickGreenTag();
        assertThat(cardPage.isTagPresent(), is(true));
    }

    @Test
    public void addMember() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        cardPage.clickCardContent("1");
        cardPage.clickAddMemberButton();
        cardPage.hoverBody();
        cardPage.selectMember();
        assertThat(cardPage.isMemberPresent(), is(true));
    }

    @Test
    public void deleteCard() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();
        boardPage.clickBoardById("testing");
        String cardId = "1";
        if (cardPage.isCardPresent(cardId)) {
            cardPage.clickCardContent(cardId);
            cardPage.clickDeleteButton();
            assertThat(cardPage.isCardPresent(cardId), is(false));
        }
    }
}