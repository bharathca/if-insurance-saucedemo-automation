package com.selenium.base;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	Properties properties;
	private Select select;
	public Logger log = LogManager.getLogger(SeleniumBaseImpl.class);

	public void startApplication(String url, boolean headless) {
		try {
			setWebDriver(Browsers.CHROME, headless);
			setWait();
			getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			getWebDriver().get(url);
			logInfo("The chrome browser is launched with the application url " + url);
		} catch (Exception e) {
			logError("Something went wrong. The chrome browser is not launched \n" + e.getMessage());
		}
	}

	public void startApplication(Browsers browser, boolean headless, String url) {
		try {
			setWebDriver(browser, headless);
			setWait();
			getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
			getWebDriver().get(url);
			logInfo("The " + browser.toString() + " browser is launched with the application url " + url);
		} catch (WebDriverException e) {
			logError("The " + browser.toString() + " browser is not launched due to WebDriverException. \n"
					+ e.getMessage());
		} catch (Exception e) {
			logError("The " + browser.toString() + " browser is not launched due to Exception. \n" + e.getMessage());
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
				logError("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			logError("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			logError("The Element with locator:" + locatorType + " not found with value: " + value + "\n Exception:"
					+ e.getMessage());
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
				logError("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			logError("The Element with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			logError("The Element with locator:" + locatorType + " not found with value: " + value + "\n Exception:"
					+ e.getMessage());
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
				logError("Invalid Locator is given. Locator: " + locatorType);
				break;
			}
		} catch (NoSuchElementException e) {
			logError("The Elements with locator:" + locatorType + " not found with value: " + value
					+ "\n NoSuchElement Exception:" + e.getMessage());
		} catch (Exception e) {
			logError("The Elements with locator:" + locatorType + " not found with value: " + value + "\n Exception:"
					+ e.getMessage());
		}
		return null;
	}

	public boolean verifyUrl(String expectedUrl) {
		if (getWebDriver().getCurrentUrl().equals(expectedUrl)) {
			logInfo("The URL: " + expectedUrl + " matched successfully");
			return true;
		} else {
			logError("The URL: " + expectedUrl + " not matched");
		}
		return false;
	}

	public boolean verifyTitle(String expectedTitle) {
		if (getWebDriver().getTitle().equals(expectedTitle)) {
			logInfo("Page title: " + expectedTitle + " matched successfully");
			return true;
		} else {
			logError("Page title: " + expectedTitle + " not matched");
		}
		return false;
	}

	public void closeDriver() {
		try {
			getWebDriver().close();
			logInfo("Browser is closed");
		} catch (Exception e) {
			logError("Browser cannot be closed " + e.getMessage());
		}
	}

	public void quitDriver() {
		try {
			getWebDriver().quit();
			logInfo("Browser is quit & closed");
		} catch (Exception e) {
			logError("Browser cannot be quit & closed " + e.getMessage());
		}
	}

	public void click(WebElement element) {
		try {
			element.isDisplayed();
		} catch (NoSuchElementException e) {
			logError("The Element " + element + " is not found");
		}
		String text = "";
		try {
			try {
				JavaBase.sleep(500);
				getWait().until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				if (element.isEnabled()) {
					element.click();
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
			logError("The Element " + text + " could not be clicked due to StaleElementReferenceException:\n"
					+ e.getMessage());
		} catch (WebDriverException e) {
			logError("The Element " + element + " could not be clicked due to WebDriverException:\n" + e.getMessage());
		} catch (Exception e) {
			logError("The Element " + element + " could not be clicked due to Exception:\n" + e.getMessage());
		}
	}

	public void clearAndType(WebElement element, String data) {
		try {
			getWait().until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(data);
		} catch (ElementNotInteractableException e) {
			logError("The Element " + element + " is not Interactable \n" + e.getMessage());
		} catch (WebDriverException e) {
			logError("The Element " + element + " did not allow to clear/type \n" + e.getMessage());
		}
	}

	public String getElementText(WebElement element) {
		String text = null;
		try {
			text = element.getText();
			return text;
		} catch (WebDriverException e) {
			logError("The Element " + element + "'s text could not be retrieved due to WebDriverException: \n"
					+ e.getMessage());
		} catch (Exception e) {
			logError("The Element " + element + "'s text could not be retrieved due to Exception: \n" + e.getMessage());
		}
		return text;
	}

	public String getBackgroundColor(WebElement element) {
		String cssValue = null;
		try {
			cssValue = element.getCssValue("color");
		} catch (WebDriverException e) {
			logError("The Element " + element
					+ "'s background color could not be retrieved due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("The Element " + element + "'s background color could not be retrieved due to Exception: \n"
					+ e.getMessage());
		}
		return cssValue;
	}

	public void selectDropDownUsingText(WebElement element, String value) {
		try {
			select = new Select(element);
			select.selectByVisibleText(value);
		} catch (WebDriverException e) {
			logError("Not able to select the drop down with text due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to select the drop down with text due to Exception: \n" + e.getMessage());
		}
	}

	public List<WebElement> getDropDownOptions(WebElement element) {
		try {
			select = new Select(element);
			return select.getOptions();
		} catch (WebDriverException e) {
			logError("Not able to get the dropdown options due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to get the dropdown options due to Exception: \n" + e.getMessage());
		}
		return null;
	}

	public boolean verifyExactText(WebElement element, String expectedText) {
		try {
			String actualText = element.getText();
			if (actualText.equals(expectedText)) {
				return true;
			} else {
				logError("The expected text " + expectedText + "does not match with the actual text " + actualText);
			}
		} catch (WebDriverException e) {
			logError("Not able to verify the element's text due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to verify the element's text due to Exception: \n" + e.getMessage());
		}
		return false;
	}

	public boolean verifyExactAttribute(WebElement element, String attribute, String value) {
		try {
			if (element.getAttribute(attribute).equals(value)) {
				return true;
			} else {
				logError("The expected attribute :" + attribute + "'s value does not match with the actual value " + value);
			}
		} catch (WebDriverException e) {
			logError("Not able to verify the exact attribute value due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to verify the exact attribute value due to Exception: \n" + e.getMessage());
		}
		return false;
	}

	public boolean verifyPartialAttribute(WebElement element, String attribute, String value) {
		try {
			if (element.getAttribute(attribute).contains(value)) {
				return true;
			} else {
				logError("The expected attribute :" + attribute + " value does not contains the actual " + value);
			}
		} catch (WebDriverException e) {
			logError("Not able to verify the attribute containing the value due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to verify the attribute containing the value due to Exception: \n" + e.getMessage());
		}
		return false;
	}

	public boolean verifyDisplayed(WebElement element) {
		try {
			if (element.isDisplayed()) {
				return true;
			} else {
				logError("The element " + element + " is not visible");
			}
		}catch (WebDriverException e) {
			logError("Not able to verify whether the element is displayed due to WebDriverException: \n" + e.getMessage());
		} catch (Exception e) {
			logError("Not able to verify whether the element is displayed due to Exception: \n" + e.getMessage());
		}
		return false;
	}

	public int getElementSize(List<WebElement> element) {
		return element.size();
	}

	public void logInfo(String message) {
		log.info(message);
	}

	public void logError(String message) {
		log.error(message);
	}
}
