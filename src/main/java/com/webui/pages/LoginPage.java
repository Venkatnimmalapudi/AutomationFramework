package com.webui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class LoginPage extends BasePage{
    private static Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private String accountPageUrl;

    public LoginPage(WebDriver driver, WebDriverWait wait) throws Exception {
        super(driver, wait);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
        accountPageUrl = propertyHelper.getStringForProperty("account_page");
    }

    public void validateLogin(String fullName) {
        logger.debug("validate login details");
        WebElement heading = wait.until(ExpectedConditions.visibilityOf(h1Element));
        assertEquals("MY ACCOUNT", heading.getText());
        assertEquals(fullName, accountElement.getText());
        assertTrue(accountInfoElement.getText().contains("Welcome to your account."));
        assertTrue(logoutElement.isDisplayed());
        assertTrue(driver.getCurrentUrl().contains(accountPageUrl));
    }

}
