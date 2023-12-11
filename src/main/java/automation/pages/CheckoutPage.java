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

import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;

    public CheckoutPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "billing_first_name")
    private WebElement firstName;

    @FindBy(id = "billing_last_name")
    private WebElement lastName;

    @FindBy(id = "billing_address_1")
    private WebElement address;

    @FindBy(id = "billing_postcode")
    private WebElement zipCode;

    @FindBy(id = "billing_city")
    private WebElement townName;

    @FindBy(css = "#order_review > table > tfoot > tr.order-total > td > strong > span > bdi")
    private WebElement totalAmount;

    @FindBy(id = "place_order")
    private WebElement placeOrder;

    @FindBy(css = "#post-207 > header > h1")
    private WebElement orderStatus;

//    @FindBy(css = "#post-207 > header > h1")
//    private WebElement confirmAddress;
//
//    @FindBy(css = "#post-207 > header > h1")
//    private WebElement confirmShipping;
//
//    @FindBy(css = "#post-207 > header > h1")
//    private WebElement payByBankWire;

    @FindBy(css = "#post-207 > header > h1")
    private WebElement confirmFinalStatus;




    public void provideBillingDetails() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(address));
        address.clear();
        address.sendKeys("abc");
        zipCode.clear();
        zipCode.sendKeys("1234");
        townName.clear();
        townName.sendKeys("abc");
    }

    public String getTotalAmount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(totalAmount));
        return totalAmount.getText();
    }

    public void placeOrder() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrder));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrder);
    }

    public String getOrderStatus() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        wait.until(ExpectedConditions.visibilityOf(orderStatus));
        Thread.sleep(10000);
        System.out.println(orderStatus.getText());
        return orderStatus.getText();
    }

    public Boolean checkFinalStatus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(confirmFinalStatus));
        return confirmFinalStatus.getText().contains(Constants.COMPLETE_ORDER);
    }


}
