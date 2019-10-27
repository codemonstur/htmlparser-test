package gauntlet.model;

public class SingleTestResult implements TestResults {

    public void success(final String domain, final String htmlOutput) {
        System.out.println("[SUCCESS] [COMPARE] " + domain + " parsed successfully.");
    }

    @Override
    public void failure(final String domain, final String htmlOutput) {
        System.out.println("[FAILURE] [COMPARE] " + domain + " failed.");
    }

    @Override
    public void error(String domain, Throwable e) {
        System.err.println("[ERROR] "+e.getClass()+" for " + domain);
    }

}
