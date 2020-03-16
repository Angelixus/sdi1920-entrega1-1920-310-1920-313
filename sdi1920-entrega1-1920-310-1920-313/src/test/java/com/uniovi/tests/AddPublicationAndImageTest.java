package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_AddPublication;
import com.uniovi.tests.pageobjects.PO_ListPublication;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddPublicationAndImageTest {

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
		Connection con = null;
		Statement st = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			st.executeUpdate("DELETE FROM PUBLICATION");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		Connection con = null;
		Statement st = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			st.executeUpdate("DELETE FROM PUBLICATION");
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}
	
	@Test
	public void createPostWithImage() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		PO_AddPublication.fillFormWithoutClickingButton(driver, UUID.randomUUID().toString(), "Texto");
		
		List<WebElement> imageInput = PO_AddPublication.checkElement(driver, "id", "uploadImage");
		imageInput.get(0).sendKeys(System.getProperty("user.dir") + "\\testImage\\imageTest.png");
		
		driver.findElement(By.className("btn")).click();
		
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();
		
		PO_ListPublication.checkElement(driver, "free", "//div//img");
	}
	
	@Test
	public void createPostWithOutImage() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers", PO_Properties.getSPANISH());
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		String uuid = UUID.randomUUID().toString();
		
		PO_AddPublication.fillForm(driver, uuid, "Texto");
		
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();
				
		Boolean shouldBeTrue = PO_ListPublication.checkElementDoesNotExist(driver, "//td//img");
		assertTrue(shouldBeTrue);
	}

}
