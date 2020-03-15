package com.uniovi.tests;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_AddPublication;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddPublicationTest {

	//Alex
	static String PathFirefox65 = "C:\\Users\\roxex\\Desktop\\Clase\\3er_Curso\\2ndoTrimestre\\SDI\\Social Network\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\roxex\\Desktop\\Clase\\3er_Curso\\2ndoTrimestre\\SDI\\Social Network\\geckodriver024win64.exe";

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
	
	//Prueba 24
	@Test
	public void testValidPublication() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		PO_AddPublication.fillForm(driver, "Titulo", "Texto");
		PO_View.checkElement(driver, "text", "Titulo");

	}
	
	//Prueba 25
	@Test
	public void testInvalidPublication() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		PO_AddPublication.fillForm(driver, "Titulo", "");
		PO_AddPublication.checkKey(driver, "error.publication.text.empty", PO_Properties.getSPANISH());
		PO_AddPublication.fillForm(driver, "", "Texto");
		PO_AddPublication.checkKey(driver, "error.publication.title.empty", PO_Properties.getSPANISH());
	}
}
