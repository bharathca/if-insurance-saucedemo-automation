package simpleTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class PurchaseOrder {

	public static void main(String[] args) {
		PurchaseOrder order = new PurchaseOrder();
		String applicationURL = "https://www.saucedemo.com/";
		String applicationInventoryURL = "https://www.saucedemo.com/inventory.html";
		String applicationCartURL = "https://www.saucedemo.com/cart.html";
		String inventoryPageSecondaryHeader = "Products";
		String cartSecondaryHeader = "Your Cart";

		// Instantiate the Chrome driver
		WebDriver driver = new ChromeDriver();

		// Maximize the browser window
		driver.manage().window().maximize();

		// Set an implicit wait of 30 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// Open the application URL
		driver.get(applicationURL);

		// Validate the landing page title
		order.validateLandingPageTitle(driver, "Swag Labs");

		// Perform login
		order.login(driver, "standard_user", "secret_sauce");

		// Verify landing on the inventory page
		order.verifyInventoryPage(driver, applicationInventoryURL, inventoryPageSecondaryHeader);

		// Get the total number of products in the inventory
		List<WebElement> inventoryItems = driver.findElements(By.xpath("//div[@data-test='inventory-item']"));
		int numberOfProductsAvailable = inventoryItems.size();

		// Randomly select multiple products to add to cart
		int randomNumberOfProductsToAdd = order.generateRandomNumber(numberOfProductsAvailable, "2ToInputRange");

		// Get the list of available products
		List<String> availableProducts = order.fetchAvailableProducts(driver);

		// Add random products to cart and store their details
		Map<String, Map<String, String>> addedProducts = order.addProductsToCart(driver, availableProducts,
				randomNumberOfProductsToAdd);

		// Check the cart icon for correct number of added products
		order.verifyCartIcon(driver, randomNumberOfProductsToAdd);

		// Navigate to the cart page
		order.navigateToCartPage(driver);

		// Verify landing on the cart page
		order.verifyCartPage(driver, applicationCartURL, cartSecondaryHeader);

		// Check the products in the cart
		order.verifyCartContents(driver, addedProducts);

		order.continueToCheckOut(driver);

		order.enterCheckoutDetails(driver);

		order.verifyCartContents(driver, addedProducts);
		order.checkPaymentInfomation(driver, addedProducts);
		order.checkoutComplete(driver);
		// Close the browser
//        driver.quit();
	}

	public void validateLandingPageTitle(WebDriver driver, String expectedTitle) {
		String actualTitle = driver.getTitle();
		if (actualTitle.equals(expectedTitle)) {
			System.out.println("Page title is correct");
		} else {
			System.err.println("Page title is incorrect. Expected: " + expectedTitle + ", Actual: " + actualTitle);
		}
	}

	public void login(WebDriver driver, String username, String password) {
		WebElement usernameField = driver.findElement(By.id("user-name"));
		WebElement passwordField = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.id("login-button"));

		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		loginButton.click();
	}

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

	public int generateRandomNumber(int range, String option) {
		Random random = new Random();
		switch (option) {
		case "2ToInputRange":
			return random.nextInt(range - 1) + 2;
		case "0ToInputRange":
			return random.nextInt(range + 1);
		default:
			return -1;
		}
	}

	public List<String> fetchAvailableProducts(WebDriver driver) {
		List<String> products = new ArrayList<>();
		List<WebElement> productElements = driver
				.findElements(By.xpath("//div[@data-test='inventory-item']//div[@data-test='inventory-item-name']"));

		for (WebElement element : productElements) {
			products.add(element.getText());
		}
		return products;
	}

	public Map<String, Map<String, String>> addProductsToCart(WebDriver driver, List<String> products,
			int numberOfProducts) {
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			indices.add(i);
		}
		Collections.shuffle(indices);

		Map<String, Map<String, String>> addedProducts = new LinkedHashMap<>();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		for (int i = 0; i < numberOfProducts; i++) {
			int index = indices.get(i);
			String productName = products.get(index);

			WebElement productElement = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//div[text()='" + productName + "']/ancestor::div[@class='inventory_item'])")));

			WebElement addToCartButton = productElement.findElement(By.xpath(".//button[text()='Add to cart']"));
			WebElement productDescriptionElement = productElement
					.findElement(By.xpath(".//div[@data-test='inventory-item-desc']"));
			WebElement productPriceElement = productElement
					.findElement(By.xpath(".//div[@data-test='inventory-item-price']"));

			String productPrice = productPriceElement.getText();
			String productDescription = productDescriptionElement.getText();

			Map<String, String> productDetails = new LinkedHashMap<>();
			productDetails.put("price", productPrice);
			productDetails.put("description", productDescription);

			addedProducts.put(productName, productDetails);

			addToCartButton.click();
		}
		return addedProducts;
	}

	public void verifyCartIcon(WebDriver driver, int expectedCount) {
		String cartValue = driver.findElement(By.xpath("//span[@data-test='shopping-cart-badge']")).getText();
		if (Integer.valueOf(cartValue) == expectedCount) {
			System.out.println("Cart icon displays the correct number of items");
		} else {
			System.err.println("Cart icon displays incorrect number of items. Expected: " + expectedCount + ", Actual: "
					+ cartValue);
		}
	}

	public void navigateToCartPage(WebDriver driver) {
		driver.findElement(By.xpath("//a[@data-test='shopping-cart-link']")).click();
	}

	public void verifyCartPage(WebDriver driver, String expectedURL, String expectedHeader) {
		if (driver.getCurrentUrl().equals(expectedURL)
				&& driver.findElement(By.xpath("//span[@data-test='title']")).getText().equals(expectedHeader)) {
			System.out.println("Successfully navigated to the cart page");
		} else {
			System.err.println("Navigation to the cart page failed");
		}
	}

	public void verifyCartContents(WebDriver driver, Map<String, Map<String, String>> expectedProducts) {
		int expectedCount = expectedProducts.size();
		int actualCount = driver.findElements(By.className("cart_item")).size();

		if (expectedCount == actualCount) {
			System.out.println("The number of products in the cart matches the expected count");
		} else {
			System.err.println("Product count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
		}

		List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
		for (WebElement item : cartItems) {
			String cartName = item.findElement(By.className("inventory_item_name")).getText();
			String cartPrice = item.findElement(By.className("inventory_item_price")).getText();
			String cartDescription = item.findElement(By.className("inventory_item_desc")).getText();

			if (expectedProducts.containsKey(cartName)) {
				Map<String, String> productDetails = expectedProducts.get(cartName);

				if (productDetails.get("price").equals(cartPrice)
						&& productDetails.get("description").equals(cartDescription)) {
					System.out.println("Product verified in cart: " + cartName + " with price: " + cartPrice
							+ " and description: " + cartDescription);
				} else {
					System.err.println("Product verification failed for: " + cartName + " with price: " + cartPrice
							+ " and description: " + cartDescription);
				}
			} else {
				System.err.println("Product not found in the expected products list: " + cartName);
			}
		}
	}

	public void continueToCheckOut(WebDriver driver) {
		driver.findElement(By.xpath("//button[@data-test='checkout']")).click();
	}

	public void verifyCheckOutPage(WebDriver driver) {
		if (driver.findElement(By.xpath("//span[@data-test='title']")).getText().equals("Checkout: Your Information")) {
			System.out.println("Successfully navigated to the Checkout page");
		} else {
			System.out.println("Navigation to the checkout page is failed");
		}
	}

	public void enterCheckoutDetails(WebDriver driver) {
		Faker faker = new Faker();

		driver.findElement(By.id("first-name")).sendKeys(faker.name().firstName());
		driver.findElement(By.id("last-name")).sendKeys(faker.name().lastName());
		driver.findElement(By.id("postal-code")).sendKeys(faker.address().zipCode());
		driver.findElement(By.id("continue")).click();
	}

	public void checkoutOverView(WebDriver driver) {
		if (driver.findElement(By.xpath("//span[@data-test='title']")).getText().equals("Checkout: Overview")) {
			System.out.println("Successfully navigated to the Checkout Overview Page");
		} else {
			System.out.println("Navigation to the checkout Overview page is failed");
		}
	}

	public void checkPaymentInfomation(WebDriver driver, Map<String, Map<String, String>> addedProducts) {
		WebElement sauceCardElement = driver.findElement(By.xpath("//div[@data-test='payment-info-value']"));
		WebElement priceElement = driver.findElement(By.xpath("//div[@data-test='subtotal-label']"));
		WebElement taxElement = driver.findElement(By.xpath("//div[@data-test='tax-label']"));
		WebElement totalPriceElement = driver.findElement(By.xpath("//div[@data-test='total-label']"));
		String sauceCard = sauceCardElement.getText();
		float priceTotal = Float.parseFloat(priceElement.getText().replaceAll("[^\\d.]", ""));
		float tax = Float.parseFloat(taxElement.getText().replaceAll("[^\\d.]", ""));
		float totalPrice = Float.parseFloat(totalPriceElement.getText().replaceAll("[^\\d.]", ""));
		float expectedTotalPriceWithoutTax = 0f;

		for (Map.Entry<String, Map<String, String>> entry : addedProducts.entrySet()) {
			Map<String, String> productDetails = entry.getValue();

			String price = productDetails.get("price");
			float productPrice = Float.parseFloat(price.replace("$", ""));
			expectedTotalPriceWithoutTax += productPrice;
		}
		boolean containsPattern = sauceCard.matches("SauceCard #\\d+");
		if (containsPattern) {
			System.out.println("The text contains the pattern 'SauceCard #' followed by numbers.");
		} else {
			System.out.println("The text does not contain the specified pattern.");
		}

		if (priceTotal == expectedTotalPriceWithoutTax) {
			System.out.println("Total price without tax is correct");
		} else {
			System.out.println("Total price without tax is incorrect");
		}
		double actualTax = totalPrice - priceTotal;
		if (actualTax == tax) {
			System.out.println("Tax price is also correct");
		} else {
			System.out.println("Tax price is  incorrect");
		}
		driver.findElement(By.id("finish")).click();
	}

	public void checkoutComplete(WebDriver driver) {
		if (driver.findElement(By.xpath("//span[@data-test='title']")).getText().equals("Checkout: Complete!")) {
			System.out.println("Successfully navigated to the Checkout Overview Page");
		} else {
			System.out.println("Navigation to the checkout Overview page is failed");
		}

		boolean displayed = driver.findElement(By.xpath("//h2[@data-test='complete-header']")).isDisplayed();
		System.out.println(displayed);
	}

}
