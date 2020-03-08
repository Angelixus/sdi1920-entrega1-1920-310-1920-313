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

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpTest {
	
	// En Windows (Debe ser la versiÃ³n 65.0.1 y desactivar las actualizacioens
	// automÃ¡ticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\angel\\git\\sdi1920-entrega1-1920-310-1920-313\\sdi1920-entrega1-1920-310-1920-313\\lib\\geckodriver024win64.exe";
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
	public void testEnterSignup() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}
	
	// Prueba1
	@Test
	public void testValidSignup() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@uniovi.es", "Pedrin", "Garcia", "123456", "123456");
		PO_HomeView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
	}
	// Prueba2
	@Test
	public void testInvalidEmptySignup() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "", "Pedrin", "Garcia", "123456", "123456");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getSPANISH());
		/*
		PO_RegisterView.checkKey(driver, "error.email.empty", PO_Properties.getSPANISH());
		*/
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@uniovi.es", "", "Garcia", "123456", "123456");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getSPANISH());
		/*
		PO_HomeView.checkKey(driver, "error.name.empty", PO_Properties.getSPANISH());
		*/
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@uniovi.es", "Pedrin", "", "123456", "123456");
		PO_RegisterView.checkKey(driver, "signUp.register", PO_Properties.getSPANISH());
		/*
		PO_HomeView.checkKey(driver, "error.lastName.empty", PO_Properties.getSPANISH());
		*/
	}
	
	// Prueba3
	@Test
	public void testInvalidRepeatedPasswordSignup() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@gmail.com", "Pedrin", "Garcia", "123456", "1234567");
		PO_RegisterView.checkKey(driver, "error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}
	
	@Test
	// Prueba4
	public void testInvalidExistingEmail() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@gmail.com", "Pedrin", "Garcia", "123456", "123456");
		PO_HomeView.checkKey(driver, "welcome.message", PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "example@gmail.com", "Pedrin", "Garcia", "123456", "123456");
		PO_RegisterView.checkKey(driver, "error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

}