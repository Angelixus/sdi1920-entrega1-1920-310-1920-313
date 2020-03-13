package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
}
