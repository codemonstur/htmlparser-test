package gauntlet.actions;

import gauntlet.model.CliArguments;
import htmlparser.HtmlParser;
import htmlparser.core.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static gauntlet.Repository.sourceToResult;
import static htmlparser.HtmlParser.newHtmlParser;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum Parse {;

    public static void parseAllRepoFiles(final CliArguments arguments, final File repoDir) throws IOException {
        final HtmlParser parser = newHtmlParser().shouldPrettyPrint(false).build();

        Files
            .walk(repoDir.toPath())
            .filter(path -> path.endsWith(".source"))
            .forEach(path -> parseSingleRepoFile(path, parser));
    }

    public static void parseUnparsedRepoFiles(final CliArguments arguments, final File repoDir) throws IOException {
        final HtmlParser parser = newHtmlParser().shouldPrettyPrint(false).build();

        Files
            .walk(repoDir.toPath())
            .filter(path -> path.endsWith(".source"))
            .filter(path -> !sourceToResult(path.toFile()).exists())
            .forEach(path -> parseSingleRepoFile(path, parser));
    }

    private static void parseSingleRepoFile(final Path path, final HtmlParser parser) {
        try {
            final String source = Files.readString(path);
            final Tag intermediate = parser.fromHtml(source);
            Files.writeString(sourceToResult(path.toFile()).toPath(), parser.toHtml(intermediate), CREATE, TRUNCATE_EXISTING);
        } catch (Throwable e) {
            System.out.println("FAILED to parse file " + path);
        }
    }

}