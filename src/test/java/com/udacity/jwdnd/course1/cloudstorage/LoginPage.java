package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private final WebDriver driver;
    private final int port;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonLogin")
    private WebElement buttonLogin;

    public LoginPage(WebDriver driver, int port) {
        this.driver = driver;
        this.port = port;

        PageFactory.initElements(this.driver, this);
    }

    public void get() {
        driver.get("http://localhost:" + port + "/login");
    }

    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);

        buttonLogin.click();
    }

    public String title() {
        return driver.getTitle();
    }
}
