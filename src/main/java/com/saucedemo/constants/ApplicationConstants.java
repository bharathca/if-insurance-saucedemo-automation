package com.saucedemo.constants;

public enum ApplicationConstants {
	
	APPLICATION_PAGE_TITLE("Swag Labs"),
	PRODUCTS_PAGE_HEADER("Products"),
	CART_PAGE_HEADER("Your Cart"),
	CHECKOUT_STEP_ONE_PAGE_HEADER("Checkout: Your Information"),
	CHECKOUT_STEP_TWO_PAGE_HEADER("Checkout: Overview"),
	CHECKOUT_COMPLETE_PAGE_HEADER("Checkout: Complete!"),
	USERNAME_REQUIRED("Epic sadface: Username is required"),
	PASSWORD_REQUIRED("Epic sadface: Password is required"),
	FIRST_NAME_REQUIRED("Error: First Name is required"),
	LAST_NAME_REQUIRED("Error: Last Name is required"),
	POSTAL_CODE_REQUIRED("Error: Postal Code is required"),
	LOCKED_OUT_USER("Epic sadface: Sorry, this user has been locked out."),
	INCORRECT_USER("Epic sadface: Username and password do not match any user in this service");
	
	private final String message;

	ApplicationConstants(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
