package com.wavemaker.api.listeners;

import java.io.File;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.IConfigurationListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;

import com.wavemaker.api.manager.LoggerManager;
import com.wavemaker.api.report.ReportUtils;

import static com.wavemaker.api.report.ReportUtils.generateBeforeAnnotationsFilePath;
import static com.wavemaker.api.report.ReportUtils.getTestMethodName;
import static com.wavemaker.api.report.ReportUtils.isAnnotationBeforeOrAfterClass;
import static com.wavemaker.api.report.ReportUtils.isAnnotationBeforeOrAfterMethod;

/**
 * Created by Tejaswi Maryala on 11/29/2017.
 */
public class WMTestListener implements ITestListener,IConfigurationListener2 {
    private static final Logger logger = LoggerFactory.getLogger(WMTestListener.class);
    LoggerManager loggerManager = new LoggerManager();

    @Override
    public void onTestStart(final ITestResult result) {
        final String testMethodClass = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        logger.info("{} _ {} has started", testMethodClass, methodName);
        MDC.put("variable", File.separator + ReportUtils.generateLogFileName(result));
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        final String testMethodClass = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        logger.info("{} _ {} has passed with data provider {}", testMethodClass, methodName, getDataProviderDetails(result));
        MDC.clear();
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        final String testMethodClass = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        logger.info("{} _ {} has failed with data provider {}", testMethodClass, methodName, getDataProviderDetails(result),
                result.getThrowable());
        loggerManager.saveLogFileInLogsFolder(ReportUtils.generateLogFileName(result));
        MDC.clear();
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        final String testMethodClass = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        logger.info("{} _ {} has skipped with data provider {}", testMethodClass, methodName, getDataProviderDetails(result));
        MDC.clear();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {

    }

    @Override
    public void onStart(final ITestContext context) {
        System.out.println("Test " + context.getName() + " has started");
    }

    @Override
    public void onFinish(final ITestContext context) {
        logger.info("Test context {} has finished", context.getName());
    }

    private String getDataProviderDetails(final ITestResult result) {
        final Object[] parameters = result.getParameters();
        StringBuilder dataProvider = new StringBuilder();
        dataProvider.append("{");
        for (Object param : parameters) {
            if (param == null) {
                dataProvider.append("null ,");
            } else {
                dataProvider.append(param.toString()).append(",");
            }
        }
        dataProvider.append("}");
        return dataProvider.toString();
    }

    @Override
    public void beforeConfiguration(final ITestResult tr) {
        ConstructorOrMethod method = tr.getMethod().getConstructorOrMethod();
        if (isAnnotationBeforeOrAfterMethod(method.getMethod())) {
            logger.info("Calling method {} in class {}", method.getName(), method.getDeclaringClass().getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (isMethodParameterTypeExist(parameterTypes)) {
                MDC.put("variable", File.separator + generateBeforeAnnotationsFilePath(tr));
            }
        } else if (isAnnotationBeforeOrAfterClass(method.getMethod())) {
            MDC.put("variable", File.separator + generateBeforeAnnotationsFilePath(tr));
        }
    }

    @Override
    public void onConfigurationSuccess(final ITestResult itr) {
        logger.info("{}_{} configuration method {} has passed", itr.getTestClass().getRealClass().getSimpleName(),
                getTestMethodName(itr), itr.getMethod().getMethodName());
        MDC.clear();
    }

    @Override
    public void onConfigurationFailure(final ITestResult itr) {
        String testMethodName = getTestMethodName(itr);
        logger.error("{}_{} configuration method {} has failed", itr.getTestClass().getRealClass().getSimpleName(),
                testMethodName, itr.getMethod().getMethodName(), itr.getThrowable());
        loggerManager.saveLogFileInLogsFolder(itr.getTestClass().getRealClass().getSimpleName() + File.separator + testMethodName);
        MDC.clear();
    }

    @Override
    public void onConfigurationSkip(final ITestResult itr) {
        String testMethodName = getTestMethodName(itr);
        MDC.put("variable", File.separator + generateBeforeAnnotationsFilePath(itr));
        logger.error("{}_{} configuration method {} has skipped", itr.getTestClass().getRealClass().getSimpleName(),
                testMethodName, itr.getMethod().getMethodName(), itr.getThrowable());
        loggerManager.saveLogFileInLogsFolder(itr.getTestClass().getRealClass().getSimpleName() + File.separator + testMethodName);
        MDC.clear();
    }

    private boolean isMethodParameterTypeExist(Class<?>[] parameterTypes) {
        for (final Class<?> parameterType : parameterTypes) {
            if (parameterType == Method.class) {
                return true;
            }
        }
        return false;
    }
}
