package com.saucedemo.stepDefinitions;


import java.util.Map;

import com.github.javafaker.Faker;
import com.saucedemo.pages.CartPageObjects;
import com.saucedemo.pages.CheckoutCompletePageObjects;
import com.saucedemo.pages.CheckoutOverviewPageObjects;
import com.saucedemo.pages.CheckoutYourInformationPageObjects;
import com.saucedemo.pages.CommonPageObjects;
import com.saucedemo.pages.LoginPageObjects;
import com.saucedemo.pages.ProductsPageObjects;
import com.testng.base.ApplicationSpecificMethods;

public class BaseTest extends ApplicationSpecificMethods {
	CommonPageObjects commonPage = new CommonPageObjects(driver);
	LoginPageObjects loginPage = new LoginPageObjects(driver);
	ProductsPageObjects productsPage = new ProductsPageObjects(driver);
	CartPageObjects cartPage = new CartPageObjects(driver);
	CheckoutYourInformationPageObjects checkoutInfoPage = new CheckoutYourInformationPageObjects(driver);
	CheckoutCompletePageObjects checkoutCompletePage = new CheckoutCompletePageObjects(driver);
	CheckoutOverviewPageObjects checkoutOverview = new CheckoutOverviewPageObjects(driver);
	Faker faker = new Faker();
	
	private Map<String, Map<String, String>> productsAddedInCart;

	public Map<String, Map<String, String>> getProductsAddedInCart() {
		return productsAddedInCart;
	}

	public void setProductsAddedInCart(Map<String, Map<String, String>> productsAddedInCart) {
		this.productsAddedInCart = productsAddedInCart;
	}
}
