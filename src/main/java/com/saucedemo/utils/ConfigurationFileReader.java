package com.saucedemo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationFileReader {
	private static ConfigurationFileReader instance;
	private Properties properties;
	public Logger log = LogManager.getLogger(ConfigurationFileReader.class);


	private ConfigurationFileReader() {
		properties = new Properties();
		try {
			FileInputStream fileInput = new FileInputStream("./src/test/resources/configurations/default.properties");
			properties.load(fileInput);
			log.info("Properties file is loaded");
		} catch (FileNotFoundException e) {
			log.error("Properties file is not loaded as the file is not found");
		} catch (IOException e) {
			log.error("Properties file is not loaded due to IO Exception");
		}
	}

	public static ConfigurationFileReader getInstance() {
		if (instance == null) {
			synchronized (ConfigurationFileReader.class) {
				if (instance == null) {
					instance = new ConfigurationFileReader();
				}
			}
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	public Properties getProperties() {
		return properties;
	}
}
