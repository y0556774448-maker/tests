package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests {

    private WebDriver driver;
    private final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (isHeadless) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testSuccessfulLogin() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        boolean isInventoryDisplayed = driver.findElements(By.id("inventory_container")).size() > 0;
        Assert.assertTrue(isInventoryDisplayed, "Login failed for valid user.");
    }

    @Test
    public void testLockedOutUserLogin() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("locked_out_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(errorMessage.getText().contains("Sorry, this user has been locked out"), 
                "Error message for locked out user is incorrect.");
    }

    @Test
    public void testInvalidPasswordLogin() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("wrong_password");
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"), 
                "Error message for invalid password is incorrect.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}