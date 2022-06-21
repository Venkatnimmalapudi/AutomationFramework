package com.listeners;

import com.annotations.RetryCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/*
 *
 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
 *
 * This method decides how many times a test needs to be rerun. TestNg will
 * call this method every time a test fails. So we can put some code in here
 * to decide when to rerun the test.
 *
 * Note: This method will return true if a tests needs to be retried and
 * false it not.
 *
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);
    int maxRetry = 0;
    int counter = 0;

    @Override
    public boolean retry(ITestResult result) {

        // check if the test method had RetryCount annotation
        RetryCount annotation = result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);

        // All tests that are retried after failures will appear in the skipped tests
        // list. Which causes TestNg to report those retry attempts as skipped tests.
        // Here we will explicitly remove the retry test from the skipped tests list so that
        // TestNg doesn't report retry attempts as skipped attempts.
        // The line below simply does that.
        result.getTestContext().getSkippedTests().removeResult(result.getMethod());

        // based on the value of annotation see if test needs to be rerun
        if (annotation != null)
            maxRetry = annotation.value();
        else
            maxRetry = 2;

        logger.debug("Max retry count is : {} and current retry counter value : {}", maxRetry, counter);

        if(counter <= maxRetry) {
            counter++;
            return true;
        }
        return false;
    }
}
