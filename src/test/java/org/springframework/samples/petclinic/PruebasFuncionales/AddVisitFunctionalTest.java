package org.springframework.samples.petclinic.PruebasFuncionales;

// REGISTRAR VISITA - LA PRUEBA FUNCIONA CON LA FECHA POR DEFECTO
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

public class AddVisitFunctionalTest {

	private WebDriver driver;

	private Map<String, Object> vars;

	JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--remote-debugging-port=9222"); // Asegura la comunicación
		driver = new ChromeDriver(options);
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void addVisitFunctional() throws InterruptedException {
		/*
		 * driver.get("http://localhost:8081/"); driver.manage().window().setSize(new
		 * Dimension(1920, 982));
		 * driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click(
		 * ); driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		 * driver.findElement(By.linkText("Jorge Frankli")).click();
		 */

		driver.get("http://localhost:8081/owners/1");
		Thread.sleep(500);
		driver.findElement(By.linkText("Add Visit")).click();
		// driver.findElement(By.id("date")).click();
		// driver.findElement(By.id("date")).sendKeys("2026-12-12");
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).sendKeys("vacunacion");
		driver.findElement(By.cssSelector(".btn")).click();
		Thread.sleep(500);
		assertEquals(driver.findElement(By.cssSelector("#success-message > span")).getText(),
				"Your visit has been booked");
	}

}
