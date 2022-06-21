package com.webui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CheckoutPage extends BasePage {
    private static Logger logger = LoggerFactory.getLogger(CheckoutPage.class);

    @FindBy(linkText = "Women") private WebElement womenLinkTextElement;
    @FindBy(xpath = "//a[@title='Faded Short Sleeve T-shirts']/ancestor::li") private WebElement titleWebElement;
    @FindBy(name = "Submit") private WebElement submitWebElement;
    @FindBy(xpath = "//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']") private WebElement proceedToCheckoutElement;
    @FindBy(xpath = "//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']") private WebElement cartNavigationElement;
    @FindBy(name = "processAddress") private WebElement processAddressWebElement;
    @FindBy(id = "uniform-cgv") private WebElement cgvVWebElement;
    @FindBy(name = "processCarrier") private WebElement processCarrierWebElement;
    @FindBy(xpath = "//*[@title='Close']") private WebElement closeElement;
    @FindBy(className = "bankwire") private WebElement bankWireWebElement;
    @FindBy(xpath = "//*[@id='cart_navigation']/button") private WebElement cartButtonWebElement;
    @FindBy(xpath = "//li[@class='step_done step_done_last four']") private WebElement stepDoneWebElement;
    @FindBy(xpath = "//li[@id='step_end' and @class='step_current last']") private WebElement stepEndWebElement;
    @FindBy(xpath = "//*[@class='cheque-indent']/strong") private WebElement chequeWebElement;

    private String orderConfirmationUrl;
    private final String productXPath = "//a[@title='${title}' and @class='product-name']";

    public CheckoutPage(WebDriver driver, WebDriverWait wait) throws Exception {
        super(driver, wait);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
        orderConfirmationUrl = propertyHelper.getStringForProperty("order_confirmation_page");
    }


    public void selectWomenCategoryAndSubmit(String title) {
        logger.debug("Click on Women Category and Select an item submit it");
        wait.until(ExpectedConditions.visibilityOf(womenLinkTextElement)).click();
        final String xpath = productXPath.replaceAll("\\$\\{title\\}", title);
        logger.debug("xpath is :" + xpath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
        wait.until(ExpectedConditions.visibilityOf(submitWebElement)).click();
    }

    public void proceedToCheckout() {
        logger.debug("proceed to checkout");
        wait.until(ExpectedConditions.visibilityOf(proceedToCheckoutElement)).click();
        wait.until(ExpectedConditions.visibilityOf(cartNavigationElement)).click();
    }

    public void processAddress() {
        logger.debug("accept address is correct and confirm");
        wait.until(ExpectedConditions.visibilityOf(processAddressWebElement)).click();

        if (!cgvVWebElement.isSelected()) {
            wait.until(ExpectedConditions.visibilityOf(cgvVWebElement)).click();
        }
        processCarrierWebElement.click();
        //wait.until(ExpectedConditions.visibilityOf(closeElement)).click();
    }


    public void makeOrderPaymentAndConfirm() {
        logger.debug("make payment of the order");
        //wait.until(ExpectedConditions.visibilityOf(closeElement)).click();
        wait.until(ExpectedConditions.visibilityOf(bankWireWebElement)).click();
        wait.until(ExpectedConditions.visibilityOf(cartButtonWebElement)).click();
        final WebElement heading = wait.until(ExpectedConditions.visibilityOf(h1Element));
        assertEquals("ORDER CONFIRMATION", heading.getText());
    }

    public void validateOrderCompleteScreen() {
        logger.debug("validate id if the order is successful");
        assertTrue(stepDoneWebElement.isDisplayed());
        assertTrue(stepEndWebElement.isDisplayed());
        assertTrue(chequeWebElement.getText()
                .contains("Your order on My Store is complete."));
        assertTrue(driver.getCurrentUrl().contains(orderConfirmationUrl));
    }

}
