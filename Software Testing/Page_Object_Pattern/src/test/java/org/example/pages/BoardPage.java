package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BoardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By addNewMemberButton = By.cssSelector("li > .add-new");
    private By memberEmailField = By.id("crawljax_member_email");
    private By submitButton = By.cssSelector("button");
    private By memberAvatar = By.cssSelector("li:nth-child(2) > .react-gravatar");
    private By listNameField = By.id("list_name");
    private By listTitle = By.cssSelector("h4");
    private By addListButton = By.cssSelector(".inner");

    public BoardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickBoardById(String boardId) {
        By boardLink = By.cssSelector("#\\31-" + boardId + " > .inner");
        wait.until(ExpectedConditions.elementToBeClickable(boardLink)).click();
    }

    public void clickAddNewMemberButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewMemberButton)).click();
    }

    public void enterMemberEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(memberEmailField)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(memberEmailField)).sendKeys(email);
    }

    public void clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isMemberAvatarPresent() {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(memberAvatar));
        return elements.size() > 0;
    }

    public void enterListName(String listName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(listNameField)).sendKeys(listName);
    }

    public String getListTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(listTitle)).getText();
    }

    public void clickAddListButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addListButton)).click();
    }
}