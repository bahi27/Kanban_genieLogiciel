package org.projest.app.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IssueIT {

	static WebDriver driver;
	static String host, port;

	@BeforeAll
	static void setupWebDriver() {
		if (driver != null) return;
		host = System.getProperty("host", "localhost");
		port = System.getProperty("servlet.port", "8080");
		try {
			WebDriverManager.firefoxdriver().setup();
		} catch (Exception e) {
			// sur GitHub, Selenium Manager prend le relais
		}
		FirefoxOptions options = new FirefoxOptions();
		if (System.getProperty("headless") != null || System.getenv("CI") != null) {
			options.addArguments("-headless");
		}
		driver = new FirefoxDriver(options);
	}

	@AfterAll
	static void shutdownWebDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	// ----- mots-clés -----

	static String baseUrl() {
		return "http://" + host + ":" + port + "/";
	}

	static List<WebElement> listIssues() {
		driver.get(baseUrl() + "list");
		return driver.findElements(By.className("issue_row"));
	}

	static void createIssue(String title) {
		driver.get(baseUrl() + "list");
		driver.findElement(By.id("title")).sendKeys(title);
		WebElement button = driver.findElement(By.id("create"));
		button.click();
		// attendre que la page soit rechargée (l'ancien bouton disparaît)
		new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.stalenessOf(button));
	}

	// ----- tests -----

	@Test
	void createdIssueAppearsInList() {
		String title = "issue-" + System.currentTimeMillis();
		createIssue(title);
		boolean found = listIssues().stream()
				.anyMatch(e -> e.getText().equals(title));
		assertTrue(found, "L'issue créée n'apparaît pas dans la liste");
	}

	@Test
	void creatingIssueAddsOneRow() {
		int before = listIssues().size();
		createIssue("autre-" + System.currentTimeMillis());
		int after = listIssues().size();
		assertEquals(before + 1, after);
	}
}