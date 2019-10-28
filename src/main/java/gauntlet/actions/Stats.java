package gauntlet.actions;

import gauntlet.model.CliArguments;
import gauntlet.model.ParseResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static gauntlet.Repository.loadMetricsFile;
import static gauntlet.Repository.sourceToResult;
import static gauntlet.actors.MetricActor.newMetricActor;
import static gauntlet.model.Metrics.printMetrics;
import static gauntlet.model.ParseResult.*;
import static java.util.stream.Collectors.toList;

public enum Stats {;

    public static void printStats(final CliArguments arguments, final File repoDir) throws IOException {
        printMetrics(loadMetricsFile(repoDir));
    }

    public static void collectStats(final CliArguments arguments, final File repoDir) throws IOException {
        final List<ParseResult> results = Files
            .walk(repoDir.toPath())
            .filter(path -> path.toFile().getName().endsWith(".source"))
            .map(Stats::isSourceEqualToParsed)
            .collect(toList());

        newMetricActor()
            .calculateMetrics(results)
            .printMetrics()
            .saveMetrics(repoDir);
    }

    private static ParseResult isSourceEqualToParsed(final Path sourceFile) {
        final File parsedFile = sourceToResult(sourceFile.toFile());
        if (!parsedFile.exists()) return missing;

        try {
            final String sourceContent = Files.readString(sourceFile);
            final String parsedContent = Files.readString(parsedFile.toPath());
            return sourceContent.equals(parsedContent) ? success : failed;
        } catch (Exception e) {
            return error;
        }
    }

}
