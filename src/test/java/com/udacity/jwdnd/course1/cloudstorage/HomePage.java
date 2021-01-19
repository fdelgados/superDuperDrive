package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private static final String USERNAME = "foo";
    private static final String PASSWORD = "barbaz";

    private final WebDriver driver;
    private final int port;

    @FindBy(id = "buttonLogout")
    private WebElement buttonLogout;

    public HomePage(WebDriver driver, int port) {
        this.driver = driver;
        this.port = port;

        PageFactory.initElements(this.driver, this);
    }

    public void get() {
        if (driver.getTitle().equals("Home")) {
            return;
        }

        driver.get("http://localhost:" + port + "/home");

        if (driver.getTitle().equals("Login")) {
            login();
        }

        if (driver.getTitle().equals("Login")) {
            signup();
            login();
        }
    }

    private void login() {
        LoginPage loginPage = new LoginPage(driver, port);
        loginPage.get();
        loginPage.login(USERNAME, PASSWORD);
    }

    private void signup() {
        SignUpPage signUpPage = new SignUpPage(driver, port);
        signUpPage.get();
        signUpPage.signup("Cisco", "Delgado", USERNAME, PASSWORD);
    }

    public final void logout() {
        buttonLogout.submit();
    }
}
