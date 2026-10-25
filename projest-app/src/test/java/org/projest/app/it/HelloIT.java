package org.projest.app.it;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HelloIT {

	public static WebDriver driver;
	private static String host, port;

	@BeforeAll
	public static void setupWebDriver() {
		if (driver != null) return;
		host = System.getProperty("host", "localhost");
		port = System.getProperty("servlet.port", "8080");
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions options = new FirefoxOptions();
		if (System.getProperty("headless") != null) {
			options.addArguments("-headless");
		}
		driver = new FirefoxDriver(options);
	}

	@AfterAll
	public static void shutdownWebDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public static String getBaseUrl() {
		return "http://" + host + ":" + port + "/";
	}

	@Test
	public void testName() {
		String testName = "testname";
		driver.get(getBaseUrl() + "hello?name=" + testName);
		assertTrue(driver.getPageSource().contains(testName),
				"Sent name not found in page");
	}
}