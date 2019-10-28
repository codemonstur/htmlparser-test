package gauntlet.actors;

import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static gauntlet.Repository.loadHostnamesFile;
import static gauntlet.Repository.saveHostnamesFile;

public final class DownloadActor {
    public static DownloadActor newDownloadActor() {
        return new DownloadActor();
    }

    private Hostnames hostnames;
    private File repoDir;
    public DownloadActor loadHostnames(final File repoDir) throws IOException {
        this.repoDir = repoDir;
        this.hostnames = loadHostnamesFile(repoDir);
        return this;
    }
    public DownloadActor withHostnames(final File repoDir, final Hostnames hostnames) {
        this.repoDir = repoDir;
        this.hostnames = hostnames;
        return this;
    }

    public HostnamesAdder addNewHostnames() {
        return new HostnamesAdder(this);
    }
    public HostnamesAdder redownloadBroken() {
        return new HostnamesAdder(this).withNew(hostnames.broken);
    }

    public void saveResults() throws IOException {
        saveHostnamesFile(repoDir, hostnames);
    }

    public static class HostnamesAdder {

        private DownloadActor actor;
        private int increment = Integer.MAX_VALUE;
        private DownloadHostname downloader;
        private List<Predicate<String>> filters = new ArrayList<>();
        private Collection<String> newHostnames;

        public HostnamesAdder(final DownloadActor actor) {
            this.actor = actor;
        }
        public HostnamesAdder withNew(final Collection<String> newHostnames) {
            this.newHostnames = newHostnames;
            return this;
        }

        public HostnamesAdder saveEvery(final int increment) {
            this.increment = increment;
            return this;
        }

        public HostnamesAdder filter(final Predicate<String> filter) {
            this.filters.add(filter);
            return this;
        }

        public HostnamesAdder addWith(final DownloadHostname downloader) {
            this.downloader = downloader;
            return this;
        }

        public DownloadActor run() throws IOException {
            int numProcessed = 0;
            DOWNLOAD: for (final String hostname : newHostnames) {
                for (final Predicate<String> filter : filters) {
                    if (filter.test(hostname))
                        continue DOWNLOAD;
                }

                downloader.download(hostname, actor.repoDir, actor.hostnames);
                numProcessed++;

                if (numProcessed % increment == 0)
                    saveHostnamesFile(actor.repoDir, actor.hostnames);
            }
            return actor;
        }
    }

    public interface DownloadHostname {
        boolean download(String hostname, File repoDir, Hostnames hostnames);
    }
}
