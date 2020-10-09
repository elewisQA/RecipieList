package com.qa.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
//---[ Imports ]---
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
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
		this.driver.manage().window().setSize(new Dimension(1366, 768));
	}
	
	@Test
	void renameRecipeTest() {
		// Set-up Test Resources
		driver.get(this.url);
		String name = "Battenburg";
	
		// Click 'Edit' Button for first recipe
		this.wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Rename
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("title-field")));
		this.driver.findElement(By.id("title-field")).sendKeys(name);
		
		// Click 'Save'
		this.driver.findElement(By.id("save-button")).click();

		// Check Rename After Refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("title-field")));
		WebElement title = this.driver.findElement(By.id("title-field"));
		
		// Test Assertion
		assertThat(title.getAttribute("innerHTML").equals(name));
	}
	
	@Test
	void renameIngredientTest() {
		// Set-up Test Resources
		driver.get(this.url);
		String name = "Rye-Flour";
	
		// Click 'Edit' Button for first recipe
		this.wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Rename
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-name-1")));
		WebElement field = this.driver.findElement(By.id("ingredient-name-1"));
		field.sendKeys(name);
		
		// Click 'Save'
		this.driver.findElement(By.id("isave-1"))
		.click();

		// Check Rename After Refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-name-1")));
		WebElement savedName = this.driver.findElement(By.id("ingredient-name-1"));
		
		// Test Assertion
		assertThat(savedName.getAttribute("innerHTML").equals(name));
	}
	
	@AfterEach
	void tearDown() {
		driver.quit();
	}
}
