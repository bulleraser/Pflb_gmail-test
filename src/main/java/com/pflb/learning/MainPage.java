package com.pflb.learning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AbstractPage {

    @FindBy(xpath = "//*[starts-with(@href,'https://accounts.google.com/SignOut')]")
    private WebElement profileContainer;

    @FindBy(xpath = "(//div[2]/div/div/div/div/div/*[@role='button'])[3]")
    private WebElement btnCompose;

    @FindBy(xpath = "//div[@role='main']//tr[@jsaction]")
    private WebElement tblMails;

    @FindBy(xpath = "//div[3]/div[1]/div[1]/div/div/div[5]/div")
    private WebElement btnRefresh;

    public MainPage(WebDriver driver) {
        super(driver);
        wait.until(drvr -> profileContainer.isDisplayed());
    }

    public String getCurrentUser() {
        String string = profileContainer.getAttribute("aria-label");
        return string.substring(string.indexOf('(') + 1, string.indexOf(')'));
    }

    public void clickCompose() {
        btnCompose.click();
    }

    public void clickDrafts() {
        try {
            WebElement btnDrafts = driver.findElement(By.xpath("//a[contains(@href,'#drafts')]"));
            btnDrafts.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("#drafts"));
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            WebElement btnDrafts = driver.findElement(By.xpath("//a[contains(@href,'#drafts')]"));
            btnDrafts.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("#drafts"));
        }
    }


    public List<String> GetMailSubjects() {
        List<WebElement> subjects = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]//td/div/div/div/span/span"));
        List<WebElement> subjectsButtons = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]"));
        List<String> subjectsStr = new ArrayList<>();
        for(WebElement subject: subjects) {
            subjectsStr.add(subject.getText());
        }
        return subjectsStr;
    }

    public void clickMail(String subject) {
        List<WebElement> subjects;
        List<WebElement> subjectsButtons;
        List<String> subjectsStr = new ArrayList<>();
        try {
            subjects = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]//td/div/div/div/span/span"));
            subjectsButtons = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]"));
            for(WebElement subj: subjects) {
                subjectsStr.add(subj.getText());
            }
        } catch(org.openqa.selenium.StaleElementReferenceException ex) {
            subjects = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]//td/div/div/div/span/span"));
            subjectsButtons = driver.findElements(By.xpath("//div[@role='main']//tr[@jsaction]"));
            for (WebElement subj : subjects) {
                subjectsStr.add(subj.getText());
            }
        }

        int i = subjectsStr.indexOf(subject);
        subjectsButtons.get(i).click();
    }

    public void clickSent() {
        try {
            WebElement btnSent = driver.findElement(By.xpath("//a[contains(@href,'#sent')]"));
            btnSent.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("#sent"));
        } catch(org.openqa.selenium.StaleElementReferenceException ex) {
            WebElement btnSent = driver.findElement(By.xpath("//a[contains(@href,'#sent')]"));
            WebElement oldTblMail = driver.findElement(By.xpath("//div[@role='main' and not(contains(@style,'display:none'))]"));
            btnSent.click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlContains("#sent"));
            wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(oldTblMail)));
        }
    }

    public List<String> getSentTo() {
        List<WebElement> txtToSend;
        try {
            txtToSend = driver.findElements(By.xpath("//div/span[@email]"));
        } catch(org.openqa.selenium.StaleElementReferenceException ex) {
            txtToSend = driver.findElements(By.xpath("//div/span[@email]"));
        }
        List<String> sendStr = new ArrayList<>();
        for(WebElement send: txtToSend) {
            sendStr.add(send.getAttribute("email"));
        }
        return sendStr;
    }

    public String getSentMessage() {
        try {
            WebElement txtMessage = driver.findElement(By.xpath("(//*[@role='gridcell'])[3]/div"));
            wait.until(drvr -> txtMessage.isDisplayed());
            return txtMessage.getText();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            WebElement txtMessage = driver.findElement(By.xpath("(//*[@role='gridcell'])[3]/div"));
            wait.until(drvr -> txtMessage.isDisplayed());
            return txtMessage.getText();
        }
    }

    public void clickLogOut() {
        profileContainer.click();
        WebElement btnQuit = driver.findElement(By.xpath("//*[starts-with(@href,'https://accounts.google.com/Logout')]"));
        wait.until(drvr -> btnQuit.isDisplayed());
        btnQuit.click();
    }
}
