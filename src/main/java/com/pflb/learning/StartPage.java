package com.pflb.learning;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends AbstractPage{
    @FindBy(xpath = "/html/body/nav/div/a[2]")
    private WebElement btnSignIn;

    {
        driver.get("https://www.google.com/intl/ru/gmail/about/#");
    }

    public StartPage(WebDriver driver) {
        super(driver);
    }

    public void signIn() {
        btnSignIn.click();
    }

    public void open(String url) {
        driver.get(url);
    }
}
