import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class PetStoreTest {

    private AppiumDriver<MobileElement> driver;
    private PetStoreHomePage petStoreHomePage;

    @Before
    public void setUp() throws MalformedURLException {
        // Set up Appium capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("appPackage", "your.app.package"); // Replace with the actual package name
        capabilities.setCapability("appActivity", "your.app.activity"); // Replace with the actual activity name
        capabilities.setCapability("automationName", "UiAutomator2");

        // Initialize Appium driver
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);

        // Initialize Page Object using PageFactory
        petStoreHomePage = new PetStoreHomePage();
        PageFactory.initElements(new AppiumFieldDecorator(driver), petStoreHomePage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void verifyAddToCartFunctionality() {
        // Your test logic goes here
        petStoreHomePage.navigateToPetCategory("Dogs");
        petStoreHomePage.addToCart("Golden Retriever");

        // Add assertions
        Assert.assertTrue("Cart is not empty after adding a pet", petStoreHomePage.isCartNotEmpty());
        Assert.assertEquals("Incorrect number of items in the cart", 1, petStoreHomePage.getCartItemCount());
        // Add more assertions as needed
    }

    // Define your Page Object Model classes here
    public static class PetStoreHomePage {

        // Define your locators using By class
        By petCategoryButton = By.id("petCategoryButtonId");
        By addToCartButton = By.id("addToCartButtonId");
        By cartIcon = By.id("cartIconId"); // Replace with your actual locator
        By cartItemCountLabel = By.id("cartItemCountLabelId"); // Replace with your actual locator

        // Define your page methods
        public void navigateToPetCategory(String categoryName) {
            // Your logic to navigate to a specific pet category
            driver.findElement(petCategoryButton).click();
            // Add more steps if needed
        }

        public void addToCart(String petName) {
            // Your logic to add a pet to the cart
            driver.findElement(By.name(petName)).click();
            driver.findElement(addToCartButton).click();
            // Add more steps if needed
        }

        public boolean isCartNotEmpty() {
            // Your logic to check if the cart is not empty
            // Example: return true if the cart is not empty, false otherwise
            return driver.findElement(cartIcon).isEnabled();
        }

        public int getCartItemCount() {
            // Your logic to get the number of items in the cart
            // Example: return the count of items in the cart
            return Integer.parseInt(driver.findElement(cartItemCountLabel).getText());
        }
    }
}
