package gauntlet;

import gauntlet.core.Repository;
import gauntlet.model.CliArguments;
import gauntlet.model.Hostnames;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static gauntlet.core.Download.addNewHostnames;
import static gauntlet.util.Constants.*;
import static gauntlet.util.IO.toLines;
import static jcli.CliParserBuilder.newCliParser;

public enum Main {;

    public static void main(final String... args) throws IOException {
        final CliArguments arguments = newCliParser(CliArguments::new)
            .onErrorPrintHelpAndExit()
            .onHelpPrintHelpAndExit()
            .parseSuppressErrors(args);

        switch (arguments.action) {
            case "download":
        }

        List<String> brokenDomains = toLines("/" + DOMAINS_BROKEN);
        List<String> failureDomains = toLines("/" + DOMAINS_FAILURE);
        List<String> successDomains = toLines("/" + DOMAINS_SUCCESS);

        final File repoDir = new File("data");
        System.out.println("Using repository directory: " + repoDir.getAbsolutePath());

        final Hostnames hostnames = new Hostnames(new HashSet<>(brokenDomains), new HashSet<>());
        addNewHostnames(failureDomains, repoDir, hostnames);
        addNewHostnames(successDomains, repoDir, hostnames);

        final File hostnamesFile = new File(repoDir, "repository-urls.json");
        Repository.save(hostnamesFile, hostnames);

    }

    private
}
