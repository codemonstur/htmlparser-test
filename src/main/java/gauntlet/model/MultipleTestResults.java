package gauntlet.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static gauntlet.util.Constants.*;
import static gauntlet.util.IO.writeString;

public class MultipleTestResults implements TestResults {

    private boolean reportSuccess;
    private boolean reportFailure;

    public final List<String> failure = new ArrayList<>();
    public final List<String> success = new ArrayList<>();
    public final List<String> broken = new ArrayList<>();

    public final List<String> log = new ArrayList<>();

    public MultipleTestResults(final boolean reportSuccess, final boolean reportFailure) {
        this.reportFailure = reportFailure;
        this.reportSuccess = reportSuccess;
    }

    public void success(final String domain, final String htmlOutput) {
        try {
            success.add(domain);
            log(reportSuccess, "[SUCCESS] [COMPARE] " + domain + " parsed successfully.");

            writeString(htmlOutput, new File(DIR_SUCCESS, domain + HTML_EXTENSION));
            new File(DIR_FAILURE, domain + HTML_EXTENSION).delete();
        } catch (Exception e) {
            System.err.println("Failed to register a success " + e.getMessage());
        }
    }
    public void failure(final String domain, final String htmlOutput) {
        try {
            failure.add(domain);
            log(reportFailure, "[FAILURE] [COMPARE] " + domain + " failed.");
            writeString(htmlOutput, new File(DIR_FAILURE, domain + HTML_EXTENSION));
            new File(DIR_SUCCESS, domain + HTML_EXTENSION).delete();
        } catch (Exception e) {
            System.err.println("Failed to register a failure" + e.getMessage());
        }
    }
    public void error(final String domain, final Throwable e) {
        broken.add(domain);
        log(reportFailure, "[ERROR] "+e.getClass()+" for " + domain);
    }

    private void log(final boolean report, final String message) {
        if (report) System.out.println(message);
        log.add(message);
    }

}
