package com.saucedemo.testRunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" }, monochrome = true, glue = {
		"com.saucedemo.stepDefinitions" }, plugin = { "pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })
public class TestNGRunner extends AbstractTestNGCucumberTests {
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}