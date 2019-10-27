package gauntlet.model;

public interface TestResults {

    void success(final String domain, final String htmlOutput);
    void failure(final String domain, final String htmlOutput);
    void error(final String domain, final Throwable e);

    default void result(final boolean success, final String domain, final String result) {
        if (success) {
            success(domain, result);
        } else {
            failure(domain, result);
        }
    }

}
