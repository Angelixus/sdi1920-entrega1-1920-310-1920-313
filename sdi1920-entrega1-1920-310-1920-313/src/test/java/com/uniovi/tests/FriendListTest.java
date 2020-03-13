package com.uniovi.tests;

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
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendListTest {

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
		driver.navigate().to(URL);
		// We need to send some friends first
		PO_PrivateView.fillFormAndCheckKey(driver, "lucio@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.sendFriendRequest(driver, "//tbody[tr]//tr[1]//div//div//button");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_PrivateView.fillFormAndCheckKey(driver, "geralt@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.sendFriendRequest(driver, "//tbody//tr[2]//div//div//button");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		PO_PrivateView.fillFormAndCheckKey(driver, "wolf@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.sendFriendRequest(driver, "//tbody//tr[2]//div//div//button");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		PO_PrivateView.fillFormAndCheckKey(driver, "juan@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.enterFriendRequestList(driver, "friendRequest.list.title", PO_Properties.getSPANISH());
		PO_PrivateView.acceptFriendRequest(driver, "Marta");
		PO_PrivateView.acceptFriendRequest(driver, "Pedro");
		PO_PrivateView.acceptFriendRequest(driver, "María");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
	}

	@AfterClass
	static public void end() {
		Connection con = null;
		Statement st = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			st.executeUpdate("DELETE FROM FRIEND");
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
	public void testListFriends() {
		PO_PrivateView.fillFormAndCheckKey(driver, "juan@uniovi.es", "123456", "userList.nextUsers",
				PO_Properties.getSPANISH());

		PO_PrivateView.enterFriendList(driver, "friends.list.title", PO_Properties.getSPANISH());

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			rs = st.executeQuery("SELECT ID FROM USER u WHERE u.email=\'juan@uniovi.es\'");
			rs.next();
			long id = rs.getLong(1);

			rs.close();
			rs = st.executeQuery("SELECT * FROM FRIEND f WHERE f.FRIENDA_ID=" + id + "OR f.FRIENDB_ID=" + id);
			List<Long> ids = new ArrayList<>();
			while (rs.next()) {
				Long idA = rs.getLong(2);
				Long idB = rs.getLong(3);
				
				if(idA != id)
					ids.add(idA);
				
				if(idB != id)
					ids.add(idB);
			}
			for(Long idLocal : ids) {
				rs = st.executeQuery("SELECT u.EMAIL FROM USER u WHERE u.ID=" + idLocal);
				rs.next();
				
				String email = rs.getString(1);
				PO_PrivateView.checkElement(driver, "text", email);
				rs.close();
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
