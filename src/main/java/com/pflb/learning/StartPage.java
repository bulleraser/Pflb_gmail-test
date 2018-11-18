package com.pflb.learning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StartPage extends AbstractPage{

    {
        driver.get("https://www.google.com/intl/ru/gmail/about/#");
    }

    public StartPage(WebDriver driver) {
        super(driver);
    }

    public void signIn() {
        WebElement btnSignIn = driver.findElement(By.xpath("//a[@data-g-label='Sign in']"));
        btnSignIn.click();
    }

    public void open(String url) {
        driver.get(url);
    }
}
