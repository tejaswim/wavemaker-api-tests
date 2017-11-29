package com.wavemaker.api.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import com.wavemaker.api.config.StudioTestConfig;

import static com.wavemaker.api.report.ReportUtils.*;

public class ReportGenerator2 implements IReporter {

    private static final String SKIPPED = "skipped";
    private static final String FAILED = "failed";
    private static final String PASSED = "passed";
    private static final Logger logger = LoggerFactory.getLogger(ReportGenerator2.class);
    private static final StudioTestConfig studioTestConfig = StudioTestConfig.getInstance();

    private PrintWriter m_out;

    private int m_row;

    private Integer m_testIndex;

    private int m_methodIndex;

    /** Creates summary of the run */
    @Override
    public void generateReport(List<XmlSuite> xml, List<ISuite> suites, String outdir) {
        try {
            m_out = createWriter(outdir);
        } catch (IOException e) {
            logger.error("output file", e);
            return;
        }
        m_out.println(ReportUtils.getStartHtml(suites.get(0).getName()));
        generateOverviewSuites(suites);
        generateSuiteSummaryReport(suites);
        generateMethodSummaryReport(suites);
        generateMethodDetailReport(suites);
        endHtml(m_out);
        m_out.flush();
        m_out.close();
    }

    private void generateOverviewSuites(final List<ISuite> suites) {
        m_out.println("<table class='numi'>");
        m_out.println(
                "<tr><td colspan=\"5\" align=\"center\"><font color=\"#264b7f\" face=\"Arial\" size=\"2\"><b>Summary Report</b></font></td>");
        final String highlighter = " class=\"tdHighlighter\"";
        m_out.println("<tr " + highlighter + "><td>SUITENAME</td><td>PASSED</td><td>SKIPPED</td><td>FAILED</td" +
                "><td>TOTAL</td><tr>");
        int finalCount = 0, finalPass = 0, finalFail = 0, finalSkip = 0;
        for (ISuite iSuite : suites) {
            final Map<String, ISuiteResult> results = iSuite.getResults();
            int passedNum = 0;
            int failedNum = 0;
            int skippedNum = 0;
            int totalCount = 0;
            for (ISuiteResult iSuiteResult : results.values()) {
                ITestContext iTestContext = iSuiteResult.getTestContext();
                passedNum += iTestContext.getPassedTests().size();
                failedNum += iTestContext.getFailedTests().size();
                skippedNum += iTestContext.getSkippedTests().size();
            }
            totalCount += passedNum + failedNum + skippedNum;
            m_out.println("<tr><td" + highlighter + ">" + iSuite.getName().toUpperCase()
                    + "</td><td>" + passedNum + "</td><td>" + skippedNum + "</td><td>" + failedNum + "</td><td>"
                    + totalCount + "</td><tr>");
            finalPass += passedNum;
            finalFail += failedNum;
            finalSkip += skippedNum;
            finalCount += totalCount;
        }
        m_out.println(
                "<tr><td" + highlighter + ">TOTAL</td><td class=\"passed\">" + finalPass + "</td><td class=\"skipped\">" + finalSkip +
                        "</td><td class=\"numi_attn\">" + finalFail + "</td><td>" + finalCount + "</td><tr>");
        m_out.println("</table>");
    }

    protected PrintWriter createWriter(String outdir) throws IOException {
        Date now = new Date();
        new File(outdir).mkdirs();
        return new PrintWriter(new BufferedWriter(
                new FileWriter(new File(outdir, "wavemaker_testng_emailable_report_" + now.getTime() + ".html"))));
    }

