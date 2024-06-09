package com.selenium.base;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.design.Browsers;

public class WebDriverInstance {

	private static final ThreadLocal<RemoteWebDriver> remoteWebDriver = new ThreadLocal<RemoteWebDriver>();
	private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	
	public static RemoteWebDriver getWebDriver() {
		return remoteWebDriver.get();
	}
	public static WebDriverWait getWait() {
		return wait.get();
	}
	
	public void setWebDriver(Browsers browser, boolean headless) {
		switch (browser) {
		case CHROME:
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized"); 
			chromeOptions.addArguments("--disable-notifications"); 
			chromeOptions.addArguments("--incognito");
			remoteWebDriver.set(new ChromeDriver(chromeOptions));
			break;
		case FIREFOX:
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--disable-notifications"); 
			firefoxOptions.addArguments("--incognito");
			remoteWebDriver.set(new FirefoxDriver(firefoxOptions));
			getWebDriver().manage().window().maximize();
			break;
		case EDGE:
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--start-maximized"); 
			edgeOptions.addArguments("--disable-notifications"); 
			edgeOptions.addArguments("--incognito");
			remoteWebDriver.set(new EdgeDriver(edgeOptions));
			break;	
		default:
			System.err.println("There is no such browser. Please select the available browser");
			break;
		}
	}
	
	public void setWait() {
		wait.set(new WebDriverWait(getWebDriver(), Duration.ofSeconds(10)));
	}
	
}
