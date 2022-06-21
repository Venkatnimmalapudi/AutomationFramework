package com.webui.pages;

import com.webui.helpers.PropertyHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {
    private static Logger logger = LoggerFactory.getLogger(BasePage.class);
    @FindBy(className = "login") public WebElement loginElement;
    @FindBy(id = "email") public WebElement emailElement;
    @FindBy(id = "passwd") public WebElement passwordElement;
    @FindBy(id = "SubmitLogin") public WebElement submitLoginElement;
    @FindBy(css = "h1") public WebElement h1Element;
    @FindBy(className = "account") public WebElement accountElement;
    @FindBy(className = "info-account") public WebElement accountInfoElement;
    @FindBy(className = "logout") public WebElement logoutElement;

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final PropertyHelper propertyHelper = PropertyHelper.getInstance();

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void loadBasePageUrl() throws Exception {
        String url = propertyHelper.getStringForProperty("base_url") + propertyHelper.getStringForProperty("home_page");
        logger.debug("Loading url is : {}", url);
        driver.get(url);
    }

    public void login(String email, String password) {
        logger.debug("login details");
        wait.until(ExpectedConditions.visibilityOf(loginElement)).click();
        emailElement.sendKeys(email);
        passwordElement.sendKeys(password);
        submitLoginElement.click();
    }

}
