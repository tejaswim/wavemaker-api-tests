package com.wavemaker.api.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.wavemaker.api.config.StudioTestConfig;

/**
 * Created by Tejaswi Maryala on 11/27/2017.
 */
public class WMInvokedListener implements IInvokedMethodListener2 {
    private static final Logger logger = LoggerFactory.getLogger(WMInvokedListener.class);
    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult, final ITestContext context) {
        if(method.isTestMethod()){
            int count = StudioTestConfig.getInstance().getInvocationCount();
            method.getTestMethod().setInvocationCount(count);
            logger.info("Successfully set invocation count as {}",count);
        }
    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult, final ITestContext context) {

    }

    @Override
    public void beforeInvocation(final IInvokedMethod method, final ITestResult testResult) {

    }

    @Override
    public void afterInvocation(final IInvokedMethod method, final ITestResult testResult) {

    }
}
