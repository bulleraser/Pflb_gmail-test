package com.pflb.learning;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    @FindBy(css = "input[type=\"email\"]")
    private WebElement txtUsername;

    @FindBy(css = "input[type=\"password\"]")
    private WebElement txtPassword;

    private boolean nextCounter = false;
    @FindBy(xpath = "//*[@id=\"passwordNext\"]/content/span")
    private WebElement btnPasswordNext;

    @FindBy(xpath = "//*[@id=\"identifierNext\"]/content/span")
    private WebElement btnLoginNext;

    @FindBy(xpath = "//*[@id=\"headingText\"]/content")
    private WebElement txtSignIn;

    @FindBy(xpath = "//*[@id=\"headingText\"]/content")
    private WebElement txtSignOut;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillUsername(String text) {
        txtUsername.sendKeys(text);
    }

    public void fillPassword(String text) {
        wait.until(drvr -> txtPassword.isDisplayed());
        txtPassword.sendKeys(text);
    }

    public void submit() {
        if(nextCounter) {
            btnPasswordNext.click();
        } else {
            btnLoginNext.click();
            nextCounter = !nextCounter;
        }
    }

    public String getTextSignIn() {
        return txtSignIn.getText();
    }

    public boolean checkFillPassword() {
        try {
            WebElement passwordField = driver.findElement(By.cssSelector("input[type=\"password\"]"));
            wait.until(drvr -> passwordField.isDisplayed());
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
