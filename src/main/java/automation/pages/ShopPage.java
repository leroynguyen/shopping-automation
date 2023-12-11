package automation.pages;

import automation.utils.Constants;
import automation.drivers.DriverSingleton;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import automation.utils.Utils;

import java.time.Duration;

public class ShopPage {
    private WebDriver driver;

    public ShopPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#main > ul > li:nth-child(2) > a.button")
    private WebElement addToCartButton;

    @FindBy(css = "body > nav > div.wb4wp-wrapper > div.wb4wp-right > div > a > span")
    private WebElement numberOfItems;

    @FindBy(css = "body > nav > div.wb4wp-wrapper > div.wb4wp-right > div > a")
    private WebElement cartButton;


    public void addElementToCart() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
        Thread.sleep(2000);
        System.out.println(numberOfItems.getText());
        if (numberOfItems.getText().contains(Constants.CART_QUANTITY)) {
            System.out.println("Cart has been updated!");
            Utils.takeScreenshot();
        }
        else {
            System.out.println("Cart has not been updated.");
            Utils.takeScreenshot();
        }
    }

    public String getNumberOfItems() {
        return numberOfItems.getText();
    }

    public void proceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();

    }
}
