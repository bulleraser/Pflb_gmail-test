package com.pflb.learning;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends AbstractPage {
    private boolean nextCounter = false;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillUsername(String text) {
        WebElement txtUsername = driver.findElement(By.cssSelector("input[type=\"email\"]"));
        txtUsername.sendKeys(text);
    }

    public void fillPassword(String text) {
        try {
            WebElement txtPassword = driver.findElement(By.cssSelector("input[type=\"password\"]"));
            //wait.until(drvr -> txtPassword.isDisplayed());
            txtPassword.sendKeys(text);
        } catch(org.openqa.selenium.StaleElementReferenceException ex) {
            WebElement txtPassword = driver.findElement(By.cssSelector("input[type=\"password\"]"));
            //wait.until(drvr -> txtPassword.isDisplayed());
            txtPassword.sendKeys(text);
        }
    }

    public void submit() {
        if(nextCounter) {
            WebElement btnPasswordNext = driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span"));
            btnPasswordNext.click();
        } else {
            WebElement btnLoginNext = driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content/span"));
            btnLoginNext.click();
            nextCounter = !nextCounter;
        }
    }

    public String getTextSignIn() {
        WebElement txtSignIn = driver.findElement(By.xpath("//*[@id=\"headingText\"]/content"));
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
