package org.springframework.samples.petclinic.PruebasFuncionales;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindOwnerFunctionalTest {

	private WebDriver driver;

	@BeforeEach
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");

		driver = new ChromeDriver(options);
		// driver.manage().window().setSize(new Dimension(1920, 982));
	}

	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void findOwnerFunctional() {
		driver.get("http://localhost:8081/owners/find");
		driver.findElement(By.id("lastName")).sendKeys("smith");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();

		String actualText = driver.findElement(By.cssSelector("p")).getText();
		assertEquals("No ha sido encontrado", actualText);
	}

}
