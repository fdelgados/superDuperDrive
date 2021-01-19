package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialsView extends HomePage {
    private final JavascriptExecutor jsExecutor;
    private final WebDriverWait wait;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "nav-credentials")
    private WebElement credentialsView;

    @FindBy(id = "addNewCredentialButton")
    private WebElement createCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(css = "#credentialTable .credentialUrl")
    private List<WebElement> credentialUrlCells;

    @FindBy(css = "#credentialTable .credentialUsername")
    private List<WebElement> credentialUsernameCells;

    @FindBy(css = "#credentialTable tbody tr")
    private List<WebElement> credentialRows;

    public CredentialsView(WebDriver driver, int port) {
        super(driver, port);

        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
    }

    @Override
    public void get() {
        super.get();
        viewTabsInterface();
    }

    private void viewTabsInterface() {
        jsExecutor.executeScript("arguments[0].click();", credentialsTab);
        wait.until(ExpectedConditions.attributeContains(credentialsView, "class", "active"));
    }

    public void createNewCredential(String url, String username, String password) {
        jsExecutor.executeScript("arguments[0].click();", createCredentialButton);

        wait.until(ExpectedConditions.visibilityOf(inputCredentialUrl));

        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);
        inputCredentialPassword.sendKeys(password);

        inputCredentialPassword.submit();
    }

    public void editCredential(String url, String username, String password) {
        if (numberOfDisplayedCredentials() == 0) {
            createNewCredential("https://www.udacity.com", "myusername", "123456abc");
            get();
        }

        credentialRows.get(0).findElement(By.className("js-openCredentialButton")).click();

        wait.until(ExpectedConditions.visibilityOf(inputCredentialUrl));

        inputCredentialUrl.clear();
        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.clear();
        inputCredentialUsername.sendKeys(username);
        inputCredentialPassword.clear();
        inputCredentialPassword.sendKeys(password);

        inputCredentialPassword.submit();
    }

    public void deleteCredential() {
        if (numberOfDisplayedCredentials() == 0) {
            return;
        }

        credentialRows.get(0).findElement(By.className("js-deleteCredentialButton")).click();
    }

    public String credentialUrlFromList() {
        return credentialUrlCells.get(0).getText();
    }

    public String credentialUsernameFromList() {
        return credentialUsernameCells.get(0).getText();
    }

    public String credentialPlainPasswordFromList() {
        return credentialRows.get(0).findElement(By.tagName("button")).getAttribute("data-password");
    }

    public int numberOfDisplayedCredentials() {
        return credentialRows.size();
    }
}
