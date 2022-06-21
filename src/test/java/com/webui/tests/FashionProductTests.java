package com.webui.tests;

import com.listeners.TestListener;
import com.webui.dataproviders.FashionProductDataprovider;
import com.webui.dto.FashionProduct;
import com.webui.pages.CheckoutPage;
import com.webui.pages.HomePage;
import com.webui.pages.LoginPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Date;

@Listeners(TestListener.class)
public class FashionProductTests extends BaseTest {
    final String existingUserEmail = "gk123@gk.com";
    final String existingUserPassword = "123456";

    public FashionProductTests() {
        super();
    }

    @Test(groups = {"signInTest", "webui"})
    public void signInTest() throws Exception {
        final HomePage homePage = new HomePage(driver.get(), wait.get());
        String timestamp = String.valueOf(new Date().getTime());
        String email = "gk_" + timestamp + "@gk" + timestamp.substring(7) + ".com";
        String name = "Firstname";
        String surName = "Lastname";
        homePage.createAccountWithEmail(email);
        homePage.fillAccountDetails(name, surName);
        homePage.submitAccountDetailsAndValidate(name, surName);
    }

    @Test(groups = {"logInTest", "webui"})
    public void logInTest() throws Exception {
        final LoginPage loginPage = new LoginPage(driver.get(), wait.get());
        loginPage.loadBasePageUrl();
        loginPage.login(existingUserEmail, existingUserPassword);
        loginPage.validateLogin("test test");
    }

    @Test(groups = {"checkoutTest", "webui"}, dataProviderClass = FashionProductDataprovider.class, dataProvider = "fetchData")
    public void checkoutTest(final FashionProduct fashionProduct) throws Exception {
        final CheckoutPage checkoutPage = new CheckoutPage(driver.get(), wait.get());
        checkoutPage.loadBasePageUrl();
        checkoutPage.login(existingUserEmail, existingUserPassword);
        checkoutPage.selectWomenCategoryAndSubmit(fashionProduct.getTitle());
        checkoutPage.proceedToCheckout();
        checkoutPage.processAddress();
        checkoutPage.makeOrderPaymentAndConfirm();
        checkoutPage.validateOrderCompleteScreen();
    }
}
