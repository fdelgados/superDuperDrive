package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static final String USERNAME = "foo";
	private static final String PASSWORD = "barbaz";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void itShouldAllowAccessToLoginPageToUnauthenticatedUsers() {
		LoginPage loginPage = new LoginPage(driver, port);
		loginPage.get();

		Assertions.assertEquals("Login", loginPage.title());
	}

	@Test
	public void itShouldAllowAccessToSignupPageToUnauthenticatedUsers() {
		SignUpPage signUpPage = new SignUpPage(driver, port);
		signUpPage.get();

		Assertions.assertEquals("Sign Up", signUpPage.title());
	}

	@Test
	public void itShouldRedirectToHomeIfUserIsNotAuthenticated() {
		driver.get("http://localhost:" + port + "/home");

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void itShouldSignupLoginAndLogout() {
		SignUpPage signUpPage = new SignUpPage(driver, port);
		signUpPage.get();
		signUpPage.signup("Cisco", "Delgado", USERNAME, PASSWORD);

		LoginPage loginPage = new LoginPage(driver, port);
		loginPage.get();
		loginPage.login(USERNAME, PASSWORD);

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver, port);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void itShouldCreateAndDeleteNotes() {
		String noteTitle = "Note 1";
		String noteDescription = "Lorem ipsum";

		NotesView notesView = new NotesView(driver, port);
		notesView.get();
		notesView.createNewNote(noteTitle, noteDescription);

		assertResultIsSuccess();

		notesView.get();

		Assertions.assertEquals(1, notesView.numberOfNotesDisplayed());
		Assertions.assertEquals(noteTitle, notesView.noteTitleFromList());
		Assertions.assertEquals(noteDescription, notesView.noteDescriptionFromList());

		notesView.deleteNote();
		notesView.get();

		Assertions.assertEquals(0, notesView.numberOfNotesDisplayed());
	}

	@Test
	public void itShouldEditAnExistingNote() {
		String noteNewTitle = "Note 1 edited";
		String noteNewDescription = "Lorem ipsum dolor sit amet";

		NotesView notesView = new NotesView(driver, port);
		notesView.get();

		notesView.editNote(noteNewTitle, noteNewDescription);

		assertResultIsSuccess();

		notesView.get();

		Assertions.assertEquals(noteNewTitle, notesView.noteTitleFromList());
		Assertions.assertEquals(noteNewDescription, notesView.noteDescriptionFromList());
	}

	private void assertResultIsSuccess() {
		Assertions.assertEquals("Result", driver.getTitle());
		WebElement resultTitle = driver.findElement(By.tagName("h1"));
		Assertions.assertEquals("Success", resultTitle.getText());
	}

	private void logout() {
		HomePage homePage = new HomePage(driver, port);

		homePage.logout();
	}
}
