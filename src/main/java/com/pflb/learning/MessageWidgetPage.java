package com.pflb.learning;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MessageWidgetPage extends AbstractPage {

    public MessageWidgetPage(WebDriver driver) {
        super(driver);
    }

    public void fillTo(String email) {
        WebElement txtTo = driver.findElement(By.cssSelector("textarea[name='to']"));
        txtTo.sendKeys(email);
    }

    public void fillSubject(String subject) {
        WebElement txtSubject = driver.findElement(By.cssSelector("input[name='subjectbox']"));
        txtSubject.sendKeys(subject);
        txtSubject.sendKeys(Keys.TAB);
    }

    public void fillMessage(String message) {
            driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys(message);
    }

    public void clickSavenClose() {
        WebElement btnSaveNClose = driver.findElement(By.xpath("//img[@src='images/cleardot.gif'][3]"));
        btnSaveNClose.click();
    }

    public String getMailBody() {
        WebElement txtMessageBody = driver.findElement(By.xpath("//div[@role='textbox']"));
        try {
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        }
    }

    public void clickSend() {
        WebElement btnSend = driver.findElement(By.xpath("//div[@role='button'and@tabindex='1'and@data-tooltip-delay]"));
        btnSend.click();
    }
}
