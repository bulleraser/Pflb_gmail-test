package com.pflb.learning;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: " + alertText);
                alert.accept();
                wait.until(ExpectedConditions.urlContains("signin"));
                WebElement passwordField = driver.findElement(By.cssSelector("input[type=\"password\"]"));
                wait.until(drvr -> passwordField.isDisplayed());
                return true;
            } catch (NoAlertPresentException e) {
                wait.until(ExpectedConditions.urlContains("signin"));
                WebElement passwordField = driver.findElement(By.cssSelector("input[type=\"password\"]"));
                wait.until(drvr -> passwordField.isDisplayed());
                return true;
            }
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
