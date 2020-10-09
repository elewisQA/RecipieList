package com.qa.selenium;

//---[ Imports ]---
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//---[ Testing Code ]---
public class siteTests {

	//--[ Testing Variables ]--
	private ChromeDriver driver;
	private WebDriverWait wait;
	private String url = "http://127.0.0.1:5500/src/main/resources/website/html/index.html";

	//--[ Test Cases ]--
	@BeforeEach
	void init() {
		// Set-up Selenium Params
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(false); // Show Browser when Testing (?)
		this.driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1366, 768));
	}
	
	@Test
	void renameTest() throws InterruptedException {
		// Open Web-Page
		driver.get(url);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		driver.findElement(By.id("editRecipe-1")).click();
		
		Thread.sleep(5000);
	}
}
