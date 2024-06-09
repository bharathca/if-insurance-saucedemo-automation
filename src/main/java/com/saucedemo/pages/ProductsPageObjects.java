package com.saucedemo.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.java.base.JavaBase;
import com.java.design.RandomNumberOptions;
import com.selenium.design.Locators;
import com.testng.base.ApplicationSpecificMethods;

public class ProductsPageObjects extends ApplicationSpecificMethods {
	// Locators - xPath
	private final String productElement = "(//div[text()='replaceText']/ancestor::div[@class='inventory_item'])";
	private final String productsAvailableElement = "//div[@data-test='inventory-item']";
	private final String productNamesElement = "//div[@data-test='inventory-item']//div[@data-test='inventory-item-name']";
	private final String productNameElement = ".//div[@data-test='inventory-item-name']";
	private final String productDescriptionElement = ".//div[@data-test='inventory-item-desc']";
	private final String productPriceElement = ".//div[@data-test='inventory-item-price']";
	private final String addToCartButtonElement = ".//button[text()='Add to cart']";
	private final String sortingDropDownElement = "//select[@data-test='product-sort-container']";

	// Variables
	private int totalRandomNumberOfProducts;

	public ProductsPageObjects(WebDriver driver) {
		this.driver = driver;
	}

	public int getTotalNumberOfRandomProducts() {
		return totalRandomNumberOfProducts;
	}

	public int getTotalNumberOfAvailableProducts() {
		return locateElements(Locators.XPATH, productsAvailableElement).size();
	}

	public int getRandomNumberOfProducts() {
		totalRandomNumberOfProducts = JavaBase.generateRandomNumber(getTotalNumberOfAvailableProducts(),
				RandomNumberOptions.twoToInputRange);
		return totalRandomNumberOfProducts;
	}

	public List<String> getNamesOfAvailableProducts() {
		List<String> productNames = new LinkedList<String>();
		List<WebElement> productElements = locateElements(Locators.XPATH, productNamesElement);
		for (WebElement productElement : productElements) {
			productNames.add(productElement.getText());
		}
		return productNames;
	}

	// Method to fetch available products and their details
	public Map<String, Map<String, String>> getProductDetails() {
		Map<String, Map<String, String>> productsInfo = new LinkedHashMap<>();

		List<WebElement> productElements = locateElements(Locators.XPATH, productsAvailableElement);

		for (WebElement productElement : productElements) {
			Map<String, String> productDetails = new LinkedHashMap<>();

			String productName = getElementText(locateElement(Locators.XPATH, productElement, productNameElement));
			String productPrice = getElementText(locateElement(Locators.XPATH, productElement, productPriceElement));
			String productDescription = getElementText(
					locateElement(Locators.XPATH, productElement, productDescriptionElement));

			productDetails.put("price", productPrice);
			productDetails.put("description", productDescription);
			productsInfo.put(productName, productDetails);
		}
		return productsInfo;
	}

	public Map<String, Map<String, String>> addProductsToCart(List<String> productNames,
			int totalRandomNumberOfProducts, boolean randomOrder) {
		// This list helps to add the product uniquely
		List<Integer> indices = new ArrayList<>();
		Map<String, Map<String, String>> productsAddedToCart = new LinkedHashMap<>();

		// Gets the position of the available products
		for (int i = 0; i < productNames.size(); i++) {
			indices.add(i);
		}
		if (randomOrder) {
			// Shuffles the position of them
			Collections.shuffle(indices);
		}
		for (int i = 0; i < totalRandomNumberOfProducts; i++) {
			int index = indices.get(i);
			String productName = productNames.get(index);
			WebElement actualProductElement = locateElement(Locators.XPATH,
					JavaBase.dynamicLocatorGenerator(productElement, productName));
			String productPrice = getElementText(
					locateElement(Locators.XPATH, actualProductElement, productPriceElement));
			String productDescription = getElementText(
					locateElement(Locators.XPATH, actualProductElement, productDescriptionElement));
			Map<String, String> productDetails = new LinkedHashMap<>();
			productDetails.put("price", productPrice);
			productDetails.put("description", productDescription);
			productsAddedToCart.put(productName, productDetails);
			click(locateElement(Locators.XPATH, actualProductElement, addToCartButtonElement));
			logInfo(productName+" product is added to the cart");
		}
		return productsAddedToCart;
	}

