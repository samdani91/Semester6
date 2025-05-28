package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By commentTextarea = By.cssSelector("textarea");
    private By submitButton = By.cssSelector("button");
    private By commentText = By.cssSelector(".text");
    private By commentTime = By.xpath("//div[@id='authentication_container']/div/div/div[2]/div/div/div/div[2]/div/div[2]/small");
    private By editLink = By.linkText("Edit");
    private By titleInput = By.cssSelector("input");
    private By descriptionTextarea = By.cssSelector("textarea:nth-child(2)");
    private By saveButton = By.cssSelector("button:nth-child(3)");
    private By updatedTitle = By.cssSelector(".info h3");
    private By updatedDescription = By.cssSelector("p");
    private By addTagButton = By.cssSelector(".button:nth-child(3) > span");
    private By greenTag = By.cssSelector(".green");
    private By cardTags = By.cssSelector(".card-tags");
    private By deleteButton = By.cssSelector(".fa-trash-o");
    private By addMemberButton = By.cssSelector(".button:nth-child(2) > span");
    private By memberOption = By.cssSelector("ul:nth-child(2) span:nth-child(2)");
    private By cardMembers = By.cssSelector(".card-members");

    public CardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By getCardContentLocator(String cardId) {
        return By.cssSelector(".card-content > span");
    }

    public boolean isCardPresent(String cardId) {
        By cardLocator = getCardContentLocator(cardId);
        List<WebElement> elements = driver.findElements(cardLocator);
        return !elements.isEmpty();
    }

    public void clickCardContent(String cardId) {
        By cardLocator = getCardContentLocator(cardId);
        wait.until(ExpectedConditions.elementToBeClickable(cardLocator)).click();
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextarea)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextarea)).sendKeys(comment);
    }

    public void clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isCommentPresent() {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(commentText));
        return !elements.isEmpty();
    }

    public void clickEditLink() {
        wait.until(ExpectedConditions.elementToBeClickable(editLink)).click();
    }

    public void enterNewTitle(String title) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput));
        input.clear();
        input.sendKeys(title);
    }

    public void enterDescription(String description) {
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionTextarea));
        textarea.click();
        textarea.clear();
        textarea.sendKeys(description);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public String getUpdatedTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(updatedTitle)).getText();
    }

    public String getCommentTime(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(commentTime)).getText();
    }

    public String getUpdatedDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(updatedDescription)).getText();
    }

    public void clickAddTagButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addTagButton));
        element.click();
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }

    public void hoverBody() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        Actions builder = new Actions(driver);
        builder.moveToElement(element, 0, 0).perform();
    }

    public void clickGreenTag() {
        wait.until(ExpectedConditions.elementToBeClickable(greenTag)).click();
    }

    public boolean isTagPresent() {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardTags));
        return !elements.isEmpty();
    }

    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }

    public void clickAddMemberButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addMemberButton));
        element.click();
        Actions builder = new Actions(driver);
        builder.moveToElement(element).perform();
    }

    public void selectMember() {
        wait.until(ExpectedConditions.elementToBeClickable(memberOption)).click();
    }

    public boolean isMemberPresent() {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cardMembers));
        return !elements.isEmpty();
    }
}