package gauntlet.model;

import java.util.List;

import static gauntlet.model.ParseResult.*;
import static gauntlet.util.Convert.count;
import static java.lang.String.format;

public final class Metrics {

    public final int numAll;
    public final int numFailure;
    public final int numSuccess;
    public final int numMissing;
    public final double percentage;

    public Metrics(final int numAll, final int numFailure, final int numSuccess, final int numMissing,
                    final double percentage) {
        this.numAll = numAll;
        this.numFailure = numFailure;
        this.numSuccess = numSuccess;
        this.numMissing = numMissing;
        this.percentage = percentage;
    }

    public static Metrics calculateMetrics(final List<ParseResult> results) {
        final int numAll = results.size();
        final int numFailure = count(results, failed);
        final int numSuccess = count(results, success);
        final int numMissing = count(results, missing);

        final double percentage = numSuccess * 100 / (double)(numSuccess + numFailure);
        return new Metrics(numAll, numFailure, numSuccess, numMissing, percentage);
    }

    public static Metrics printMetrics(final Metrics metrics) {
        System.out.println("Number of missing tests    : " + metrics.numMissing);
        System.out.println("Number of failed tests     : " + metrics.numFailure);
        System.out.println("Number of successful tests : " + metrics.numSuccess);
        System.out.println("Total number of tests      : " + metrics.numAll);
        System.out.println(format("\nSuccess percentage: %.2f", metrics.percentage));
        return metrics;
    }

}
