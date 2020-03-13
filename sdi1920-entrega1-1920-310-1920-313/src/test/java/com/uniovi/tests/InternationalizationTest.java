package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InternationalizationTest {

	// En Windows (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens
	// automÃ¡ticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\angel\\git\\repository\\sdi1920-entrega1-1920-310-1920-313\\lib\\geckodriver024win64.exe";
	// En MACOSX (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens
	// automÃ¡ticas):
	// static String PathFirefox65 =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// static String Geckdriver024 = "/Users/delacal/selenium/geckodriver024mac";
	// ComÃºn a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090/";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	@Test
	public void testInternationalization() {
		// index View Internationalization
		PO_View.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getSPANISH());

		PO_NavView.changeIdiom(driver, "btnEnglish");

		PO_View.checkKey(driver, "welcome.message", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getENGLISH());

		PO_NavView.changeIdiom(driver, "btnSpanish");

		PO_View.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getSPANISH());

		// Access and check Internationalization of signup view
		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.lastName", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.password", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.repeatPassword", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "btnSend.message", PO_Properties.getSPANISH());

		PO_NavView.changeIdiom(driver, "btnEnglish");

		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "singUp.name", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "singUp.lastName", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "singUp.password", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "singUp.repeatPassword", PO_Properties.getENGLISH());
		PO_RegisterView.checkKey(driver, "btnSend.message", PO_Properties.getENGLISH());

		PO_NavView.changeIdiom(driver, "btnSpanish");

		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.name", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.lastName", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.password", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "singUp.repeatPassword", PO_Properties.getSPANISH());
		PO_RegisterView.checkKey(driver, "btnSend.message", PO_Properties.getSPANISH());

		// Login to test private views
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());

		// Test user list view
		PO_PrivateView.checkKey(driver, "userList.title", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.searchButton", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.nextUsers", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.emailHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.nameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "pagination.last", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "pagination.first", PO_Properties.getSPANISH());
		
		PO_NavView.changeIdiom(driver, "btnEnglish");

		PO_PrivateView.checkKey(driver, "userList.title", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "userList.searchButton", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "userList.nextUsers", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "userList.emailHead", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "userList.nameHead", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "pagination.last", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "pagination.first", PO_Properties.getENGLISH());
		
		PO_NavView.changeIdiom(driver, "btnSpanish");

		PO_PrivateView.checkKey(driver, "userList.title", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.searchButton", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.nextUsers", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.emailHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "userList.nameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "pagination.last", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "pagination.first", PO_Properties.getSPANISH());
		
		// Check nav
		
		PO_NavView.checkKey(driver, "friends.message", PO_Properties.getSPANISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"friendRequest-menu\"]");
		PO_NavView.checkKey(driver, "friendRequest.list", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "friends.list", PO_Properties.getSPANISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"users-menu\"]");
		PO_NavView.checkKey(driver, "users.add", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "users.list", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "language.change", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "logout.message", PO_Properties.getSPANISH());
		// Logout to check signup and login buttons
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_NavView.changeIdiom(driver, "btnEnglish");
		
		PO_NavView.checkKey(driver, "friends.message", PO_Properties.getENGLISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"friendRequest-menu\"]");
		PO_NavView.checkKey(driver, "friendRequest.list", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "friends.list", PO_Properties.getENGLISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"users-menu\"]");
		PO_NavView.checkKey(driver, "users.add", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "users.list", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "language.change", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "logout.message", PO_Properties.getENGLISH());
		// Logout to check signup and login buttons
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_NavView.changeIdiom(driver, "btnEnglish");
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getENGLISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getENGLISH());
		
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getENGLISH());
	
		PO_NavView.changeIdiom(driver, "btnSpanish");
		
		PO_NavView.checkKey(driver, "friends.message", PO_Properties.getSPANISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"friendRequest-menu\"]");
		PO_NavView.checkKey(driver, "friendRequest.list", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "friends.list", PO_Properties.getSPANISH());
		PO_NavView.clickXPath(driver, "//li[@id=\"users-menu\"]");
		PO_NavView.checkKey(driver, "users.add", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "users.list", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "language.change", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "logout.message", PO_Properties.getSPANISH());
		// Logout to check signup and login buttons
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_NavView.checkKey(driver, "login.message", PO_Properties.getSPANISH());
		PO_NavView.checkKey(driver, "signup.message", PO_Properties.getSPANISH());
		
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());
		
		// Check friend list
		PO_PrivateView.clickXPath(driver, "//li[@id=\"friendRequest-menu\"]");
		PO_PrivateView.clickXPath(driver, "//li[@id=\"friendRequest-menu\"]//ul//li//a[@href=\"/friend/list\"]");
		PO_PrivateView.checkKey(driver, "friends.list.title", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.searchButton", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.nextUsers", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.nameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.lastNameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.emailHead", PO_Properties.getSPANISH());
		
		PO_NavView.changeIdiom(driver, "btnEnglish");
		
		PO_PrivateView.checkKey(driver, "friends.list.title", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "friends.searchButton", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "friends.nextUsers", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "friends.nameHead", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "friends.lastNameHead", PO_Properties.getENGLISH());
		PO_PrivateView.checkKey(driver, "friends.emailHead", PO_Properties.getENGLISH());
		
		PO_NavView.changeIdiom(driver, "btnSpanish");
		
		PO_PrivateView.checkKey(driver, "friends.list.title", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.searchButton", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.nextUsers", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.nameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.lastNameHead", PO_Properties.getSPANISH());
		PO_PrivateView.checkKey(driver, "friends.emailHead", PO_Properties.getSPANISH());


	}
}
