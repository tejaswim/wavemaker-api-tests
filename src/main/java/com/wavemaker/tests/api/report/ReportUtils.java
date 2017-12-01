package com.wavemaker.tests.api.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.wavemaker.tests.api.config.StudioTestConfig;

/**
 * Created by ArjunSahasranam on 12/1/16.
 */
public class ReportUtils {
    private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);
    private static final String GECKO_DRIVER = "webdriver.firefox.marionette";
    private static final String TOGGLE_SERVER_LOGS = "$('#serverLogs').click(function(){$('p.serverLogs').toggle();});";
    private static final String TOGGLE_APP_LOGS = "$('#appLogs').click(function(){$('p.appLogs').toggle();});";
    private static final StudioTestConfig instance = StudioTestConfig.getInstance();


    public static String getTime(long time) {
        long totalSeconds = time / 1000;

        long minutes = totalSeconds / 60;
        long remSeconds = totalSeconds % 60;

        long hours = minutes / 60;
        long remMinutes = minutes % 60;
        if (remMinutes == 0) {
            return remSeconds + " seconds";
        }
        if (hours == 0) {
            return remMinutes + " minutes " + remSeconds + " seconds";
        }
        return hours + " hours " + remMinutes + " minutes " + remSeconds + " seconds";
    }

    // return environmental details
    public static String getHostName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            return hostname;
        } catch (UnknownHostException e) {
            return null;
        }
    }

    // return time and date
    public static String timeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime()).toString();
    }

    public static String getLogsDirectoryPath() {
        final URL resourceAsStream = Thread.currentThread().getContextClassLoader().getResource("data");
        File file = null;
        try {
            file = new File(resourceAsStream.toURI());
        } catch (URISyntaxException e) {
        }
        return file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf("target")) + "logs";
    }

    static final String getStartHtml(String suiteName) {
        InputStream resourceAsStream = ReportUtils.class.getResourceAsStream("/start.html");
        String startHtml = null;
        try {
            startHtml = IOUtils.toString(resourceAsStream);
        } catch (IOException e) {
            // log
        } finally {
            IOUtils.closeQuietly(resourceAsStream);
        }

        String hostName = ReportUtils.getHostName();
        String[] searchList = {"${toggleServerLogs}", "${toggleAppLogs}", "${suitename}", "${hostname}", "${osname}",
                "${osversion}", "${url}", "${browser}", "${timestamp}"};
        String[] replacements = {TOGGLE_SERVER_LOGS, TOGGLE_APP_LOGS, suiteName, hostName,
                System.getProperty("os.name"), System.getProperty("os.version"),
                instance.getUrl(), "N/A", ReportUtils.timeStamp(),};
        return StringUtils.replaceEach(startHtml, searchList, replacements);
    }

    public static <T> Class getClassType(T t) {
        return (t != null) ? t.getClass() : null;
    }

    public static String logDataProviderInfo(ITestResult result) {
        StringBuilder parametersStr = new StringBuilder();

        parametersStr.append("<details><summary>click here for params info</summary>");
        int index = 1;
        Object[] parameters = result.getParameters();
        parametersStr.append("<p>");
        for (Object param : parameters) {
            if (param == null) {
                param = "null";
            }
            parametersStr.append("<b>Parameter #").append(index).append(": </b>");
            parametersStr.append(param.toString().trim()).append("</br></br>");
            index++;
        }
        parametersStr.append("</p></details>");
        return parametersStr.toString();
    }

    public static int generateHashCode(final ITestResult result) {
        return result.hashCode();
    }

    public static String generateScreenshotFilePath(final ITestResult result) {
        return "screenshots" + File.separator + generateScreenshotFileName(result) + ".jpg";
    }

    public static String generateScreenshotFileName(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        return tcClassName + "_" + methodName + generateHashCode(result);
    }

    public static String generateBeforeClassLogFilePath(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        return tcClassName + File.separator + tcClassName + ".log";
    }

    public static String generateBeforeClassServerLogFilePath(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        return "server" + File.separator + tcClassName + ".log";
    }

    public static String generateBeforeClassApplicationLogFilePath(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        return "apps" + File.separator + tcClassName + ".log";
    }

    public static String generateServerLogFilePathWithHashCode(final ITestResult result) {
        return "server" + File.separator + generateLogFileNameWithHashCode(result) + ".log";
    }

    public static String generateServerLogFilePath(final ITestResult result) {
        return "server" + File.separator + generateLogFileName(result) + ".log";
    }

    public static String generateApplicationLogFilePathWithHashCode(final ITestResult result) {
        return "apps" + File.separator + generateLogFileNameWithHashCode(result) + ".log";
    }

    public static String generateApplicationLogFilePath(final ITestResult result) {
        return "apps" + File.separator + generateLogFileName(result) + ".log";
    }

    public static String generateLogFilePathWithHashCode(final ITestResult result) {
        return generateLogFileNameWithHashCode(result) + ".log";
    }

    public static String generateLogFilePath(final ITestResult result) {
        return generateLogFileName(result) + ".log";
    }

    public static String generateLogFileNameWithHashCode(final ITestResult result) {
        return generateLogFileName(result) + generateHashCode(result);
    }

    public static String generateLogFileName(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        return tcClassName + File.separator + methodName;
    }

    public static String generateBeforeAnnotationsFilePath(final ITestResult result) {
        final String tcClassName = result.getTestClass().getRealClass().getSimpleName();
        String methodName = getTestMethodName(result);
        return tcClassName + File.separator + methodName;
    }

    public static String getTestMethodName(final ITestResult itr) {
        boolean beforeOrAfterClass = isAnnotationBeforeOrAfterClass(itr.getMethod().getConstructorOrMethod().getMethod());
        if (!beforeOrAfterClass) {
            Method testMethod = getTestMethod(itr);
            if (testMethod != null) {
                return testMethod.getName();
            }
        } else {
            return itr.getTestClass().getRealClass().getSimpleName();
        }
        return StringUtils.EMPTY;
    }

    public static boolean isAnnotationBeforeOrAfterClass(final Method method) {
        return (AnnotationUtils.findAnnotation(method, BeforeClass.class) != null) ||
                (AnnotationUtils.findAnnotation(method, AfterClass.class) != null);
    }

    public static boolean isAnnotationBeforeOrAfterMethod(final Method method) {
        return (AnnotationUtils.findAnnotation(method, BeforeMethod.class) != null) ||
                (AnnotationUtils.findAnnotation(method, AfterMethod.class) != null);
    }

    private static Method getTestMethod(final ITestResult tr) {
        Object[] parameters = tr.getParameters();
        if (parameters.length != 0) {
            for (final Object parameter : parameters) {
                if (parameter instanceof Method) {
                    return (Method) parameter;
                }
            }
        }
        return null;
    }
}
