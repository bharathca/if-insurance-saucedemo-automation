package simpleTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SortProductItems {

    public static void main(String[] args) {
        // Initialize the SortProductItems instance
        SortProductItems items = new SortProductItems();

        // Define application URLs and headers
        String applicationURL = "https://www.saucedemo.com/";
        String applicationInventoryURL = "https://www.saucedemo.com/inventory.html";
        String inventoryPageSecondaryHeader = "Products";

        // Instantiate the Chrome driver
        WebDriver driver = new ChromeDriver();

        // Maximize the browser window and set an implicit wait of 30 seconds
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        // Explicit wait of 15 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Open the application URL
        driver.get(applicationURL);

        // Validate the landing page title
        items.validateLandingPageTitle(driver, "Swag Labs");

        // Perform login with standard credentials
        items.login(driver, "standard_user", "secret_sauce");

        // Verify navigation to the inventory page
        items.verifyInventoryPage(driver, applicationInventoryURL, inventoryPageSecondaryHeader);

        // Perform sorting and validation for different sorting orders
        items.sortAndValidate(driver, wait);

        // Close the browser
        driver.quit();
    }

    // Method to validate the landing page title
    public void validateLandingPageTitle(WebDriver driver, String expectedTitle) {
        String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Page title is correct");
        } else {
            System.err.println("Page title is incorrect. Expected: " + expectedTitle + ", Actual: " + actualTitle);
        }
    }

    // Method to log into the application
    public void login(WebDriver driver, String username, String password) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    // Method to verify navigation to the inventory page
    public void verifyInventoryPage(WebDriver driver, String expectedURL, String expectedHeader) {
        String actualURL = driver.getCurrentUrl();
        WebElement headerElement = driver.findElement(By.xpath("//span[@data-test='title']"));
        String actualHeader = headerElement.getText();
        if (actualURL.equals(expectedURL) && actualHeader.equals(expectedHeader)) {
            System.out.println("Successfully navigated to the inventory page");
        } else {
            System.err.println("Navigation to the inventory page failed");
        }
    }

    // Method to fetch available products and their details
    public Map<String, Map<String, String>> fetchAvailableProducts(WebDriver driver) {
        Map<String, Map<String, String>> productsInfo = new LinkedHashMap<>();

        List<WebElement> productElements = driver.findElements(By.xpath("//div[@data-test='inventory-item']"));

        for (WebElement element : productElements) {
            Map<String, String> productDetails = new LinkedHashMap<>();
            String productName = element.findElement(By.xpath(".//div[@data-test='inventory-item-name']")).getText();
            String productPrice = element.findElement(By.xpath(".//div[@data-test='inventory-item-price']")).getText();
            String productDescription = element.findElement(By.xpath(".//div[@data-test='inventory-item-desc']")).getText();
            productDetails.put("price", productPrice);
            productDetails.put("description", productDescription);
            productsInfo.put(productName, productDetails);
        }
        return productsInfo;
    }

    // Method to get sorting options from the dropdown
    public List<WebElement> getSortingOptions(WebDriver driver) {
        WebElement selectSorting = driver.findElement(By.xpath("//select[@data-test='product-sort-container']"));
        Select select = new Select(selectSorting);
        return select.getOptions();
    }

    // Method to sort products based on the given order
    public void sortProducts(WebDriver driver, String sortingOrder, WebDriverWait wait) {
        WebElement selectSortingDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@data-test='product-sort-container']")));
        wait.until(ExpectedConditions.elementToBeClickable(selectSortingDropdown));
        Select select = new Select(selectSortingDropdown);
        select.selectByVisibleText(sortingOrder);
    }

    // Method to perform sorting and validation for different sorting orders
    public void sortAndValidate(WebDriver driver, WebDriverWait wait) {
        // Define the sorting orders to test
        List<String> sortingOrders = List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)");

        // Loop through each sorting order
        for (String sortingOrder : sortingOrders) {
            try {
                // Re-locate sorting options to avoid stale element reference
                List<WebElement> sortingOptions = getSortingOptions(driver);
                List<String> sortingOptionTexts = sortingOptions.stream()
                                                               .map(WebElement::getText)
                                                               .collect(Collectors.toList());

                // Check if the expected sorting order is available
                if (sortingOptionTexts.contains(sortingOrder)) {
                    // Fetch available products before sorting
                    Map<String, Map<String, String>> beforeSortingProducts = fetchAvailableProducts(driver);

                    // Extract product names and prices before sorting
                    List<String> productNamesBeforeSorting = new ArrayList<>(beforeSortingProducts.keySet());
                    List<Double> productPricesBeforeSorting = beforeSortingProducts.values().stream()
                            .map(details -> Double.parseDouble(details.get("price").replace("$", "")))
                            .collect(Collectors.toList());

                    // Sort products based on the given order
                    sortProducts(driver, sortingOrder, wait);

                    // Fetch available products after sorting
                    Map<String, Map<String, String>> afterSortingProducts = fetchAvailableProducts(driver);

                    // Extract product names and prices after sorting
                    List<String> productNamesAfterSorting = new ArrayList<>(afterSortingProducts.keySet());
                    List<Double> productPricesAfterSorting = afterSortingProducts.values().stream()
                            .map(details -> Double.parseDouble(details.get("price").replace("$", "")))
                            .collect(Collectors.toList());

                    // Validate sorting order
                    switch (sortingOrder) {
                        case "Name (A to Z)":
                            Collections.sort(productNamesBeforeSorting);
                            if (productNamesBeforeSorting.equals(productNamesAfterSorting)) {
                                System.out.println("Sorting Order: " + sortingOrder + " works fine");
                            } else {
                                System.out.println("Sorting Order: " + sortingOrder + " is not working fine");
                            }
                            break;
                        case "Name (Z to A)":
                            Collections.sort(productNamesBeforeSorting, Collections.reverseOrder());
                            if (productNamesBeforeSorting.equals(productNamesAfterSorting)) {
                                System.out.println("Sorting Order: " + sortingOrder + " works fine");
                            } else {
                                System.out.println("Sorting Order: " + sortingOrder + " is not working fine");
                            }
                            break;
                        case "Price (low to high)":
                            Collections.sort(productPricesBeforeSorting);
                            if (productPricesBeforeSorting.equals(productPricesAfterSorting)) {
                                System.out.println("Sorting Order: " + sortingOrder + " works fine");
                            } else {
                                System.out.println("Sorting Order: " + sortingOrder + " is not working fine");
                            }
                            break;
                        case "Price (high to low)":
                            Collections.sort(productPricesBeforeSorting, Collections.reverseOrder());
                            if (productPricesBeforeSorting.equals(productPricesAfterSorting)) {
                                System.out.println("Sorting Order: " + sortingOrder + " works fine");
                            } else {
                                System.out.println("Sorting Order: " + sortingOrder + " is not working fine");
                            }
                            break;
                        default:
                            System.err.println("The given sorting order is not there in the sorting dropdown");
                            break;
                    }
                } else {
                    System.err.println("The given sorting order is not there in the sorting dropdown");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
