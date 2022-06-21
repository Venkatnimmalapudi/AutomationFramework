package com.webui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class HomePage extends BasePage {
    private static Logger logger = LoggerFactory.getLogger(HomePage.class);

    @FindBy(id= "email_create") private WebElement emailCreateElement;
    @FindBy(id= "SubmitCreate") private WebElement submitCreateElement;
    @FindBy(id= "id_gender2") private WebElement genderElement;
    @FindBy(id = "customer_firstname") private WebElement firstNameElement;
    @FindBy(id = "customer_lastname") private WebElement lastNameElement;
    @FindBy(id = "days") private WebElement daysElement;
    @FindBy(id = "months") private WebElement monthsElement;
    @FindBy(id = "years") private WebElement yearsElement;
    @FindBy(id = "company") private WebElement companyElement;
    @FindBy(id = "address1") private WebElement address1Element;
    @FindBy(id = "address2") private WebElement address2Element;
    @FindBy(id = "city") private WebElement cityElement;
    @FindBy(id = "id_state") private WebElement stateElement;
    @FindBy(id = "postcode") private WebElement postCodeElement;
    @FindBy(id = "other") private WebElement otherElement;
    @FindBy(id = "phone") private WebElement phoneElement;
    @FindBy(id = "phone_mobile") private WebElement mobileElement;
    @FindBy(id = "alias") private WebElement aliasElement;
    @FindBy(id = "submitAccount") private WebElement submitAccountElement;



    public HomePage(WebDriver driver, WebDriverWait wait) throws Exception {
        super(driver, wait);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
        loadBasePageUrl();
    }


    public void createAccountWithEmail(String email) {
        logger.debug("Passing Email Id for creation of account");
        wait.until(ExpectedConditions.visibilityOf(loginElement)).click();
        logger.debug("email is : {}", email);
        emailCreateElement.sendKeys(email);
        submitCreateElement.click();
    }

    public void fillAccountDetails(String name, String surname) {
        logger.debug("Fill Create Account Details");
        wait.until(ExpectedConditions.visibilityOf(genderElement)).click();
        firstNameElement.sendKeys(name);
        lastNameElement.sendKeys(surname);
        passwordElement.sendKeys("Qwerty");
        Select select = new Select(daysElement);
        select.selectByValue("1");
        select = new Select(monthsElement);
        select.selectByValue("1");
        select = new Select(yearsElement);
        select.selectByValue("2000");
        companyElement.sendKeys("Company");
        address1Element.sendKeys("Qwerty, 123");
        address2Element.sendKeys("zxcvb");
        cityElement.sendKeys("Qwerty");
        select = new Select(stateElement);
        select.selectByVisibleText("Colorado");
        postCodeElement.sendKeys("12345");
        otherElement.sendKeys("Qwerty");
        phoneElement.sendKeys("12345123123");
        mobileElement.sendKeys("12345123123");
        aliasElement.sendKeys("gk");
    }

    public void submitAccountDetailsAndValidate(String name, String surname) throws Exception {
        logger.debug("Submit Account Details...");
        submitAccountElement.click();

        WebElement heading =
                wait.until(ExpectedConditions.visibilityOf(h1Element));

        logger.debug("Validate Home Page...");
        assertEquals(heading.getText(), "MY ACCOUNT");
        assertEquals(accountElement.getText(), name + " " + surname);
        assertTrue(accountInfoElement.getText().contains("Welcome to your account."));
        assertTrue(logoutElement.isDisplayed());
        assertTrue(driver.getCurrentUrl().contains(propertyHelper.getStringForProperty("account_page")));
    }
}
