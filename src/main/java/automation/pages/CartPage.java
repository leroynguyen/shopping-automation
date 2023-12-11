package automation.pages;

import automation.drivers.DriverSingleton;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;

    public CartPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#post-206 > content > div > div.woocommerce > div.cart-collaterals > div > div > a")
    private WebElement proceedToCheckoutButton;

    @FindBy(id = "coupon_code")
    private WebElement couponCode;

    @FindBy(css = "#post-206 > content > div > div.woocommerce > form > table > tbody > tr:nth-child(2) > td > div > button")
    private WebElement applyCoupon;

    public void couponCode() {
        couponCode.sendKeys("UDEMY-CHALLENGE");
    }

    public void applyCoupon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(applyCoupon));
        applyCoupon.click();
    }

    public void proceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedToCheckoutButton);
//        proceedToCheckoutButton.click();
    }

}
