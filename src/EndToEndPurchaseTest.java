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

import java.util.List;

public class EndToEndPurchaseTest {

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
    public void testCompletePurchaseFlowWithLowestPriceItem() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed.");

        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[starts-with(@id, 'add-to-cart-')]"));

        double lowestPrice = Double.MAX_VALUE;
        int lowestPriceIndex = 0;

        for (int i = 0; i < priceElements.size(); i++) {
            String priceText = priceElements.get(i).getText().replace("$", "").trim();
            double price = Double.parseDouble(priceText);
            
            if (price < lowestPrice) {
                lowestPrice = price;
                lowestPriceIndex = i;
            }
        }

        addToCartButtons.get(lowestPriceIndex).click();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        String itemTotalText = driver.findElement(By.className("summary_subtotal_label")).getText();
        String taxText = driver.findElement(By.className("summary_tax_label")).getText();
        String totalText = driver.findElement(By.className("summary_total_label")).getText();

        double itemTotal = extractNumericValue(itemTotalText);
        double tax = extractNumericValue(taxText);
        double displayedTotal = extractNumericValue(totalText);

        double calculatedTotal = Math.round((itemTotal + tax) * 100.0) / 100.0;

        Assert.assertEquals(displayedTotal, calculatedTotal, 0.001, 
                "Displayed total does not match Item Total plus Tax!");

        driver.findElement(By.id("finish")).click();

        WebElement successHeader = driver.findElement(By.className("complete-header"));
        Assert.assertEquals(successHeader.getText(), "Thank you for your order!", 
                "Purchase process was not completed successfully.");
    }

    private double extractNumericValue(String text) {
        String numericPart = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numericPart);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}