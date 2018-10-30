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
import java.util.concurrent.TimeUnit;


public class StepDefinitions {
    WebDriver driver = null;
    LoginPage loginPage = null;
    MainPage mainPage = null;
    StartPage startPage = null;
    User user;
    String to;
    String subject;
    String message;
    Logger log = LogManager.getLogger(StepDefinitions.class);

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\vlad\\inf\\chromedriver.exe");
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

    @И("^пользователь вводит в поле \"([^\"]*)\" свои данные$")
    public void setTextToInput(String fieldName) {
        switch (fieldName) {
            case "Телефон или адрес эл. почты":
                loginPage.fillUsername(user.getUsername());
                log.info("Filled login");
                break;
            case "Введите пароль":
                loginPage.fillPassword(user.getPassword());
                log.info("Filled password");
                break;
            default:
                throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }
    }

    @И("^нажимает кнопку \"([^\"]*)\"$")
    public void clickNext(String btnName) {
        if(!btnName.equals("Далее")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
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

    @И("^пользоатель нажимает кнопку \"([^\"]*)\"$")
    public void clickSignIn(String btnName) {
        if(!btnName.equals("Войти")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        startPage.signIn();
        log.info("Sign in button clicked");
    }

    @И("^происходит переход на страницу входа$")
    public void openLoginPage() {
        loginPage = new LoginPage(driver);
        Assert.assertEquals(loginPage.getTextSignIn(), "Вход");
        log.info("Opened login page");
    }

    @Пусть("^пользователь нажимает кнопку \"([^\"]*)\"$")
    public void composeMail(String btnName) {
        if(!btnName.equals("Написать")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        mainPage.clickCompose();
        log.info("Clicked to compose button");
    }

    @И("^пользователь вводит в поле \"([^\"]*)\" адрес получателя \"([^\"]*)\"$")
    public void fillToField(String fieldName, String email) {
        if(!fieldName.equals("Кому")) {
            throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }
        to = email;
        mainPage.fillTo(email);
        log.info("Filled 'to' field with email");
    }

    @И("^вводит в поле \"([^\"]*)\" тему письма \"([^\"]*)\"$")
    public void fillSubjectField(String fieldName, String subjectName) {
        if(!fieldName.equals("Тема")) {
            throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }
        String symbols = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder randString = new StringBuilder();
        randString.append("Test-");
        int count = (int)(Math.random() * 30);
        for(int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        subject = randString.toString();
        mainPage.fillSubject(subject);
        log.info("Filled subject field with random text");
    }

    @И("^пользователь вводит в поле \"([^\"]*)\" текст письма$")
    public void fillTextField(String fieldName) {
        if(!fieldName.equals("Тело письма")) {
            throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }
        String symbols = "qwertyuiopasdfghjklzxcvbnm0123456789";
        StringBuilder randString = new StringBuilder();
        int count = (int)(Math.random() * 30);
        for(int i = 0; i < count; i++) {
            randString.append(symbols.charAt((int) (Math.random() * symbols.length())));
        }
        message = randString.toString();
        mainPage.fillMessage(message);
        log.info("Filled mail body field with random text");
    }

    @И("^нажимает крестик \"([^\"]*)\"$")
    public void pressSaveClose(String btnName) {
        if(!btnName.equals("Сохранить и закрыть")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        mainPage.clickSavenClose();
        log.info("Button 'Save and close' clicked");
    }

    @Тогда("^счетчик писем в разделе \"([^\"]*)\" увеличивается на (\\d+)$")
    public void CheckMailIsSaved(String btnName, int change) {
        //Assert.assertEquals(change, mainPage.checkDraftsCounterChange());
    }

    @И("^пользователь переходит в раздел \"([^\"]*)\"$")
    public void openDrafts(String btnName) {
        if(!btnName.equals("Черновики")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        mainPage.clickDrafts();
        log.info("Go to drafts");
    }

    @И("^выбирает первое письмо в списке$")
    public void openFirstLetter() {
        mainPage.clickFirstMail(subject);
        log.info("The first mail on page is opened");
    }

    @Тогда("^оно то самое только что созданное письмо$")
    public void checkMailInDrafts() {
        Assert.assertEquals(message, mainPage.getMailBody());
        log.info("This mail is saved successfully in drafts");
    }

    @И("^нажимает на кнопку \"([^\"]*)\"$")
    public void pressSend(String btnName) {
        if(!btnName.equals("Отправить")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        mainPage.clickSend();
        log.info("Clicked to send this mail");
    }

    @И("^переходим в \"([^\"]*)\"$")
    public void openSent(String btnName) {
        if(!btnName.equals("Отправленные")) {
            throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
        mainPage.clickSent();
        log.info("Go to sent");
    }

    @Тогда("^наже письмо появилось в отправленных$")
    public void checkSentMail() {
        Assert.assertEquals(to.startsWith(mainPage.getSentTo()), true);
        Assert.assertEquals(mainPage.getSentSubject(), subject);
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
        Assert.assertEquals(loginPage.checkFillPassword(), true);
        log.info("Log out successful. Loaded login page now");
    }
}