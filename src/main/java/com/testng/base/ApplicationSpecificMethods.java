package com.testng.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Optional;

import com.selenium.base.SeleniumBaseImpl;
import com.selenium.design.Browsers;

public class ApplicationSpecificMethods extends SeleniumBaseImpl {
	protected WebDriver driver;
	protected Properties properties;
	
	public void preCondition(@Optional("defaultBrowser") String browser, Properties properties) {
		this.properties = properties;
		startApplication(Browsers.valueOf((browser)), false, properties.getProperty("sauceDemo.URL"));
		driver = getWebDriver();
	}

	public void postCondition() {
		closeDriver();
	}
}