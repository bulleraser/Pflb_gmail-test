package com.pflb.learning;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MessageWidgetPage extends AbstractPage {

    @FindBy(css = "textarea[name='to']")
    private WebElement txtTo;

    @FindBy(css = "input[name='subjectbox']")
    private WebElement txtSubject;

    //@FindBy(id = ":6w")
    @FindBy(xpath = "//tbody/tr/td[2]/div[2]/div")
    private WebElement txtMessageBody;

    @FindBy(xpath = "//div/div[4]/table/tbody/tr/td/div/div[2]")
    private WebElement btnSend;

    @FindBy(xpath = "//tbody/tr/td[2]/img[3]")
    private WebElement btnSaveNClose;

    public MessageWidgetPage(WebDriver driver) {
        super(driver);
    }

    public void fillTo(String email) {
        txtTo.sendKeys(email);
    }

    public void fillSubject(String subject) {
        txtSubject.sendKeys(subject);
        txtSubject.sendKeys(Keys.TAB);
    }

    public void fillMessage(String message) {
        driver.findElement(By.xpath("//tbody/tr/td[2]/div[2]/div")).sendKeys(message);
    }

    public void clickSavenClose() {
        btnSaveNClose.click();
    }

    public String getMailBody() {
        try {
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        }
    }

    public void clickSend() {
        btnSend.click();
    }
}
