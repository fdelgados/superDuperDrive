package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    private final WebDriver driver;
    private final int port;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSignup")
    private WebElement buttonSignup;

    @FindBy(className = "alert-danger")
    private WebElement alertMessage;

    public SignUpPage(WebDriver driver, int port) {
        this.driver = driver;
        this.port = port;

        PageFactory.initElements(this.driver, this);
    }

    public void get() {
        driver.get("http://localhost:" + port + "/signup");
    }

    public void signup(String firstName, String lastName, String username, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);

        buttonSignup.click();
    }

    public String title() {
        return driver.getTitle();
    }

    public Boolean isError() {
        return alertMessage != null;
    }
}
