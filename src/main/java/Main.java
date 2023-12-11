import automation.drivers.DriverSingleton;
import automation.pages.*;
import automation.utils.Constants;
import automation.utils.FrameworkProperties;
import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) throws Exception {
        FrameworkProperties frameworkProperties = new FrameworkProperties();
        DriverSingleton.getInstance(frameworkProperties.getProperty(Constants.BROWSER));
        WebDriver driver = DriverSingleton.getDriver();
        driver.get("https://bitheap.tech");

        HomePage homePage = new HomePage();
        SignInPage signInPage = new SignInPage();
        ShopPage shopPage = new ShopPage();
        CartPage cartPage = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

//        homePage.dismiss();
        homePage.clickLogIn();
        signInPage.logIn(frameworkProperties.getProperty(Constants.EMAIL), frameworkProperties.getProperty(Constants.PASSWORD));
        if (homePage.getUsername().equals("Hello, Leroy"))
            System.out.println("Test Passed");
        else
            System.out.println("Test Failed");
//        homePage.dismiss();

        homePage.clickShopButton();

        shopPage.addElementToCart();
        shopPage.proceedToCheckout();
        cartPage.couponCode();
        cartPage.applyCoupon();
        cartPage.proceedToCheckout();
        checkoutPage.provideBillingDetails();
        checkoutPage.placeOrder();
        Thread.sleep(5000);

        if (checkoutPage.getOrderStatus().equals("Order received"))
            System.out.println("Test Passed");
        else
            throw new Exception();

        DriverSingleton.closeObjectInstance();

    }
}
