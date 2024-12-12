package org.springframework.samples.petclinic.PruebasFuncionales;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindOwnerFunctionalTest {

	private WebDriver driver;

	private Map<String, Object> vars;

	private JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--remote-debugging-port=9222"); // Asegura la comunicación
		driver = new ChromeDriver(options);
		js = (JavascriptExecutor) driver;
		vars = new HashMap<>();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void findOwnerFunctional() {
		driver.get("http://localhost:8081/owners/find");
		driver.manage().window().setSize(new Dimension(1920, 982));
		driver.findElement(By.id("lastName")).click();
		driver.findElement(By.id("lastName")).sendKeys("smith");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

		// Usamos assertThat con Hamcrest para la validación.
		String actualText = driver.findElement(By.cssSelector("p")).getText();
		assertEquals("No ha sido encontrado", actualText);
	}

}