	// Method to get sorting options from the dropdown
	public List<WebElement> getSortingOptionElements() {
		return getDropDownOptions(locateElement(Locators.XPATH, sortingDropDownElement));
	}

	// Method to sort products based on the given order
	public void sortProducts(String sortingOrder) {
		WebElement sortingDropdownElements = locateElement(Locators.XPATH, sortingDropDownElement);
		getWait().until(ExpectedConditions.elementToBeClickable(sortingDropdownElements));
		selectDropDownUsingText(sortingDropdownElements, sortingOrder);
	}

	// Method to perform sorting and validation for different sorting orders and add them in the cart
	public Map<String, Map<String, String>> sortAndAddProductsInCart(String sortingOrder) {
		try {
			List<WebElement> sortingOptions = getSortingOptionElements();
			List<String> sortingOptionTexts = sortingOptions.stream().map(WebElement::getText)
					.collect(Collectors.toList());

			// Check if the expected sorting order is available
			if (sortingOptionTexts.contains(sortingOrder)) {
				// Fetch available products before sorting
				Map<String, Map<String, String>> beforeSortingProducts = getProductDetails();

				// Extract product names and prices before sorting
				List<String> productNamesBeforeSorting = new ArrayList<>(beforeSortingProducts.keySet());
				List<Double> productPricesBeforeSorting = beforeSortingProducts.values().stream()
						.map(details -> Double.parseDouble(details.get("price").replace("$", "")))
						.collect(Collectors.toList());

				// Sort products based on the given order
				sortProducts(sortingOrder);

				// Fetch available products after sorting
				Map<String, Map<String, String>> afterSortingProducts = getProductDetails();

				// Extract product names and prices after sorting
				List<String> productNamesAfterSorting = new ArrayList<>(afterSortingProducts.keySet());
				List<Double> productPricesAfterSorting = afterSortingProducts.values().stream()
						.map(details -> Double.parseDouble(details.get("price").replace("$", "")))
						.collect(Collectors.toList());

				if (sortingOrder.contains("Name")) {
					validateProductsNamesAfterSorting(sortingOrder, productNamesBeforeSorting,
							productNamesAfterSorting);
				} else {
					validateProductsPriceAfterSorting(sortingOrder, productPricesBeforeSorting,
							productPricesAfterSorting);
				}

				return addProductsToCart(productNamesAfterSorting, getRandomNumberOfProducts(), false);

			} else {
				logError("The given sorting order "+sortingOrder+" is not there in the sorting dropdown");
			}
		} catch (Exception e) {
			logError("There is an issue while sorting the products & validating them "+e.getMessage());
		}
		return null;
	}

	public void validateProductsNamesAfterSorting(String sortingOrder, List<String> productNamesBeforeSorting,
			List<String> productNamesAfterSorting) {
		// Validate sorting order
		switch (sortingOrder) {
		case "Name (A to Z)":
			Collections.sort(productNamesBeforeSorting);
			break;
		case "Name (Z to A)":
			Collections.sort(productNamesBeforeSorting, Collections.reverseOrder());
			break;
		default:
			logError("The given sorting order "+sortingOrder+" for product names are not there in the sorting dropdown");
			break;
		}

		if (productNamesBeforeSorting.equals(productNamesAfterSorting)) {
			logInfo("The given sorting order "+sortingOrder+" for product names are working with no issues");
		} else {
			logError("The given sorting order "+sortingOrder+" for product names are not working fine");
		}
	}

	public void validateProductsPriceAfterSorting(String sortingOrder, List<Double> productPricesBeforeSorting,
			List<Double> productPricesAfterSorting) {
		// Validate sorting order
		switch (sortingOrder) {
		case "Price (low to high)":
			Collections.sort(productPricesBeforeSorting);
			break;
		case "Price (high to low)":
			Collections.sort(productPricesBeforeSorting, Collections.reverseOrder());
			break;
		default:
			logError("The given sorting order "+sortingOrder+" for product prices are not there in the sorting dropdown");
			break;
		}

		if (productPricesBeforeSorting.equals(productPricesAfterSorting)) {
			logInfo("The given sorting order "+sortingOrder+" for product price are working fine");
		} else {
			logError("The given sorting order "+sortingOrder+" for product price are not working fine");
		}
	}
}
