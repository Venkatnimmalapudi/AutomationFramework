package com.webui.tests;

import com.webui.helpers.PropertyHelper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest implements ITest {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();


    @BeforeSuite(alwaysRun = true)
    public void setup () {
        final PropertyHelper propertyHelper = PropertyHelper.getInstance();
        propertyHelper.loadLocalizableProperties("src/test/resources/properties/endpoints.properties");

        if (System.getProperty("env").contains("staging"))
            propertyHelper.loadLocalizableProperties("src/test/resources/properties/staging.properties");
        else if (System.getProperty("env").contains("prod"))
            propertyHelper.loadLocalizableProperties("src/test/resources/properties/prod.properties");

    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {

        //browser, env will be set via jvm arguments
        switch (System.getProperty("browser")) {
            case "chrome" :
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
        }


        //Create a wait. All test classes use this.
        wait.set(new WebDriverWait(driver.get(),15));

        //Maximize Window
        driver.get().manage().window().maximize();
        testName.set(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void teardown () {
        driver.get().close();
        driver.get().quit();
    }


    public static void captureBrowserLogs() throws IOException {
        final Logs logs= driver.get().manage().logs();
        final LogEntries logEntries=logs.get(LogType.BROWSER);
        final String filePath = "logs/browser/" + testName.get() + "_" + System.currentTimeMillis() + ".log";
        final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.append("======== Browser log - starts ========");
        for(LogEntry logEntry:logEntries){
            writer.append(logEntry.getMessage()+"\n");
        }
        writer.append("======== Browser log - ends ========");
        writer.close();
    }

    public static void captureScreenShot() throws IOException {
        final TakesScreenshot scrShot =((TakesScreenshot)driver.get());
        final File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File DestFile=new File("logs/screenshots/");

        //Copy file at destination
        FileUtils.copyFileToDirectory(SrcFile, DestFile);

    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
