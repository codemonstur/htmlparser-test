package gauntlet.core;

import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static gauntlet.core.Repository.toRepositorySourceFile;
import static gauntlet.util.HTTP.downloadHtml;
import static gauntlet.util.IO.writeString;

public enum Download {;

    public static void retestBrokenHostnames(final Hostnames hostnames, final File repoDir) {
        for (final String hostname : hostnames.broken) {
            try {
                downloadFile(hostname, repoDir);
                hostnames.downloaded.add(hostname);
                hostnames.broken.remove(hostname);
            } catch (Exception e) {
                System.out.println("Failed to add " + hostname + ", error is: " + e.getMessage());
            }
        }
    }

    public static void addNewHostnames(final List<String> newHostnames, final File repoDir,
                                       final Hostnames hostnames) {
        for (final String hostname : newHostnames) {
            if (hostnames.broken.contains(hostname)) continue;
            if (hostnames.downloaded.contains(hostname)) continue;

            try {
                downloadFile(hostname, repoDir);
                hostnames.downloaded.add(hostname);
            } catch (Exception e) {
                System.out.println("Failed to add " + hostname + ", error is: " + e.getMessage());
                hostnames.broken.add(hostname);
            }
        }
    }

    private static void downloadFile(final String hostname, final File repoDir) throws IOException {
        writeString(downloadHtml(hostname), toRepositorySourceFile(repoDir, hostname));
    }

}
