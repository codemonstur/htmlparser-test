package gauntlet.actions;

import gauntlet.model.CliArguments;
import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static gauntlet.Repository.loadHostnamesFile;
import static gauntlet.Repository.toRepositorySourceFile;
import static gauntlet.actors.DownloadActor.newDownloadActor;
import static gauntlet.util.HTTP.downloadHtml;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.writeString;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum Download {;

    public static void downloadBrokenHostnames(final CliArguments arguments, final File repoDir) throws IOException {
        newDownloadActor()
            .loadHostnames(repoDir)
            .redownloadBroken()
                .saveEvery(50)
                .addWith(Download::downloadHostname)
                .run()
            .saveResults();
    }

    public static void addNewHostnames(final CliArguments arguments, final File repoDir) throws IOException {
        addNewHostnames(readAllLines(Paths.get(arguments.hostnameList)), repoDir);
    }
    public static void addNewHostnames(final List<String> newHostnames, final File repoDir) throws IOException {
        final Hostnames hostnames = loadHostnamesFile(repoDir);

        newDownloadActor()
            .withHostnames(repoDir, hostnames)
            .addNewHostnames()
                .withNew(newHostnames)
                .saveEvery(50)
                .filter(hostnames.broken::contains)
                .filter(hostnames.downloaded::contains)
                .addWith(Download::downloadHostname)
                .run()
            .saveResults();
    }

    private static boolean downloadHostname(final String hostname, final File repoDir, final Hostnames hostnames) {
        try {
            final String html = downloadHtml(hostname);
            writeString(toRepositorySourceFile(repoDir, hostname).toPath(), html, CREATE, TRUNCATE_EXISTING);
            hostnames.downloaded.add(hostname);
            System.out.println(" [INFO] " + hostname);
            return true;
        } catch (Exception e) {
            hostnames.broken.add(hostname);
            System.out.println("[ERROR] " + hostname + ", error is: " + e.getMessage());
            return false;
        }
    }

}
