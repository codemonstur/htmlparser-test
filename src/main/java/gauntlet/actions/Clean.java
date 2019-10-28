package gauntlet.actions;

import gauntlet.Repository;
import gauntlet.model.CliArguments;
import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import static gauntlet.Repository.loadHostnamesFile;
import static gauntlet.Repository.saveHostnamesFile;

public enum Clean {;

    public static void cleanBroken(final CliArguments arguments, final File repoDir) throws IOException {
        final Hostnames hostnames = loadHostnamesFile(repoDir);
        hostnames.broken.retainAll(hostnames.downloaded);
        saveHostnamesFile(repoDir, hostnames);
    }

    public static void cleanDownloaded(final CliArguments arguments, final File repoDir) throws IOException {
        final Hostnames hostnames = loadHostnamesFile(repoDir);

        final Set<String> realDownloaded = new HashSet<>();
        Files
            .walk(repoDir.toPath())
            .filter(path -> path.endsWith(".source"))
            .map(Repository::sourceToDomain)
            .forEach(realDownloaded::add);
        hostnames.downloaded = realDownloaded;

        saveHostnamesFile(repoDir, hostnames);
    }

}
