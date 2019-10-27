package gauntlet.core;

import com.google.gson.Gson;
import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static gauntlet.util.Convert.*;
import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum Repository {;

    private static final Gson GSON = new Gson();
    public static Hostnames load(final File file) throws IOException {
        return GSON.fromJson(Files.readString(file.toPath()), Hostnames.class);
    }

    public static void save(final File file, final Hostnames hostnames) throws IOException {
        Files.writeString(file.toPath(), GSON.toJson(hostnames), CREATE, TRUNCATE_EXISTING);
    }

    public static File sourceToResult(final File source) {
        final String path = source.getAbsolutePath();
        return new File(path.substring(0, path.lastIndexOf("."))+".result");
    }

    public static File toRepositorySourceFile(final File repoDir, final String hostname) throws IOException {
        return toRepositoryFile(repoDir, hostname, "source");
    }
    public static File toRepositoryResultFile(final File repoDir, final String hostname) throws IOException {
        return toRepositoryFile(repoDir, hostname, "result");
    }
    private static File toRepositoryFile(final File repoDir, final String hostname, final String suffix) throws IOException {
        final String hash = bytesToHex(md5(hostname, null));
        if (hash == null) throw new IOException("Hashing domain with MD5 failed");

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
