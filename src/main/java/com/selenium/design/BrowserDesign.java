package com.selenium.design;

import java.util.List;
import org.openqa.selenium.WebElement;

public interface BrowserDesign {

	/**
	 * This method launches the Chrome browser, maximizes the window, sets a 30-second wait time, 
	 * and loads the specified URL. If headless mode is enabled, it runs without a visible UI.
	 * 
	 * @param url The website address to open.
	 * @param headless Whether to run the browser without a UI.
	 */
	public void startApplication(String url, boolean headless);
	
	/**
	 * This method launches the specified browser (Chrome, Firefox, Edge), maximizes the window, 
	 * sets a 30-second wait time, and loads the specified URL. If headless mode is enabled, it runs without a visible UI.
	 * 
	 * @param browser The browser to use (Chrome, Firefox, Edge, or IE).
	 * @param headless Whether to run the browser without a UI.
	 * @param url The website address to open.
	 */
	public void startApplication(Browsers browser, boolean headless, String url);
	
	/**
	 * This method finds an element on the web page using the specified locator type (like ID, name, class, etc.) 
	 * and locator value.
	 * 
	 * @param locatorType The type of locator (ID, name, class, etc.).
	 * @param value The value of the locator.
	 * @return The first matching WebElement found.
	 */
	public WebElement locateElement(Locators locatorType, String value);	
	
	/**
	 * This method finds all elements on the web page that match the specified locator type 
	 * and locator value.
	 * 
	 * @param locatorType The type of locator (ID, name, class, etc.).
	 * @param value The value of the locator.
	 * @return A list of all matching WebElements, or an empty list if none are found.
	 */
	public List<WebElement> locateElements(Locators locatorType, String value);
	
	/**
	 * This method checks if the current URL in the browser matches the expected URL.
	 * 
	 * @param expectedUrl The URL you expect to see.
	 * @return True if the current URL matches the expected URL, false otherwise.
	 */
	public boolean verifyUrl(String expectedUrl);
	
	/**
	 * This method checks if the current page title in the browser matches the expected title.
	 * 
	 * @param expectedTitle The title you expect to see.
	 * @return True if the current title matches the expected title, false otherwise.
	 */
	public boolean verifyTitle(String expectedTitle);
	
	/**
	 * This method closes the currently active browser window.
	 */
	public void closeDriver();
	
	/**
	 * This method closes all browser windows opened by the WebDriver.
	 */
	public void quitDriver();
}
