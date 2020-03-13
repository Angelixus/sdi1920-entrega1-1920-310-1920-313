package com.uniovi.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchUsersTest {

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
	public void searchEmptyTest() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.email FROM USER u");

			PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
					PO_Properties.getSPANISH());

			PO_PrivateView.searchText(driver, "");
			List<String> emails = new ArrayList<String>();
			while (rs.next()) {
				String email = rs.getString(1);
				if (!(email.equals("lucio@uniovi.es") || email.equals("admin@gmail.com")))
					emails.add(email);
			}

			int modulus = emails.size() % 5;
			int pages = emails.size() / 5;
			if (modulus != 0)
				pages++;

			int localCounter = 0;
			for (int counter = 0; counter < pages; counter++) {
				for (int i = localCounter; i < emails.size(); i++) {
					PO_PrivateView.checkElement(driver, "text", emails.get(i));
					if (counter < pages) {
						if ((localCounter % ((5 * (counter + 1)) - 1)) == 0 && localCounter != 0) {
							localCounter++;
							break;
						}
					}
					localCounter++;
				}

				if (counter != pages - 1) {
					// Go to next page
					List<WebElement> paginationClicks = new ArrayList<WebElement>();
					if (counter == 0)
						paginationClicks = PO_NavView.checkElement(driver, "free",
								"//ul[@class=\"pagination pagination-centered\"]//li[3]//a");
					else
						paginationClicks = PO_NavView.checkElement(driver, "free",
								"//ul[@class=\"pagination pagination-centered\"]//li[4]//a");

					// Only one page should be obtained
					assertTrue(paginationClicks.size() == 1);
					paginationClicks.get(0).click();
				}
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void searchNonExistingTextTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.searchText(driver, "########");
		assertTrue(PO_PrivateView.checkElementDoesNotExist(driver, "//tbody//td"));
	}
	
	@Test
	public void searchExistingEmailTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.searchText(driver, "juan@uniovi.es");
		List<WebElement> elements = PO_PrivateView.checkElement(driver, "text", "juan@uniovi.es");
		assertTrue(elements.size() == 1);
	}
	
	@Test
	public void searchExistingNameTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.searchText(driver, "Alejandro");
		List<WebElement> elements = PO_PrivateView.checkElement(driver, "text", "alex@uniovi.es");
		assertTrue(elements.size() == 1);
	}
	
	@Test
	public void searchExistingSurnameTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.searchText(driver, "Almonte");
		List<WebElement> elements = PO_PrivateView.checkElement(driver, "text", "wolf@uniovi.es");
		assertTrue(elements.size() == 1);
	}
}
