package com.pflb.learning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbstractPage {

    @FindBy(css = "[aria-label^=\"Аккаунт Google:\"]")
    private WebElement profileContainer;

    @FindBy(xpath = ".//*[text()='Написать']/..")
    private WebElement btnCompose;

    @FindBy(css = "textarea[name=\"to\"]")
    private WebElement txtTo;

    @FindBy(css = "input[name=\"subjectbox\"]")
    private WebElement txtSubject;

    @FindBy(xpath = "(.//*[@aria-label='Тело письма'])[2]")
    private WebElement txtMessageBody;

    @FindBy(css = "[aria-label^=\"Отправить\"]")
    private WebElement btnSend;

    @FindBy(css = "[aria-label^=\"Сохранить и закрыть\"]")
    private WebElement btnSaveNClose;

    @FindBy(css = "[title^=\"Черновики\"]")
    private WebElement btnDrafts;
//
//    @FindBy(xpath = "//*[@id=\":9j\"]/div/div[2]/div")
//    private WebElement elementDraftsCounter;

  //  private int draftsCounter;

    @FindBy(xpath = "//table/tbody/tr[1]")
    private WebElement btnFirstMail;

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

    public void fillTo(String email) {
        txtTo.sendKeys(email);
    }

    public void fillSubject(String subject) {
        txtSubject.sendKeys(subject);
    }

    public void fillMessage(String message) {
        txtMessageBody.click();
        txtMessageBody.sendKeys(message);
    }

    public void clickSavenClose() {
//        if (elementDraftsCounter.isDisplayed()) {
//            draftsCounter = Integer.parseInt(elementDraftsCounter.getText());
//        } else {
//            draftsCounter = 0;
//        }
        //System.out.println(draftsCounter);
        btnSaveNClose.click();
    }

    public void clickDrafts() {
        //driver.findElement(By.cssSelector("[title^=\"Черновики\"]")).click();
        driver.get("https://mail.google.com/mail/u/0/#drafts");
    }

    public void clickFirstMail(String subject) {
        StringBuilder sb = new StringBuilder();
        sb.append("//*[text()='");
        sb.append(subject);
        sb.append("']/../../..");
        try {
            WebElement fmail = driver.findElement(By.xpath(sb.toString()));
            fmail.click();
        }
        catch(org.openqa.selenium.StaleElementReferenceException ex)
        {
            WebElement fmail = driver.findElement(By.xpath(sb.toString()));
            fmail.click();
        }
    }

    public String getMailBody() {
        try {
            txtMessageBody = driver.findElement(By.xpath("(.//*[@aria-label='Тело письма'])[2]"));
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            txtMessageBody = driver.findElement(By.xpath("(.//*[@aria-label='Тело письма'])[2]"));
            wait.until(drvr -> txtMessageBody.isDisplayed());
            return txtMessageBody.getText();
        }
    }

    public void clickSend() {
        btnSend = driver.findElement(By.cssSelector("[aria-label^='Отправить']"));
        btnSend.click();
    }

    public void clickSent() {
        driver.get("https://mail.google.com/mail/u/0/#sent");
    }

    public String getSentTo() {
        WebElement txtToSend = driver.findElement(By.xpath("//*[text()='Кому: ']/span"));
        return txtToSend.getText();
    }

    public String getSentSubject() {
        WebElement txtSubjectSend = driver.findElement(By.xpath("//*[text()='Кому: ']/../../td[6]//span/span"));
        return txtSubjectSend.getText();
    }

    public String getSentMessage() {
        driver.findElement(By.xpath("//*[text()='Кому: ']")).click();
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
        driver.findElement(By.cssSelector("[aria-label^=\"Аккаунт Google:\"]")).click();
        WebElement btnQuit = driver.findElement(By.xpath(".//*[text()='Выйти']"));
        wait.until(drvr -> btnQuit.isDisplayed());
        btnQuit.click();
    }
}
