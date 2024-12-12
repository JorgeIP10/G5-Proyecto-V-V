package org.springframework.samples.petclinic.PruebasFuncionales;

// Registrar OWNER - LA PRUEBA FUNCIONA
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

public class AddOwnerFunctionalTest {

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
	public void addOwnerFunctional() throws InterruptedException {
		driver.get("http://localhost:8081/owners/find");
		driver.manage().window().setSize(new Dimension(1920, 982));
		driver.findElement(By.linkText("Add Owner")).click();
		driver.findElement(By.id("firstName")).click();
		driver.findElement(By.id("firstName")).sendKeys("Jim");
		driver.findElement(By.id("lastName")).sendKeys("Cju");
		driver.findElement(By.id("address")).sendKeys("625 ");
		driver.findElement(By.id("address")).sendKeys("625 Cardinal");
		driver.findElement(By.id("address")).sendKeys("625 Cardinal Ave");
		driver.findElement(By.id("city")).sendKeys("Sun");
		driver.findElement(By.id("city")).sendKeys("Sun Praire");
		driver.findElement(By.id("telephone")).click();
		driver.findElement(By.id("telephone")).click();
		driver.findElement(By.id("telephone")).click();
		driver.findElement(By.id("telephone")).sendKeys("6085551029");
		Thread.sleep(500);
		driver.findElement(By.cssSelector(".btn")).click();
		Thread.sleep(500);
		driver.findElement(By.id("success-message")).click();
		assertEquals(driver.findElement(By.cssSelector("#success-message > span")).getText(), "New Owner Created");
	}

}