    private void generateSuiteSummaryReport(List<ISuite> suites) {
        tableStart("testOverview", null);
        m_out.print("<tr>");
        String[] tableHeaders = {"Test", "Scenarios<br/>Passed", "# skipped", "# failed", "Total<br/> Tests",
                "Start<br/>Time", "End<br/>Time", "Total<br/>Time", "Included<br/>Groups",
                "Excluded<br/>Groups"};
        for (String tableHeader : tableHeaders) {
            tableColumnStart(tableHeader);
        }

        m_out.println("</tr>");
        long suiteStartTime = Long.MAX_VALUE;
        long suiteEndTime = Long.MIN_VALUE;
        m_testIndex = 1;
        int testCount = 0;
        for (ISuite suite : suites) {
            if (suites.size() > 1) {
                titleRow(suite.getName(), 8);
            }
            int totalPass = 0;
            int totalSkip = 0;
            int totaFail = 0;
            int qty_tests = 0;
            Map<String, ISuiteResult> tests = suite.getResults();
            for (ISuiteResult iSuiteResult : tests.values()) {
                qty_tests = qty_tests + 1;
                testCount = testCount + 1;
                ITestContext iTestContext = iSuiteResult.getTestContext();
                startSummaryRow(iTestContext.getName()); // test name
                int passedNum = iTestContext.getPassedTests().size();
                int failedNum = iTestContext.getFailedTests().size();
                int skippedNum = iTestContext.getSkippedTests().size();
                totalPass += passedNum;
                totalSkip += skippedNum;
                totaFail += failedNum;
                summaryCell(passedNum, Integer.MAX_VALUE); // scenario passed
                summaryCell(skippedNum, 0);
                summaryCell(failedNum, 0);
                summaryCell(passedNum + skippedNum + failedNum, Integer.MAX_VALUE);

                //time calculation
                Date contextStartDate = iTestContext.getStartDate();
                Date contextEndDate = iTestContext.getEndDate();
                long contextStartTime = iTestContext.getStartDate().getTime();
                long contextEndTime = iTestContext.getEndDate().getTime();

                if (testCount == 1) {
                    suiteStartTime = contextStartTime;
                }
                suiteEndTime = contextEndTime;
                summaryCell(contextStartDate.toString(), true);
                m_out.println("</td>");
                summaryCell(contextEndDate.toString(), true);
                m_out.println("</td>");

                summaryCell(ReportUtils.getTime(contextEndTime - contextStartTime), true);
                summaryCell(iTestContext.getIncludedGroups());
                summaryCell(iTestContext.getExcludedGroups());
                m_out.println("</tr>");
                m_testIndex++;
            }
            if (qty_tests > 1) {
                m_out.println("<tr class=\"total\"><td>Total</td>");
                summaryCell(totalPass, Integer.MAX_VALUE);
                summaryCell(totalSkip, 0);
                summaryCell(totaFail, 0);
                summaryCell(totaFail + totalPass + totalSkip, Integer.MAX_VALUE);
                summaryCell(" ", true);
                summaryCell(" ", true);
                summaryCell(ReportUtils.getTime(suiteEndTime - suiteStartTime), true);
                m_out.println("<td colspan=\"2\">&nbsp;</td></tr>");
            }
        }
        m_out.println("</table>");
    }

