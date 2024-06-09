package com.saucedemo.stepDefinitions;

import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.xml.XmlTest;

import com.saucedemo.utils.ConfigurationFileReader;
import com.testng.base.ApplicationSpecificMethods;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	ApplicationSpecificMethods specificMethods = new ApplicationSpecificMethods();

	XmlTest xmlTest;
	String browser;
	protected Properties properties;

	private BaseTest baseTest;

	public Hooks(BaseTest baseTest) {
		this.baseTest = baseTest;
	}

	@Before
	public void setUpBrowser() {
		properties = ConfigurationFileReader.getInstance().getProperties();
		specificMethods.preCondition(getBrowserType(), properties);
	}

	@After
	public void tearDown(Scenario scenario) {
		addScreenshotEvidence(scenario, ApplicationSpecificMethods.getWebDriver());
		specificMethods.postCondition();
	}

	public String getBrowserType() {
		try {
			xmlTest = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest();
			browser = xmlTest.getParameter("browser");
			if (browser.equals(null)) {
				throw new Exception();
			}
		} catch (Exception e) {
			browser = properties.getProperty("defaultBrowser");
		}
		return browser;
	}

	public void addScreenshotEvidence(Scenario scenario, WebDriver driver) {
		String testName = scenario.getName();
		Date date = new Date();
		if (scenario.isFailed() && driver != null) {
			scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png",
					"Test Name:" + testName + ". captured at:" + date);
		}
	}
}
