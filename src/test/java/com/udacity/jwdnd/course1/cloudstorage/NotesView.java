package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotesView extends HomePage {
    protected final JavascriptExecutor jsExecutor;
    protected final WebDriverWait wait;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-notes")
    private WebElement notesView;

    @FindBy(id = "addNewNoteButton")
    private WebElement createNoteButton;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDescription;

    @FindBy(css = "#notesTable .noteTitle")
    private List<WebElement> noteTitleCells;

    @FindBy(css = "#notesTable tbody tr")
    private List<WebElement> noteRows;

    @FindBy(css = "#notesTable .noteDescription")
    private List<WebElement> noteDescriptionCells;

    public NotesView(WebDriver driver, int port) {
        super(driver, port);

        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
    }

    @Override
    public void get() {
        super.get();
        viewNotesInterface();
    }

    private void viewNotesInterface() {
        jsExecutor.executeScript("arguments[0].click();", notesTab);
        wait.until(ExpectedConditions.attributeContains(notesView, "class", "active"));
    }

    public void createNewNote(String title, String description) {
        jsExecutor.executeScript("arguments[0].click();", createNoteButton);

        wait.until(ExpectedConditions.visibilityOf(inputNoteTitle));

        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(description);

        inputNoteDescription.submit();
    }

    public void editNote(String title, String description) {
        if (numberOfNotesDisplayed() == 0) {
            createNewNote("Foo", "Bar");
            get();
        }

        noteRows.get(0).findElement(By.className("js-openNoteButton")).click();

        wait.until(ExpectedConditions.visibilityOf(inputNoteTitle));

        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.clear();
        inputNoteDescription.sendKeys(description);

        inputNoteDescription.submit();
    }

    public void deleteNote() {
        if (numberOfNotesDisplayed() == 0) {
            return;
        }

        noteRows.get(0).findElement(By.className("js-deleteNoteButton")).click();
    }

    public String noteTitleFromList() {
        return noteTitleCells.get(0).getText();
    }

    public String noteDescriptionFromList() {
        return noteDescriptionCells.get(0).getText();
    }

    public int numberOfNotesDisplayed() {
        return noteRows.size();
    }
}
