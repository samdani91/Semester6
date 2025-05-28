package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.SignUpPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.example.pages.BoardPage;
import org.example.pages.DashboardPage;
import org.example.pages.SignInPage;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardPageTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    private JavascriptExecutor js;
    private SignInPage signInPage;
    private SignUpPage signUpPage;
    private DashboardPage dashboardPage;
    private BoardPage boardPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        signInPage = new SignInPage(driver);
        signUpPage = new SignUpPage(driver);
        dashboardPage = new DashboardPage(driver);
        boardPage = new BoardPage(driver);
        driver.manage().window().setSize(new Dimension(602, 1063));
        vars = new HashMap<>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void signIn(){
        signInPage.enterEmail("john@phoenix-trello.com");
        signInPage.enterPassword("12345678");
        signInPage.clickLoginButton();
    }

    public void signUp(){
        driver.get("http://localhost:4000/sign_up");
        signUpPage.enterFirstName("Samdani");
        signUpPage.enterLastName("Mozumder");
        signUpPage.enterEmail("bsse1412@iit.du.ac.bd");
        signUpPage.enterPassword("iit123");
        signUpPage.enterPasswordConfirmation("iit123");
        signUpPage.clickLoginButton();
        dashboardPage.clickSignoutButton();
    }

    public void boardCreation() {
        dashboardPage.clickAddNewBoard();
        dashboardPage.enterBoardName("Testing");
        dashboardPage.clickSubmitButton();

    }

    @Test
    public void addMemberToBoard() {
        driver.get("http://localhost:4000/");

        signUp();
        signIn();
        boardCreation();

        boardPage.clickAddNewMemberButton();
        boardPage.enterMemberEmail("bsse1412@iit.du.ac.bd");
        boardPage.clickSubmitButton();

        assertTrue(boardPage.isMemberAvatarPresent());
    }

    @Test
    public void addNonExistingUserToBoard() {
        driver.get("http://localhost:4000/");

        signIn();
        boardCreation();

        boardPage.clickAddNewMemberButton();
        boardPage.enterMemberEmail("bsse@gmail.com");
        boardPage.clickSubmitButton();

        assertEquals("User does not exist", boardPage.getErrorMessage());
    }

    @Test
    public void addUserToBoard_emptyEmail() {
        driver.get("http://localhost:4000/");

        signIn();
        boardCreation();

        boardPage.clickAddNewMemberButton();
        boardPage.enterMemberEmail("");
        boardPage.clickSubmitButton();

        assertThat(boardPage.getEmailFieldValidationMessage(), is("Please fill out this field."));
    }

    @Test
    public void addUserToBoard_invalidEmailFormat() {
        driver.get("http://localhost:4000/");

        signIn();
        boardCreation();

        boardPage.clickAddNewMemberButton();
        boardPage.enterMemberEmail("bsse@.com");
        boardPage.clickSubmitButton();

        assertThat(boardPage.getEmailFieldValidationMessage(), is("Please enter an email address."));
    }

    @Test
    public void addList() {
        driver.get("http://localhost:4000/sign_in");
        signInPage.clickLoginButton();

        boardCreation();

        boardPage.clickAddListButton();
        boardPage.enterListName("list1");
        boardPage.clickSubmitButton();
        assertThat(boardPage.getListTitle(), is("list1"));
    }
}