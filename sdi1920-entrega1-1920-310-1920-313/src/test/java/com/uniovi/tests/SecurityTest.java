package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityTest {

	// Alex
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\angel\\git\\repository\\sdi1920-entrega1-1920-310-1920-313\\lib\\geckodriver024win64.exe";

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
	public void testInvalidEnterUserList() {
		String urlLocal = "http://localhost:8090/user/list";
		driver.navigate().to(urlLocal);
		
		PO_NavView.changeIdiom(driver, "btnSpanish");
		
		PO_LoginView.checkElement(driver, "class", "btn btn-primary");
	}
	
	@Test
	public void testInvalidEnterPublicationList() {
		String urlLocal = "http://localhost:8090/publication/list";
		driver.navigate().to(urlLocal);
		
		PO_NavView.changeIdiom(driver, "btnSpanish");
		
		PO_LoginView.checkElement(driver, "class", "btn btn-primary");
	}
	
	@Test
	public void testInvalidEnterAdminOption() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());
		
		String urlLocal = "http://localhost:8090/adminView";
		driver.navigate().to(urlLocal);
		
		assertTrue(driver.findElement(By.xpath("//h1")).getText().equals("HTTP Status 403 – Forbidden"));
	}

}
