package gauntlet.actors;

import gauntlet.Repository;
import gauntlet.model.Metrics;
import gauntlet.model.ParseResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class MetricActor {
    public static MetricActor newMetricActor() {
        return new MetricActor();
    }

    private Metrics metrics;
    public MetricActor calculateMetrics(final List<ParseResult> results) {
        this.metrics = Metrics.calculateMetrics(results);
        return this;
    }
    public MetricActor printMetrics() {
        Metrics.printMetrics(metrics);
        return this;
    }
    public MetricActor saveMetrics(final File repoDir) throws IOException {
        Repository.saveMetricsFile(repoDir, metrics);
        return this;
    }
}
