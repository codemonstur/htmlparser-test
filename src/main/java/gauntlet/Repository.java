package gauntlet;

import com.google.gson.Gson;
import gauntlet.model.Hostnames;
import gauntlet.model.Metrics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static gauntlet.util.Convert.*;
import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum Repository {;

    public static File validateRepositoryDirectory(final String name) throws IOException {
        final File repoDir = new File(name);
        if (repoDir.isDirectory()) return repoDir;

        throw new IOException("Can't find repository directory: " + repoDir.getAbsolutePath());
    }

    private static final Gson GSON = new Gson();
    public static Hostnames loadHostnamesFile(final File repoDir) throws IOException {
        final File hostnamesFile = new File(repoDir, "repository-urls.json");
        if (!hostnamesFile.exists()) return new Hostnames();

        return GSON.fromJson(Files.readString(hostnamesFile.toPath()), Hostnames.class);
    }
    public static void saveHostnamesFile(final File repoDir, final Hostnames hostnames) throws IOException {
        final File hostnamesFile = new File(repoDir, "repository-urls.json");
        Files.writeString(hostnamesFile.toPath(), GSON.toJson(hostnames), CREATE, TRUNCATE_EXISTING);
    }

    public static Metrics loadMetricsFile(final File repoDir) throws IOException {
        final File metricsFile = new File(repoDir, "metrics.json");
        if (!metricsFile.exists()) return new Metrics(0, 0, 0, 0, 0.0);

        return GSON.fromJson(Files.readString(metricsFile.toPath()), Metrics.class);
    }
    public static void saveMetricsFile(final File repoDir, final Metrics metrics) throws IOException {
        final File metricsFile = new File(repoDir, "metrics.json");
        Files.writeString(metricsFile.toPath(), GSON.toJson(metrics), CREATE, TRUNCATE_EXISTING);
    }

    public static File sourceToResult(final File source) {
        final String path = source.getAbsolutePath();
        return new File(path.substring(0, path.lastIndexOf("."))+".result");
    }
    public static String sourceToDomain(final Path sourceFile) {
        final String filename = sourceFile.toFile().getName();
        return filename.substring(11, filename.length()-12);
    }

    public static File toRepositorySourceFile(final File repoDir, final String hostname) throws IOException {
        return toRepositoryFile(repoDir, hostname, "source");
    }
    private static File toRepositoryFile(final File repoDir, final String hostname, final String suffix) throws IOException {
        final String hash = bytesToHex(md5(hostname, null));
        if (hash == null) throw new IOException("Hashing hostname with MD5 failed");

        final String date = now(YYYY_MM_DD);
        final String filename = format
            ( "%s/%s/%s/%s-%s.%s"
            , hash.substring(0, 2)
            , hash.substring(2, 4)
            , hash.substring(4, 6)
            , date
            , hostname
            , suffix);

        final File repoFile = new File(repoDir, filename);
        repoFile.getParentFile().mkdirs();
        return repoFile;
    }

}
