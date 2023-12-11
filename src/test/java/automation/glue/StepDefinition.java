package automation.glue;

import automation.config.AutomationFrameworkConfiguration;
import automation.drivers.DriverSingleton;
import automation.pages.*;
import automation.utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@ContextConfiguration(classes = AutomationFrameworkConfiguration.class)
public class StepDefinition {
    private WebDriver driver;
    private HomePage homePage;
    private SignInPage signInPage;
    private CheckoutPage checkOutPage;
    private ShopPage shopPage;
    private CartPage cartPage;
    ExtentTest test;
    static ExtentReports report = new ExtentReports("report/TestReport.html");

    @Autowired
    ConfigurationProperties configurationProperties;

    @Before
    public void initializeObjects(){
        DriverSingleton.getInstance(configurationProperties.getBrowser());
        homePage = new HomePage();
        signInPage = new SignInPage();
        checkOutPage = new CheckoutPage();
        shopPage = new ShopPage();
        cartPage = new CartPage();
        TestCases[] tests = TestCases.values();
        test = report.startTest(tests[Utils.testCount].getTestName());
        Log.getLogData(Log.class.getName());
        Log.startTest(tests[Utils.testCount].getTestName());
        Utils.testCount++;

    }

    @Given("^I go to the Website")
    public void i_go_to_the_Website(){
        driver = DriverSingleton.getDriver();
        driver.get(Constants.URL);
        Log.info("Navigating to " + Constants.URL);
        test.log(LogStatus.PASS, "Navigating to " + Constants.URL);
    }

    @When("^I click on Sign In button")
    public void i_click_on_sign_in_button(){
        homePage.clickLogIn();
        Log.info("Sign In button has been clicked.");
        test.log(LogStatus.PASS, "Sign In button has been clicked.");
    }

    @When("^I add one element to the cart")
    public void i_add_one_element_to_the_cart() throws InterruptedException {
        homePage.clickShopButton();
        Log.info("Clicking shop button.");
        shopPage.addElementToCart();
        Log.info("We add an item to the cart.");
        test.log(LogStatus.PASS, "We add an item to the cart.");
    }

    @And("^I specify my credentials and click Login")
    public void i_specify_my_credentials_and_click_login(){
        signInPage.logIn(configurationProperties.getEmail(), configurationProperties.getPassword());

        test.log(LogStatus.PASS, "Login has been clicked.");
    }

    @And("^I proceed to checkout")
    public void i_proceed_to_checkout(){
        shopPage.proceedToCheckout();
        cartPage.couponCode();
        cartPage.applyCoupon();
        cartPage.proceedToCheckout();
        test.log(LogStatus.PASS, "We proceed to checkout.");
    }

    @And("^I confirm address, shipping, payment and final order")
    public void i_confirm_address_shipping_payment_and_final_order() throws InterruptedException {
        checkOutPage.provideBillingDetails();
        Log.info("We are inputting the billing details.");
        checkOutPage.placeOrder();
        Log.info("We are now placing an order.");
        test.log(LogStatus.PASS, "We confirm the final order");
    }

    @Then("^I can log into the website")
    public void i_can_log_into_the_website(){
        if(("Hello, " + configurationProperties.getUsername()).equals(homePage.getUsername()))
            test.log(LogStatus.PASS, "The authentication is successful.");
        else
            test.log(LogStatus.FAIL, "Authentication is not successful.");
        Log.info("We are logged in.");
        assertEquals("Hello, " + configurationProperties.getUsername(), homePage.getUsername());
    }

    @Then("^the elements are bought")
    public void the_elements_are_bought() throws InterruptedException {
        if(checkOutPage.checkFinalStatus()) {
            Log.info("The item is bought.");
            test.log(LogStatus.PASS, "The item is bought.");
        }
        else
            Log.error("Error: The item wasn't bought, unable to checkout");
            test.log(LogStatus.FAIL, "The item wasn't bought.");
        assertEquals("Order received", checkOutPage.getOrderStatus());
    }

    @After
    public void closeDriver(){
        DriverSingleton.closeObjectInstance();
        report.endTest(test);
        report.flush();
//        DriverSingleton.closeObjectInstance();
    }


}
