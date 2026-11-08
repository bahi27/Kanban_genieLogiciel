package org.projest.app.it;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IssueIT {

	static WebDriver driver;
	static String host, port;

	@BeforeAll
	static void setupWebDriver() {
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
	static void shutdownWebDriver() {
		if (driver != null) {
			driver.quit() ; 
			driver = null ; 
		}
	}

	// ----- mots-clés -----

	static String baseUrl() {
		return "http://" + host + ":" + port + "/";
	}

	static List<WebElement> listIssues() {
		driver.get(baseUrl() + "list");                              // 1
		return driver.findElements(By.className("issue_row"));            // 2
	}

	static void createIssue(String title) {
		driver.get(baseUrl() + "list");
		driver.findElement(By.id("title")).sendKeys(title);             // 3, 4
		driver.findElement(By.id("create")).click();                   // 5
	}

	// ----- tests -----

	@Test
	void createdIssueAppearsInList() {
		String title = "issue-" + System.currentTimeMillis();
		createIssue(title);
		boolean found = listIssues().stream()
				.anyMatch(e -> e.getText().equals(title));            // 6
		assertTrue(found, "L'issue créée n'apparaît pas dans la liste");
	}

	@Test
	void creatingIssueAddsOneRow() {
		int before = listIssues().size();
		createIssue("autre-" + System.currentTimeMillis());
		int after = listIssues().size();
		assertEquals(before + 1, after);                          // 7
	}
}