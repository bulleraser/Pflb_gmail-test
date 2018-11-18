package com.pflb.learning.stepdefinitions;

import com.pflb.learning.*;
import com.pflb.learning.entity.User;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Также;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class StepDefinitions {
    WebDriver driver = null;
    LoginPage loginPage = null;
    MainPage mainPage = null;
    StartPage startPage = null;
    MessageWidgetPage messagePage = null;
    User user;
    String to;
    String subject;
    String message;
    Logger log = LogManager.getLogger(StepDefinitions.class);

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
        driver = new ChromeDriver();
        log.info("Chrome driver started");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        log.info("Implicitly wait time is 10 seconds");
    }

    @After
    public void tearDown() {
        driver.quit();
        log.info("Driver has closed");
    }


    @Пусть("^получены данные аккаунта пользователя из файла \"([^\"]*)\"$")
    public void loadUserData(String filename) {
        try (BufferedInputStream is = new BufferedInputStream((new FileInputStream(filename)))) {
            byte[] buffer = new byte[100];
            StringBuilder sb = new StringBuilder();
            while (is.available() != 0) {
                is.read(buffer);
                sb.append(new String(buffer));
            }
            String[] data = sb.toString().split("\\ |\r\n");
            if (!(data[0].equals("login:") && data[2].equals("password:"))) {
                throw new InputMismatchException("Wrong data format in 'login.txt'");
            }
            user = new User(data[1], data[3]);
            log.info("Read login and password from file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Также("открыта страница входа в почту '(.+)'$")
    public void openStartPage(String url) {
        startPage = new StartPage(driver);
        startPage.open(url);
        log.info("Opened page " + url);
    }

    @И("^пользователь вводит свою почту$")
    public void setLogin() {
        loginPage.fillUsername(user.getUsername());
        log.info("Filled login");
    }

    @И("^пользователь вводит свой пароль$")
    public void setPassword() {
        loginPage.fillPassword(user.getPassword());
        log.info("Filled password");
    }

    @И("^нажимает кнопку Далее$")
    public void clickNext() {
        loginPage.submit();
        log.info("Clicked 'Next' button");
    }

    @Тогда("^открывается страница Входящие$")
    public void openInbox() {
        mainPage = new MainPage(driver);
        log.info("Page inbox opened");
    }

    @Также("^в правом верхнем углу видна иконка аккаунта пользователя$")
    public void checkLogin() {
        Assert.assertEquals(user.getUsername(), mainPage.getCurrentUser());
        log.info("User is logged correctly");
    }

    @И("^пользователь нажимает кнопку Войти$")
    public void clickSignIn() {
        startPage.signIn();
        log.info("Sign in button clicked");
    }

    @И("^происходит переход на страницу входа$")
    public void openLoginPage() {
        loginPage = new LoginPage(driver);
        Assert.assertEquals(loginPage.getTextSignIn(), "Вход");
        log.info("Opened login page");
    }

    @Пусть("^пользователь нажимает кнопку Написать$")
    public void composeMail() {
        mainPage.clickCompose();
        messagePage = new MessageWidgetPage(driver);
        log.info("Clicked to compose button");
    }

    @И("^пользователь вводит в поле Кому адрес получателя \"([^\"]*)\"$")
    public void fillToField(String email) {
        to = email;
        messagePage.fillTo(email);
        log.info("Filled 'to' field with email");
    }

    @И("^вводит тему письма$")
    public void fillSubjectField() {
        String symbols = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder randString = new StringBuilder();
        randString.append("Test-");
        int count = (int)(Math.random() * 30);
        for(int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        subject = randString.toString();
        messagePage.fillSubject(subject);
        log.info("Filled subject field with random text");
    }

    @И("^пользователь вводит в поле Тело письма текст письма$")
    public void fillTextField() {
        String symbols = "qwertyuiopasdfghjklzxcvbnm0123456789";
        StringBuilder randString = new StringBuilder();
        int count = (int)(Math.random() * 30);
        for(int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        message = randString.toString();
        messagePage.fillMessage(message);
        log.info("Filled mail body field with random text");
    }

    @И("^нажимает крестик$")
    public void pressSaveClose() {
        messagePage.clickSavenClose();
        log.info("Button 'Save and close' clicked");
    }

    @И("^пользователь переходит в раздел Черновики$")
    public void openDrafts() {
        mainPage.clickDrafts();
        log.info("Go to drafts");
    }

    @И("^выбирает созданное письмо в списке$")
    public void openCreatedMail() {
        List<String> subjects = mainPage.GetMailSubjects();
        Assert.assertTrue(subjects.contains(subject));
        log.info("The mail is found");
        mainPage.clickMail(subject);
        log.info("The mail on page is opened");
    }

    @Тогда("^оно то самое только что созданное письмо$")
    public void checkMailInDrafts() {
        Assert.assertEquals(messagePage.getMailBody(), message);
        log.info("This mail is saved successfully in drafts");
    }

    @И("^нажимает на кнопку Отправить$")
    public void pressSend() {
        messagePage.clickSend();
        log.info("Clicked to send this mail");
    }

    @И("^переходим в Отправленные$")
    public void openSent() {
        mainPage.clickSent();
        log.info("Go to sent");
    }

    @Тогда("^наже письмо появилось в отправленных$")
    public void checkSentMail() {
        List<String> emails = mainPage.getSentTo();
        Assert.assertTrue(emails.contains(to));
        mainPage.clickMail(subject);
        log.info("The message is added to sent list");
        Assert.assertEquals(mainPage.getSentMessage(), message);
        log.info("The message is successfully sent");
    }

    @И("^выходим из аккаунта$")
    public void logOut() {
        mainPage.clickLogOut();
        log.info("Clicked button log out under profile picture");
    }

    @Тогда("^выход произошел успешно$")
    public void checkLogOut() {
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.checkFillPassword());
        log.info("Log out successful. Loaded login page now");
    }
}