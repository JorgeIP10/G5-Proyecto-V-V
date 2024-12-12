package org.springframework.samples.petclinic.PruebasFuncionales;

//EDITA PET - SI FUNCIONA LA PRUEBA (sin modificar fecha)
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

public class EditPetFunctionalTest {

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
	public void editPetFunctional() throws InterruptedException {
		/*
		 * driver.get("http://localhost:8081/"); driver.manage().window().setSize(new
		 * Dimension(1920, 982));
		 * driver.findElement(By.cssSelector(".nav-item:nth-child(2) > .nav-link")).click(
		 * ); driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		 * driver.findElement(By.linkText("Jorge Frankli")).click();
		 */

		driver.get("http://localhost:8081/owners/1");
		Thread.sleep(1000);
		driver.findElement(By.linkText("Edit Pet")).click();
		WebElement nameField = driver.findElement(By.id("name"));
		nameField.clear();
		nameField.sendKeys("dogito");
		// driver.findElement(By.id("birthDate")).click();
		// driver.findElement(By.id("birthDate")).sendKeys("2010-09-08");
		{
			WebElement dropdown = driver.findElement(By.id("type"));
			dropdown.findElement(By.xpath("//option[. = 'dog']")).click();
		}
		{
			WebElement element = driver.findElement(By.id("type"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).clickAndHold().perform();
		}
		{
			WebElement element = driver.findElement(By.id("type"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		{
			WebElement element = driver.findElement(By.id("type"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).release().perform();
		}
		driver.findElement(By.cssSelector(".btn")).click();
		assertEquals(driver.findElement(By.cssSelector("#success-message > span")).getText(),
				"Pet details has been edited");
	}

}
