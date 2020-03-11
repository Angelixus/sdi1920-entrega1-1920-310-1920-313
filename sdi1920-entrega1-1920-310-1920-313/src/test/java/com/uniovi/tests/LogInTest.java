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
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogInTest {

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
		public void validSignupAdmin() {
			PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers", PO_Properties.getSPANISH());
			PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		}
		
		@Test
		public void validSignupNormalUser() {
			PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers", PO_Properties.getSPANISH());
			PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		}

}
