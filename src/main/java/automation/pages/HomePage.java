package automation.pages;

import automation.drivers.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    public HomePage(){
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#menu-item-1311 > a")
    private WebElement loginButton;

    @FindBy(css = "#menu-item-1310 > a")
    private WebElement shopButton;

    @FindBy(css = "#menu-item-1314 > a")
    private WebElement username;

    @FindBy(css = "body > p > a.woocommerce-store-notice__dismiss-link")
    private WebElement dismiss;

    public void clickLogIn() {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void clickShopButton() {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(shopButton));
        shopButton.click();
    }

    public String getUsername() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(username));
        return username.getText();
    }

    public void dismiss() {
        if (dismiss.isDisplayed()) {
            System.out.println(dismiss.isDisplayed());
            dismiss.click();
        } else if (!dismiss.isDisplayed()) {
            System.out.println("Dismiss is not yet found");
        } else {
            System.out.println("Dismiss is already clicked");
        }
    }

}
