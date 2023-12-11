import automation.drivers.DriverSingleton;
import automation.pages.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import automation.utils.Constants;
import automation.utils.FrameworkProperties;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    static FrameworkProperties frameworkProperties;
    static WebDriver driver;
    static HomePage homePage;
    static ShopPage shopPage;
    static CartPage cartPage;
    static SignInPage signInPage;
    static CheckoutPage checkoutPage;

    @BeforeClass
    public static void initializeObjects() {
        frameworkProperties = new FrameworkProperties();
        DriverSingleton.getInstance(frameworkProperties.getProperty(Constants.BROWSER));
        driver = DriverSingleton.getDriver();
        homePage = new HomePage();
        shopPage = new ShopPage();
        cartPage = new CartPage();
        signInPage = new SignInPage();
        checkoutPage = new CheckoutPage();
    }

    @Test
    public void Test01_testingAuthentication() {
        driver.get(Constants.URL);
        homePage.clickLogIn();
        signInPage.logIn(frameworkProperties.getProperty(Constants.EMAIL), frameworkProperties.getProperty(Constants.PASSWORD));
        assertEquals(frameworkProperties.getProperty(Constants.USERNAME), homePage.getUsername());
    }

    @Test
    public void Test02_testingAddingThingsToCart() throws InterruptedException {
        driver.get(Constants.URL);
        homePage.clickShopButton();
        shopPage.addElementToCart();
        assertEquals(Constants.CART_QUANTITY, shopPage.getNumberOfItems());
    }

    @Test
    public void Test03_testingTheFullBuyProcess() throws InterruptedException {
        driver.get(Constants.URL);
        homePage.clickShopButton();
        shopPage.addElementToCart();
//        assertEquals(Constants.CART_QUANTITY_TEST, shopPage.getNumberOfItems());
        shopPage.proceedToCheckout();
        cartPage.couponCode();
        cartPage.applyCoupon();
        cartPage.proceedToCheckout();
        checkoutPage.provideBillingDetails();
        checkoutPage.placeOrder();
        Thread.sleep(5000);
        assertEquals("Order received", checkoutPage.getOrderStatus());
    }


    @AfterClass
    public static void closeObjects(){
        driver.close();
    }
}