    /**
     * Creates a table showing the highlights of each test method with links to
     * the method details
     */
    private void generateMethodSummaryReport(List<ISuite> suites) {
        m_methodIndex = 0;
        startResultSummaryTable("methodOverview");
        int testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() > 1) {
                titleRow(suite.getName(), 5);
            }
            Map<String, ISuiteResult> results = suite.getResults();
            for (ISuiteResult result : results.values()) {
                ITestContext testContext = result.getTestContext();
                m_testIndex = testIndex;
                resultSummary(testContext.getAllTestMethods(), testContext.getFailedTests(),
                        testContext.getSkippedTests(), testContext.getPassedTests(), "");
                testIndex++;
            }
        }
        m_out.println("</table>");
    }

    /** Creates a section showing known results for each method */
    private void generateMethodDetailReport(List<ISuite> suites) {
        m_methodIndex = 0;
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                if (r.values().size() > 0) {
                    m_out.println("<h1>" + testContext.getName() + "</h1>");
                }
                resultDetail(testContext.getAllTestMethods(), testContext.getFailedTests(),
                        testContext.getSkippedTests(), testContext.getPassedTests());
            }
        }
    }

    private void resultSummary(
            ITestNGMethod[] tests, final IResultMap failedTests, final IResultMap skippedTests,
            final IResultMap passedTests, String details) {
        if (tests.length > 0) {
            StringBuilder buff = new StringBuilder();
            boolean isClassNamePrinted = false;
            int index = 0;
            for (ITestNGMethod method : tests) {
                m_methodIndex += 1;
                ITestClass testClass = method.getTestClass();
                String className = testClass.getName();
                Map<String, Set<ITestResult>> iTestResults = new HashMap<>();

                final Set<ITestResult> failedTestsresults = failedTests.getResults(method);
                if (failedTestsresults.size() > 0) {
                    iTestResults.put(FAILED, failedTestsresults);
                }

                final Set<ITestResult> skippedTestsresults = skippedTests.getResults(method);
                if (skippedTestsresults.size() > 0) {
                    iTestResults.put(SKIPPED, skippedTestsresults);
                }

                final Set<ITestResult> passedTestsresults = passedTests.getResults(method);
                if (passedTestsresults.size() > 0) {
                    iTestResults.put(PASSED, passedTestsresults);
                }

                String id = (m_testIndex == null ? null : "t" + Integer.toString(m_testIndex));
                if (!isClassNamePrinted) {
                    titleRow(className + details, 5, id);
                    isClassNamePrinted = true;
                }
                m_testIndex = null;
                long end = Long.MIN_VALUE;
                long start = Long.MAX_VALUE;
                StringBuilder parametersStr;

                for (Map.Entry<String, Set<ITestResult>> iTestResult : iTestResults.entrySet()) {
                    for (ITestResult testResult : iTestResult.getValue()) {
                        m_row += 1;
                        parametersStr = new StringBuilder();
                        if (testResult.getEndMillis() > end) {
                            end = testResult.getEndMillis();
                        }
                        if (testResult.getStartMillis() < start) {
                            start = testResult.getStartMillis();
                        }
                        buff.append("<tr class='" + iTestResult.getKey() + (index % 2 == 0 ? "odd" : "even") +
                                "'>");

                        for (Object param : testResult.getParameters()) {
                            parametersStr.append(param).append("<br>");
                        }

                        buff.append("<td><a href=\"#" + method.getMethodName() + "@" + testResult.hashCode() + "\">")
                                .append(qualifiedName(testResult.getMethod()))
                                .append(" ")
                                .append(StringUtils
                                        .isNotBlank(testResult.getMethod().getDescription()) ? "(\"" + testResult
                                        .getMethod().getDescription() + "\")"
                                        : "")
                                .append("</a></td>")
                                .append("<td>" + iTestResult.getKey() + "</td><td>")
                                .append(new Date(start).toString()).append("</td><td class=\"numi\">")
                                .append(ReportUtils.getTime(end - start)).append("</td></tr>");

                        index++;

                    }

                }
            }
            m_out.print(buff);
        }
    }

    private void resultDetail(
            final ITestNGMethod[] allTestMethods, IResultMap failedTests, IResultMap skippedTests,
            IResultMap passedTests) {
        String previousClassName = null;

        for (ITestNGMethod method : allTestMethods) {
            String cname = method.getRealClass().getSimpleName();
            if (previousClassName == null || !cname.equals(previousClassName)) {
                m_methodIndex++;
            }
            previousClassName = cname;
            if (failedTests.getAllMethods().contains(method)) {
                Set<ITestResult> failedTestsResults = failedTests.getResults(method);
                generateResultPerStatus(method, failedTestsResults);
            }
            if (skippedTests.getAllMethods().contains(method)) {
                Set<ITestResult> skippedTestsResults = skippedTests.getResults(method);
                generateResultPerStatus(method, skippedTestsResults);
            }
            if (passedTests.getAllMethods().contains(method)) {
                Set<ITestResult> passedTestsResults = passedTests.getResults(method);
                generateResultPerStatus(method, passedTestsResults);
            }
            m_out.println(
                    "<h2>" + cname + ":" + method.getMethodName() + " (\""
                            + method.getDescription() + "\") </h2>");
            m_out.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");
        }
    }

    private void generateResultPerStatus(final ITestNGMethod method, final Set<ITestResult> resultSet) {
        for (ITestResult result : resultSet) {
            generateForResult(result, method);
        }
    }

    /** Starts and defines columns result summary table */
    private void startResultSummaryTable(String style) {
        tableStart(style, "summary");
        m_out.println(
                "<tr><th>Method</th><th>Status</th><th>Start</th><th>Time</th></tr>");
        m_row = 0;
    }

    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    private void summaryCell(String v, boolean isgood) {
        m_out.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v + "</td>");
    }

    private void startSummaryRow(String label) {
        m_row += 1;
        m_out.print("<tr" + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
                + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t" + m_testIndex + "\">" + label + "</a>"
                + "</td>");
    }

    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void tableStart(String cssclass, String id) {
        m_out.println("<table style=\"clear:both;\" cellspacing=\"0\" cellpadding=\"0\""
                + (cssclass != null ? " class=\"" + cssclass + "\"" : " style=\"padding-bottom:2em\"")
                + (id != null ? " id=\"" + id + "\"" : "") + ">");
        m_row = 0;
    }

    private void tableColumnStart(String label) {
        m_out.print("<th>" + label + "</th>");
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }

    private void titleRow(String label, int cq, String id) {
        m_out.print("<tr");
        if (id != null) {
            m_out.print(" id=\"" + id + "\"");
        }
        m_out.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
        m_row = 0;
    }

    private String qualifiedName(ITestNGMethod method) {
        StringBuilder addon = new StringBuilder();
        String[] groups = method.getGroups();
        int length = groups.length;
        if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
            addon.append("(");
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    addon.append(", ");
                }
                addon.append(groups[i]);
            }
            addon.append(")");
        }

        return "<b>" + method.getMethodName() + "</b> " + addon;
    }

    private String getStatusString(final int status) {
        if (ITestResult.SUCCESS == status) {
            return PASSED;
        } else if (ITestResult.FAILURE == status) {
            return FAILED;
        } else if (ITestResult.SKIP == status) {
            return SKIPPED;
        } else {
            return "";
        }
    }

    private String generateForResult(ITestResult ans, ITestNGMethod method) {
        Object[] parameters = ans.getParameters();
        boolean hasParameters = parameters != null && parameters.length > 0;
        String contents = "";
        tableStart("result", null);
        m_out.print(
                "<tr class='" + getStatusString(ans.getStatus()) + "even' id='" + method.getMethodName() + "@" + ans
                        .hashCode() + "' " +
                        "class=\"param\">");
        if (hasParameters) {
            m_out.print("<th colspan=\"2\">Parameters</th>");
            int x = 1;
            for (Object p : parameters) {
                m_out.print("<tr class=\"param stripe " + x + "\">");
                m_out.print("<th>Parameter #" + x + "</th>");
                if (p != null) {
                    m_out.println("<td>" + Utils.escapeHtml(Utils.toString(p, p.getClass())) + "</td>");
                } else {
                    m_out.println("<td>" + p + "</td>");
                }
                m_out.println("</tr>");
                x++;
            }
            m_out.println("</tr>");
            m_out.print("<tr><th colspan=\"2\" align=\"left\">");
            if (ans.getStatus() == ITestResult.FAILURE) {
                List<String> msgs = Reporter.getOutput(ans);
                boolean hasReporterOutput = msgs.size() > 0;
                Throwable exception = ans.getThrowable();
                boolean hasThrowable = exception != null;
                if (hasThrowable) {
                    boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
                    if (hasReporterOutput) {
                        m_out.println("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure") + "</h3>");
                    }
                    generateExceptionReport(exception);
                    m_out.println("</th></tr>");
                    m_out.print("<tr><th colspan=\"2\" align=\"left\">");
                    contents = generateFailureReport(ans, method, contents);
                    m_out.println(contents);
                    m_out.println("</th></tr>");
                }
            }
            if (ans.getStatus() == ITestResult.SUCCESS) {
                contents = generateSuccessReport(ans, method, contents);
            }
            if (ans.getStatus() == ITestResult.SKIP) {
                contents = generateSkippedReport(ans, method, contents);
            }
            m_out.println("</table>");
        } else {
            if (ans.getStatus() == ITestResult.SUCCESS) {
                contents = generateSuccessReport(ans, method, contents);
            }
            if (ans.getStatus() == ITestResult.FAILURE) {
                List<String> msgs = Reporter.getOutput(ans);
                boolean hasReporterOutput = msgs.size() > 0;
                Throwable exception = ans.getThrowable();
                boolean hasThrowable = exception != null;
                if (hasThrowable) {
                    boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
                    if (hasReporterOutput) {
                        m_out.println("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure") + "</h3>");
                    }
                    generateExceptionReport(exception);
                    contents = generateFailureReport(ans, method, contents);
                    m_out.println(contents);
                }
            }
            if (ans.getStatus() == ITestResult.SKIP) {
                contents = generateSkippedReport(ans, method, contents);
            }
            m_out.println("</table>");
        }
        return contents;
    }

    private String generateSkippedReport(final ITestResult ans, final ITestNGMethod method, String contents) {
        try {
            contents += generateBeforeAnnotationLinksOnTestSkip(ans, method, contents);
        } catch (Exception e) {
            m_out.print("<tr><td>");
            e.printStackTrace(m_out);
            m_out.print("</td></tr>");
        }
        m_out.println(contents);
        return contents;
    }

    private String generateSuccessReport(final ITestResult ans, final ITestNGMethod method, String contents) {
        String executionLogsPath = studioTestConfig.getBrowser().equals("N/A") ? generateLogFilePath(ans) : generateLogFilePathWithHashCode(
                ans);
        try {
            contents +=
                    "<a class='textdecoration' target='blank' href='" + executionLogsPath + "'>Test Execution Logs</a> &nbsp;";
            if (studioTestConfig.getBrowser().equals("N/A")) {
                contents +=
                        "<a class='beforeclass' target='blank' href='" + generateBeforeClassLogFilePath(
                                ans) + "'>Execution Logs(@Class)</a> &nbsp;";
            }
        } catch (Exception e) {
            m_out.print("<tr><td>");
            e.printStackTrace(m_out);
            m_out.print("</td></tr>");
        }
        m_out.println(contents);
        return contents;
    }

    private String generateBeforeAnnotationLinksOnTestSkip(final ITestResult ans, final ITestNGMethod method, String contents) {
        String executionLogsPath = studioTestConfig.getBrowser().equals("N/A") ? generateLogFilePath(ans) : generateLogFilePathWithHashCode(
                ans);
        String serverLogFilePath = studioTestConfig.getBrowser().equals("N/A") ? generateServerLogFilePath(
                ans) : generateServerLogFilePathWithHashCode(ans);

        String applicationLogFilePath = studioTestConfig.getBrowser().equals("N/A") ? generateApplicationLogFilePath(
                ans) : generateApplicationLogFilePathWithHashCode(ans);


        if (ans.getStatus() == ITestResult.SKIP) {
            contents +=
                    "<a class='textdecoration' target='blank' href='" + executionLogsPath + "'>Test Execution Logs</a> &nbsp;";

            if (studioTestConfig.getBrowser().equals("N/A")) {
                contents +=
                        "<a class='beforeMethod' target='blank' href='" + serverLogFilePath + "'>Server logs</a> &nbsp;";
                contents +=
                        "<a class='beforeMethod' target='blank' href='" + applicationLogFilePath + "'>Application logs</a> &nbsp;";
                contents +=
                        "<a class='beforeclass' target='blank' href='" + generateBeforeClassLogFilePath(
                                ans) + "'>Execution Logs(@Class)</a> &nbsp;";
                contents +=
                        "<a class='beforeclass' target='blank' href='" + generateBeforeClassServerLogFilePath(
                                ans) + "'>Server logs(@Class) </a> &nbsp;";
                contents +=
                        "<a class='beforeclass' target='blank' href='" + generateBeforeClassApplicationLogFilePath(
                                ans) + "'>Application logs(@Class) </a> &nbsp;";
            }
        }
        return contents;
    }

    private String generateFailureReport(final ITestResult ans, final ITestNGMethod method, String contents) {
        String executionLogsPath = studioTestConfig.getBrowser().equals("N/A") ? generateLogFilePath(ans) : generateLogFilePathWithHashCode(
                ans);
        String serverLogFilePath = studioTestConfig.getBrowser().equals("N/A") ? generateServerLogFilePath(
                ans) : generateServerLogFilePathWithHashCode(ans);

        String applicationLogFilePath = studioTestConfig.getBrowser().equals("N/A") ? generateApplicationLogFilePath(
                ans) : generateApplicationLogFilePathWithHashCode(ans);

        try {
            contents +=
                    "<a class='failedTest' target='blank' href='" + executionLogsPath + "'>Test Execution Logs</a> &nbsp;";
            contents +=
                    "<a class='failedTest' target='blank' href='" + serverLogFilePath + "'> Server logs </a> &nbsp;";
            contents +=
                    "<a class='failedTest' target='blank' href='" + applicationLogFilePath + "'> Application logs </a> &nbsp;";

            contents += studioTestConfig.getBrowser().equals("N/A") ?
                    "<a class='beforeclass' target='blank' href='" + generateBeforeClassLogFilePath(
                            ans) + "'>Execution Logs(@Class)</a> &nbsp;" :
                    "<a class='failedTest' target='blank' href='" + generateScreenshotFilePath(ans) + "'> Screenshot </a> &nbsp;";
        } catch (Exception e) {
            m_out.print("<tr><td>");
            e.printStackTrace(m_out);
            m_out.print("</td></tr>");
        }
        return contents;
    }

    private void generateExceptionReport(Throwable exception) {
        m_out.print("<div class=\"stacktrace\">");
        m_out.print(Utils.stackTrace(exception, true)[0]);
        m_out.println("</div>");
    }

    /** Finishes HTML stream */
    private void endHtml(PrintWriter out) {
        out.println("</body></html>");
    }
}
