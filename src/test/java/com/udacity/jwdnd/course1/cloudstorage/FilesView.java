package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FilesView extends HomePage {
    private final JavascriptExecutor jsExecutor;
    private final WebDriverWait wait;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-files")
    private WebElement filesView;

    @FindBy(id = "uploadFileButton")
    private WebElement uploadFileButton;

    public FilesView(WebDriver driver, int port) {
        super(driver, port);

        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
    }

    @Override
    public void get() {
        super.get();
        viewFilesInterface();
    }

    private void viewFilesInterface() {
        jsExecutor.executeScript("arguments[0].click();", filesTab);
        wait.until(ExpectedConditions.attributeContains(filesView, "class", "active"));
    }

    public void uploadEmptyFile() {
        uploadFileButton.submit();
    }
}
