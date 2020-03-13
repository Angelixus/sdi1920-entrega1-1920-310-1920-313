package com.uniovi.tests.pageobjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	public static void fillFormAndCheckElement(WebDriver driver, String email, String password, String type, String target) {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, email, password);
		PO_View.checkElement(driver, type, target);
	}
	
	public static void fillFormAndCheckKey(WebDriver driver, String email, String password, String key, int locale) {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, email, password);
		PO_View.checkKey(driver, key, locale);
	}

	public static void searchText(WebDriver driver, String searchString) {
		WebElement input = driver.findElement(By.name("searchText"));	
		input.click();
		input.clear();
		input.sendKeys(searchString);
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();;
	}

	public static void enterFriendRequestList(WebDriver driver, String key, int locale) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//li[@id=\"friendRequest-menu\"]", getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//li[@id=\"friendRequest-menu\"]//ul//li//a[@href=\"/friendRequest/list\"]", getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		
		PO_FriendRequestListView.checkKey(driver, key, locale);
		
	}

	public static void sendFriendRequest(WebDriver driver, String xpath) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", xpath, getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
	}

	public static void acceptFriendRequest(WebDriver driver, String friendName) {
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//td[text()=\"" + friendName + "\"]/parent::*//td//button", getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
	}

	public static int getFriendRequestCount(WebDriver driver, String string) {
		return SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody//tr", getTimeout()).size();
	}
}
