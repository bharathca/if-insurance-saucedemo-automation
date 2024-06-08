package com.selenium.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.java.base.JavaBase;
import com.selenium.design.BrowserDesign;
import com.selenium.design.Browsers;
import com.selenium.design.Locators;
import com.selenium.design.WebElementDesign;

public class SeleniumBaseImpl extends WebDriverInstance implements BrowserDesign, WebElementDesign {

	private Select select;
	public void startApplication(String url, boolean headless) {
		try {
			setWebDriver(Browsers.CHROME, headless);
			setWait();
			getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			getWebDriver().get(url);
			System.out.println("The chrome browser is launched with the application url " + url);
		} catch (Exception e) {
			System.err.println("Something went wrong \n" + e.getMessage());
		}
	}

	public void startApplication(Browsers browser, boolean headless, String url) {
		try {
			setWebDriver(browser, headless);
			setWait();
			getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(90));
			getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
			getWebDriver().get(url);
		} catch (WebDriverException e) {
			System.err.println("The " + browser
					+ " Browser could not be Launched due to an WebDriverException. Hence Failed \\n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("The " + browser + " Browser could not be Launched due to an Exception. Hence Failed \\n"
					+ e.getMessage());
		}

	}

	public WebElement locateElement(Locators locatorType, String value) {
		try {
			switch (locatorType) {
			case CLASS_NAME:
				return getWebDriver().findElement(By.className(value));
			case CSS:
				return getWebDriver().findElement(By.cssSelector(value));
			case ID:
				return getWebDriver().findElement(By.id(value));
			case LINK_TEXT:
				return getWebDriver().findElement(By.linkText(value));
			case NAME:
				return getWebDriver().findElement(By.name(value));
			case PARTIAL_LINKTEXT:
				return getWebDriver().findElement(By.partialLinkText(value));
			case TAGNAME:
				return getWebDriver().findElement(By.tagName(value));
			case XPATH:
				return getWebDriver().findElement(By.xpath(value));
			default:
				System.err.println("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			System.err.println("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			System.err.println("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n Exception:" + e.getMessage());
		}
		return null;
	}
	
	public WebElement locateElement(Locators locatorType, WebElement locateElementFrom, String value) {
		try {
			switch (locatorType) {
			case CLASS_NAME:
				return locateElementFrom.findElement(By.className(value));
			case CSS:
				return locateElementFrom.findElement(By.cssSelector(value));
			case ID:
				return locateElementFrom.findElement(By.id(value));
			case LINK_TEXT:
				return locateElementFrom.findElement(By.linkText(value));
			case NAME:
				return locateElementFrom.findElement(By.name(value));
			case PARTIAL_LINKTEXT:
				return locateElementFrom.findElement(By.partialLinkText(value));
			case TAGNAME:
				return locateElementFrom.findElement(By.tagName(value));
			case XPATH:
				return locateElementFrom.findElement(By.xpath(value));
			default:
				System.err.println("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			System.err.println("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			System.err.println("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n Exception:" + e.getMessage());
		}
		return null;
	}

	public List<WebElement> locateElements(Locators locatorType, String value) {
		try {
			switch (locatorType) {
			case CLASS_NAME:
				return getWebDriver().findElements(By.className(value));
			case CSS:
				return getWebDriver().findElements(By.cssSelector(value));
			case ID:
				return getWebDriver().findElements(By.id(value));
			case LINK_TEXT:
				return getWebDriver().findElements(By.linkText(value));
			case NAME:
				return getWebDriver().findElements(By.name(value));
			case PARTIAL_LINKTEXT:
				return getWebDriver().findElements(By.partialLinkText(value));
			case TAGNAME:
				return getWebDriver().findElements(By.tagName(value));
			case XPATH:
				return getWebDriver().findElements(By.xpath(value));
			default:
				System.err.println("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			System.err.println("The Elements with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			System.err.println("The Elements with locator:" + locatorType + " not found with value: " + value
					+ "\n Exception:" + e.getMessage());
		}
		return null;
	}

	public boolean verifyUrl(String expectedUrl) {
		if (getWebDriver().getCurrentUrl().equals(expectedUrl)) {
			System.out.println("The URL: " + expectedUrl + " matched successfully");
			return true;
		} else {
			System.err.println("The URL: " + expectedUrl + " not matched");
		}
		return false;
	}

	public boolean verifyTitle(String expectedTitle) {
		if (getWebDriver().getTitle().equals(expectedTitle)) {
			System.out.println("Page title: " + expectedTitle + " matched successfully");
			return true;
		} else {
			System.err.println("Page title: " + expectedTitle + " not matched");
		}
		return false;
	}

	public void closeDriver() {
		try {
			getWebDriver().close();
			System.out.println("Browser is closed");
		} catch (Exception e) {
			System.err.println("Browser cannot be closed " + e.getMessage());
		}
	}

	public void quitDriver() {
		try {
			getWebDriver().quit();
			System.out.println("Browser is closed");
		} catch (Exception e) {
			System.err.println("Browser cannot be closed " + e.getMessage());
		}
	}

	public void click(WebElement element) {
		try {
			element.isDisplayed();
		} catch (NoSuchElementException e) {
			System.err.println("The Element " + element + " is not found");
		}
		String text = "";
		try {
			try {
				JavaBase.sleep(500);
				getWait().until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				if (element.isEnabled()) {
					element.click();
				} else {
					getWebDriver().executeScript("arguments[0].click()", element);
				}
			} catch (Exception e) {
				boolean bFound = false;
				int totalTime = 0;
				while (!bFound && totalTime < 10000) {
					try {
						JavaBase.sleep(500);
						element.click();
						bFound = true;

					} catch (Exception e1) {
						bFound = false;
					}
					totalTime = totalTime + 500;
				}
				if (!bFound)
					element.click();
			}
		} catch (StaleElementReferenceException e) {
			System.err.println("The Element " + text + " could not be clicked due to:" + e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("The Element " + element + " could not be clicked due to: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("The Element " + element + " could not be clicked due to: " + e.getMessage());
		}
	}

	public void clearAndType(WebElement element, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(data);
		} catch (ElementNotInteractableException e) {
			System.err.println("The Element " + element + " is not Interactable \n" + e.getMessage());
		} catch (WebDriverException e) {
			System.err.println("The Element " + element + " did not allow to clear/type \n" + e.getMessage());
		}
	}

	public String getElementText(WebElement element) {
		try {
			String text = element.getText();
			System.err.println("Text has been retrieved " + text);
			return text;
		} catch (WebDriverException e) {
			System.err.println("Sorry! text is not available \n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Sorry! text is not available \n" + e.getMessage());
		}
		return null;
	}

	public String getBackgroundColor(WebElement element) {
		String cssValue = null;
		try {
			cssValue = element.getCssValue("color");
			System.err.println("The background color is " + cssValue);
		} catch (WebDriverException e) {
			System.err.println("Not able to get the background color \n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Not able to get the background color \n" + e.getMessage());
		}
		return cssValue;
	}

	public void selectDropDownUsingText(WebElement element, String value) {
		try {
			select = new Select(element);
			select.selectByVisibleText(value);
		} catch (WebDriverException e) {
			System.err.println("Not able to select the drop down with text \n" + value);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<WebElement> getDropDownOptions(WebElement element) {
		try {
			select = new Select(element);
			return select.getOptions();
		} catch (WebDriverException e) {
			System.err.println("Not able to get the dropdown options \n");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public boolean verifyExactText(WebElement element, String expectedText) {
		try {
			String text = element.getText();
			if (text.contains(expectedText)) {
				return true;
			} else {
				System.err.println("The expected text " + text + "doesn't equals to the  " + expectedText);
			}
		} catch (WebDriverException e) {
			System.err.println("Unknown exception occured while verifying the Text \n" + e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public boolean verifyExactAttribute(WebElement element, String attribute, String value) {
		try {
			if (element.getAttribute(attribute).equals(value)) {
				return true;
			} else {
				System.err.println(
						"The expected attribute :" + attribute + " value does not contains the actual " + value);
			}
		} catch (WebDriverException e) {
			System.err.println(
					"Unknown WebDriverException occured while verifying the Attribute Text \n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown Exception occured while verifying the Attribute Text \n" + e.getMessage());
		}
		return false;
	}

	public boolean verifyPartialAttribute(WebElement element, String attribute, String value) {
		try {
			if (element.getAttribute(attribute).contains(value)) {
				return true;
			} else {
				System.err.println(
						"The expected attribute :" + attribute + " value does not contains the actual " + value);
			}
		} catch (WebDriverException e) {
			System.err.println(
					"Unknown WebDriverException occured while verifying the Attribute Text \n" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown Exception occured while verifying the Attribute Text \n" + e.getMessage());
		}
		return false;
	}

	public boolean verifyDisplayed(WebElement element) {
		try {
			if (element.isDisplayed()) {
				return true;
			} else {
				System.err.println("The element " + element + " is not visible");
			}
		} catch (WebDriverException e) {
			System.err.println(
					"Unknown WebDriverException occured while trying to verify the element is displayed or not: \n"
							+ e.getMessage());
		} catch (Exception e) {
			System.err.println("Unknown Exception occured while trying to verify the element is displayed or not: \\n\""
					+ e.getMessage());
		}
		return false;
	}
	
	public int getElementSize(List<WebElement> element) {
		return element.size();
	}

	
}
