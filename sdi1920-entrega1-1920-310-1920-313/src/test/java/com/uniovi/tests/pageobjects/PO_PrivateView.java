package com.uniovi.tests.pageobjects;

import org.openqa.selenium.WebDriver;

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
}
