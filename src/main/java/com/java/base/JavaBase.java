package com.java.base;

import java.util.Random;

import com.java.design.RandomNumberOptions;

public class JavaBase {
	
	public static int generateRandomNumber(int range, RandomNumberOptions option) {
		Random random = new Random();
		switch (option) {
		case zeroToInputRange:
			return random.nextInt(range + 1);
		case twoToInputRange:
			return random.nextInt(range - 1) + 2;
		default:
			return -1;
		}
	}
	
	public static String dynamicLocatorGenerator(String locator, String replaceText) {
		return locator.replace("replaceText", replaceText);
	}
	
	public static void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
