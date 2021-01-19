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
	private static final String CREDENTIAL_URL = "https://www.google.com";
	public static final String FIRST_NAME = "Cisco";
	public static final String LAST_NAME = "Delgado";

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
	public void itShouldRedirectToADefaultErrorPageWhenInvalidUrlRequestedAfterLogin() {
		HomePage homePage = new HomePage(driver, port);
		homePage.get();

		driver.get("http://localhost:" + port + "/invalid");

		Assertions.assertEquals("Page not found", driver.getTitle());
		homePage.get();
	}

	@Test
	public void itShouldSignupNewUsers() {
		SignUpPage signUpPage = new SignUpPage(driver, port);
		signUpPage.get();
		signUpPage.signup(FIRST_NAME, LAST_NAME, "myusername", PASSWORD);

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void itShouldSignupLoginAndLogout() {
		SignUpPage signUpPage = new SignUpPage(driver, port);
		signUpPage.get();
		signUpPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

		LoginPage loginPage = new LoginPage(driver, port);
		loginPage.get();
		loginPage.login(USERNAME, PASSWORD);

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver, port);
		homePage.logout();

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void itShouldNotAllowRegistrationOfMoreThanOneUserWithTheSameUsername() {
		SignUpPage signUpPage = new SignUpPage(driver, port);
		signUpPage.get();
		signUpPage.signup(FIRST_NAME, LAST_NAME, "sillyusername", PASSWORD);
		signUpPage.get();
		signUpPage.signup(FIRST_NAME, LAST_NAME, "sillyusername", PASSWORD);

		Assertions.assertTrue(signUpPage.isError());
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

	@Test
	public void itShouldCreateAndDeleteCredentials() {
		CredentialsView credentialsView = new CredentialsView(driver, port);
		credentialsView.get();
		credentialsView.createNewCredential(CREDENTIAL_URL, USERNAME, PASSWORD);

		assertResultIsSuccess();

		credentialsView.get();

		Assertions.assertEquals(1, credentialsView.numberOfDisplayedCredentials());
		Assertions.assertEquals(CREDENTIAL_URL, credentialsView.credentialUrlFromList());
		Assertions.assertEquals(USERNAME, credentialsView.credentialUsernameFromList());
		Assertions.assertEquals(PASSWORD, credentialsView.credentialPlainPasswordFromList());

		credentialsView.deleteCredential();
		credentialsView.get();

		Assertions.assertEquals(0, credentialsView.numberOfDisplayedCredentials());
	}

	@Test
	public void itShouldEditAnExistingCredential() {
		String credentialNewUrl = "https://www.amazon.com";
		String credentialNewUsername = "compulsive_buyer";
		String credentialNewPassword = "supersecretpassword";

		CredentialsView credentialsView = new CredentialsView(driver, port);
		credentialsView.get();

		credentialsView.editCredential(credentialNewUrl, credentialNewUsername, credentialNewPassword);

		assertResultIsSuccess();

		credentialsView.get();

		Assertions.assertEquals(credentialNewUrl, credentialsView.credentialUrlFromList());
		Assertions.assertEquals(credentialNewUsername, credentialsView.credentialUsernameFromList());
		Assertions.assertEquals(credentialNewPassword, credentialsView.credentialPlainPasswordFromList());
	}

	@Test
	public void itShouldNotAllowUploadingOfEmptyFiles() {
		FilesView filesView = new FilesView(driver, port);
		filesView.get();

		filesView.uploadEmptyFile();

		Assertions.assertEquals("Result", driver.getTitle());
		WebElement resultTitle = driver.findElement(By.tagName("h1"));
		Assertions.assertEquals("Error", resultTitle.getText());
	}

	private void assertResultIsSuccess() {
		Assertions.assertEquals("Result", driver.getTitle());
		WebElement resultTitle = driver.findElement(By.tagName("h1"));
		Assertions.assertEquals("Success", resultTitle.getText());
	}
}
