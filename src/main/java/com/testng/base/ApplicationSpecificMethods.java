package com.testng.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.selenium.base.SeleniumBaseImpl;
import com.selenium.design.Browsers;

public class ApplicationSpecificMethods extends SeleniumBaseImpl {
	protected WebDriver driver;
	protected Properties properties;
	
	@Parameters("browser")
	@BeforeMethod
	public void preCondition(@Optional("defaultBrowser") String browser) {
		properties = initializePropertyFileReader();
		startApplication(Browsers.valueOf(setUpBrowser(browser)), false, properties.getProperty("sauceDemo.URL"));
		driver = getWebDriver();
	}

	@AfterMethod
	public void postCondition() {
		closeDriver();
	}

	public String setUpBrowser(String browser) {
		if (browser.equals("defaultBrowser")) {
			return properties.getProperty(browser);
		} else {
			return browser;
		}
	}
}