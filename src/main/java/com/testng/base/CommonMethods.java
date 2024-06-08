package com.testng.base;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.selenium.base.SeleniumBaseImpl;
import com.selenium.design.Browsers;

public class CommonMethods extends SeleniumBaseImpl {

	protected WebDriver driver;
	@BeforeMethod
	public void preCondition() {
		startApplication(Browsers.CHROME, false, "https://www.saucedemo.com/");
		driver = getWebDriver();
	}

	@AfterMethod
	public void postCondition() {
		closeDriver();
	}

}