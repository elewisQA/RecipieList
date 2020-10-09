package com.qa.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
		options.setHeadless(true); // Show Browser when Testing (?)
		this.driver = new ChromeDriver(options);
		this.driver.manage().window().setSize(new Dimension(1366, 768));
	}
	
	//==[ RECIPE TEST CASE ]==
	@Test
	void renameRecipeTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Battenburg";
	
		// Click 'Edit' Button for first recipe
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
		assertThat(title.getAttribute("value").equals(name));
	}
	
	@Test
	void deleteRecipeTest() {
		// Set-up Test resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		
		// Wait for target-button to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("delRecipe-2")));
		
		// Get All Recipe-Titles
		List<WebElement> beforeTitles = this.driver.findElements(By.className("recipe-title"));
		
		// Click 'Delete'
		this.driver.findElement(By.id("delRecipe-2")).click();
		
		// Get All Recipe-Titles
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("delRecipe-1")));
		List<WebElement> afterTitles = this.driver.findElements(By.className("recipe-title"));
		
		// Assert - list size difference
		assertThat(beforeTitles.size() !=  afterTitles.size());
	}
	
	@Test
	void addRecipeTest() {
		// Set-up Test resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		
		// Click 'Add' Button
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("btn btn-primary")));
		this.driver.findElement(By.className("btn btn-primary")).click(); // Click It
		
		// Wait for reload
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'New Recipe')]")));
		
		// Test Fails if this never appears
	}
	
	//==[ INGREDIENT TEST CASES ]==
	@Test
	void renameIngredientTest(){
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Rye-Flour";
	
		// Click 'Edit' Button for first recipe	
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
		assertThat(savedName.getAttribute("value").equals(name));
	}
	
	@Test
	void updateIngredientTest(){
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Wheat-Flour";
		String unit = "kg";
		String qty = "7";
	
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for elements to appear 
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-name-1")));
		this.driver.findElement(By.id("ingredient-name-1")).sendKeys(name);		// Update Name
		this.driver.findElement(By.id("qty-1")).sendKeys(qty);					// Update Quantity
		this.driver.findElement(By.id("unit-1")).sendKeys(unit);				// Update Unit
		
		
		// Click 'Save'
		this.driver.findElement(By.id("isave-1"))
		.click();

		// Wait for elements after refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-name-1")));
		
		WebElement updatedName = this.driver.findElement(By.id("ingredient-name-1"));
		WebElement updatedQty = this.driver.findElement(By.id("qty-1"));
		WebElement updatedUnit = this.driver.findElement(By.id("unit-1"));

		// Test Assertions
		assertThat(updatedName.getAttribute("value").equals(name));
		assertThat(updatedQty.getAttribute("value").equals(qty));
		assertThat(updatedUnit.getAttribute("value").equals(unit));
	}
	
	@Test
	void deleteIngredientTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
	
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for elements to appear 
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("idel-2")));
		
		// Get Before-List
		List<WebElement> beforeNames = driver.findElements(By.className("ingredient-name-field"));
		
		// Click Delete
		driver.findElement(By.id("idel-2")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("idel-1")));
		List<WebElement> afterNames = driver.findElements(By.className("ingredient-name-field"));
		
		// Assert change in no. elements
		assertThat(beforeNames.size() != afterNames.size());
	}
	
	@Test
	void addIngredientTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Rose Water";
		String unit = "tbsp";
		String qty = "10";
		
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for 'Add' Button to appear	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-add")));
		this.driver.findElement(By.id("ingredient-add")).click();
		
		// Wait for new-row to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("add-ingredient-name-field")));
		
		// Populate new row with data
		this.driver.findElement(By.className("add-ingredient-name-field"))
		.sendKeys(name);
		this.driver.findElement(By.className("add-qty-field"))
		.sendKeys(qty);
		this.driver.findElement(By.className("add-unit-field"))
		.sendKeys(unit);
		
		// Find and click "add" button
		this.driver.findElement(By.id("add-ingredient-button")).click();
		
		// Wait for page-reload
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("ingredient-name-field")));
		
		// Get new element
		WebElement newNameField = driver.findElement(By.xpath("//input[@placeholder='" + name + "']"));
		assertThat(newNameField.getAttribute("placeholder").contentEquals(name));
	}
	
	//==[ STEP TEST CASES ]==
	@Test
	void renameStepTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Step X";
	
		// Click 'Edit' Button for first recipe		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Rename
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("ingredient-name-1")));
		WebElement field = this.driver.findElement(By.id("step-name-1"));
		field.sendKeys(name);
		
		// Click 'Save'
		this.driver.findElement(By.id("ssave-1"))
		.click();

		// Check Rename After Refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("step-name-1")));
		WebElement savedName = this.driver.findElement(By.id("step-name-1"));

		// Test Assertion
		assertThat(savedName.getAttribute("value").equals(name));
	}
	
	@Test
	void updateStepTest(){
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Wheat-Flour";
		String desc = "EAT BUTTER";
	
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for elements to appear 
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("step-name-1")));
		this.driver.findElement(By.id("step-name-1")).sendKeys(name);		// Update Name
		this.driver.findElement(By.id("description-1")).sendKeys(desc);		// Update Description
		
		
		// Click 'Save'
		this.driver.findElement(By.id("ssave-1"))
		.click();

		// Wait for elements after refresh
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("step-name-1")));
		
		WebElement updatedName = this.driver.findElement(By.id("step-name-1"));
		WebElement updatedDesc = this.driver.findElement(By.id("description-1"));

		// Test Assertions
		assertThat(updatedName.getAttribute("value").equals(name));
		assertThat(updatedDesc.getAttribute("value").equals(desc));
	}
	
	@Test
	void deleteStepTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
	
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for elements to appear 
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("sdel-1")));
		
		// Get Before-List
		List<WebElement> beforeNames = driver.findElements(By.className("step-name-field"));
		
		// Click Delete
		driver.findElement(By.id("sdel-2")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("sdel-1")));
		List<WebElement> afterNames = driver.findElements(By.className("step-name-field"));
		
		// Assert change in no. elements
		assertThat(beforeNames.size() != afterNames.size());
	}
	
	@Test
	void addStepTest() {
		// Set-up Test Resources
		driver.get(this.url);
		this.wait = new WebDriverWait(driver,10);
		String name = "Opt.";
		String desc = "EAT KALE";
		
		// Click 'Edit' Button for first recipe	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("editRecipe-1")));
		this.driver.findElement(By.id("editRecipe-1")).click();
		
		// Wait for 'Add' Button to appear	
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.id("step-add")));
		this.driver.findElement(By.id("step-add")).click();
		
		// Wait for new-row to appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("add-step-name-field")));
		
		// Populate new row with data
		this.driver.findElement(By.className("add-step-name-field"))
		.sendKeys(name);
		this.driver.findElement(By.className("add-desc-field"))
		.sendKeys(desc);
		
		// Find and click "add" button
		this.driver.findElement(By.id("add-step-button")).click();
		
		// Wait for page-reload
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.className("name-step-field")));
		
		// Get new element
		WebElement newNameField = driver.findElement(By.xpath("//input[@placeholder='" + name + "']"));
		assertThat(newNameField.getAttribute("placeholder").contentEquals(name));
	}
	
	//---[ After Each - Kill Driver ]---
	@AfterEach
	void tearDown() {
		driver.quit();
	}
}
