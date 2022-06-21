package com.listeners;

import com.webui.tests.BaseTest;
import lombok.SneakyThrows;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class TestListener implements ITestListener {
    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {

    }
    public void onTestFailure(ITestResult result) {
        //if it is UI capture logs and screenshots
        try {
            BaseTest.captureBrowserLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BaseTest.captureScreenShot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}
