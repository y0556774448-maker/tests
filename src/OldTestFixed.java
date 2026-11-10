package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class OldTestFixed {

    private WebDriver driver;
    private final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testPriceSortingLowToHigh() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
        Select select = new Select(sortDropdown);
        select.selectByValue("lohi");

        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        
        boolean isSorted = true;
        for (int i = 0; i < priceElements.size() - 1; i++) {
            double currentPrice = Double.parseDouble(priceElements.get(i).getText().replace("$", ""));
            double nextPrice = Double.parseDouble(priceElements.get(i + 1).getText().replace("$", ""));
            
            if (currentPrice > nextPrice) {
                isSorted = false;
                break;
            }
        }

        Assert.assertTrue(isSorted, "Price list is not correctly sorted from Low to High.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}