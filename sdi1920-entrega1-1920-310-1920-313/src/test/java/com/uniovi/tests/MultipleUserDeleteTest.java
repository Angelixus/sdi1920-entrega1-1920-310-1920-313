package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.entities.User;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MultipleUserDeleteTest {

	// Alex
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\angel\\git\\repository\\sdi1920-entrega1-1920-310-1920-313\\lib\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090/";

	private static List<User> usersToReinsert = new ArrayList<>();

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
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM USER;");
			while(rs.next()) {
				Long id = rs.getLong(1);
				String email = rs.getString(2);
				String lastName = rs.getString(3);
				String name = rs.getString(4);
				String password = rs.getString(5);
				String role = rs.getString(6);
				
				User user = new User(email, name, lastName);
				user.setPassword(password);
				user.setRole(role);
				user.setId(id);
				usersToReinsert.add(user);
			}
		} catch(SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@AfterClass
	static public void end() {
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();

			st.executeUpdate("DELETE FROM USER");
			for(User user : usersToReinsert) {
				String query = "INSERT INTO USER(ID, EMAIL, LAST_NAME, NAME, PASSWORD, ROLE) VALUES(?, ?, ?, ?, ?, ?)";
				pst = con.prepareStatement(query);
				pst.setLong(1, user.getId());
				pst.setString(2, user.getEmail());
				pst.setString(3, user.getLastName());
				pst.setString(4, user.getName());
				pst.setString(5, user.getPassword());
				pst.setString(6, user.getRole());
				
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				st.close();
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}

	@Test
	public void deleteLastUserOnListTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());

		List<WebElement> paginationElements = PO_NavView.checkElement(driver, "free",
				"//ul[@class=\"pagination pagination-centered\"]//li");
		List<WebElement> paginationCLicks = new ArrayList<WebElement>();
		if (paginationElements.size() == 4) {
			paginationCLicks = PO_NavView.checkElement(driver, "free",
					"//ul[@class=\"pagination pagination-centered\"]//li[4]//a");
		} else {
			paginationCLicks = PO_NavView.checkElement(driver, "free",
					"//ul[@class=\"pagination pagination-centered\"]//li[4]//a");
		}

		assertTrue(paginationCLicks.size() == 1);
		paginationCLicks.get(0).click();

		String deletedUserEmail = driver.findElement(By.xpath("//tbody//tr[last()]//td[1]")).getText();

		driver.findElement(By.xpath("//tbody//tr[last()]//td[last()]//div//input")).click();

		driver.findElement(By.xpath("//button[@id=\"deleteChecked\"]")).click();

		if (paginationElements.size() == 4) {
			paginationCLicks = PO_NavView.checkElement(driver, "free",
					"//ul[@class=\"pagination pagination-centered\"]//li[4]//a");
		} else {
			paginationCLicks = PO_NavView.checkElement(driver, "free",
					"//ul[@class=\"pagination pagination-centered\"]//li[4]//a");
		}

		assertTrue(paginationCLicks.size() == 1);
		paginationCLicks.get(0).click();

		assertTrue(!driver.findElement(By.xpath("//tbody//tr[last()]//td[1]")).getText().equals(deletedUserEmail));

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();
			String query = "SELECT u.email FROM USER u WHERE u.email=\'" + deletedUserEmail + "\'";
			rs = st.executeQuery(query);
			boolean found = false;
			while (rs.next()) {
				found = true;
			}

			assertTrue(!found);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
	public void deleteFirstUserOnListTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());

		String deletedUserEmail = driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).getText();

		driver.findElement(By.xpath("//tbody//tr[1]//td[last()]//div//input")).click();

		driver.findElement(By.xpath("//button[@id=\"deleteChecked\"]")).click();

		assertTrue(!driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).getText().equals(deletedUserEmail));

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();
			String query = "SELECT u.email FROM USER u WHERE u.email=\'" + deletedUserEmail + "\'";
			rs = st.executeQuery(query);
			boolean found = false;
			while (rs.next()) {
				found = true;
			}

			assertTrue(!found);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
	public void deleteThreeUserTest() {
		PO_PrivateView.fillFormAndCheckKey(driver, "admin@gmail.com", "admin", "userList.nextUsers",
				PO_Properties.getSPANISH());

		List<String> emailsDeleted = new ArrayList<>();
		emailsDeleted.add(driver.findElement(By.xpath("//tbody//tr[1]//td[1]")).getText());
		emailsDeleted.add(driver.findElement(By.xpath("//tbody//tr[2]//td[1]")).getText());
		emailsDeleted.add(driver.findElement(By.xpath("//tbody//tr[3]//td[1]")).getText());

		driver.findElement(By.xpath("//tbody//tr[1]//td[last()]//div//input")).click();
		driver.findElement(By.xpath("//tbody//tr[2]//td[last()]//div//input")).click();
		driver.findElement(By.xpath("//tbody//tr[3]//td[last()]//div//input")).click();

		driver.findElement(By.xpath("//button[@id=\"deleteChecked\"]")).click();

		for (int i = 0; i < emailsDeleted.size(); i++) {
			int xpathIndex = i + 1;
			assertTrue(!driver.findElement(By.xpath("//tbody//tr[" + xpathIndex + "]//td[1]")).getText()
					.equals(emailsDeleted.get(i)));
		}

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
			st = con.createStatement();
			for (String deletedUserEmail : emailsDeleted) {
				String query = "SELECT u.email FROM USER u WHERE u.email=\'" + deletedUserEmail + "\'";
				rs = st.executeQuery(query);
				boolean found = false;
				while (rs.next()) {
					found = true;
				}

				assertTrue(!found);
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
