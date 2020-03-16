package com.uniovi.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_AddPublication;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListPublicationTest {

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
		driver.navigate().to(URL);

		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();

		PO_AddPublication.fillForm(driver, UUID.randomUUID().toString(), "Texto");
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
		elementos.get(0).click();
		elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();

		PO_AddPublication.fillForm(driver, UUID.randomUUID().toString(), "Texto");
		
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
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
	public void testListPublication() {
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
                PO_Properties.getSPANISH());
        List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'publications-menu')]/a");
        elementos.get(0).click();
        elementos = PO_NavView.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
        elementos.get(0).click();
        
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			rs = st.executeQuery("SELECT p.title FROM PUBLICATION p;");
			while(rs.next()) {
				String title = rs.getString(1);
				PO_View.checkElement(driver, "text", title);
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
}
