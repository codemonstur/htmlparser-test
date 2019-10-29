package gauntlet.actions;

import gauntlet.error.StreamClosed;
import gauntlet.model.CliArguments;
import gauntlet.util.Counter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static gauntlet.Repository.sourceToResult;

public enum List {;

    public static void listFailed(final CliArguments arguments, final File repoDir) throws IOException {
        final Counter numParsed = new Counter();

        try {
            Files
                .walk(repoDir.toPath())
                .filter(path -> path.toFile().getName().endsWith(".source"))
                .filter(path -> sourceToResult(path.toFile()).exists())
                .filter(List::sourceAndResultNotEqual)
                .peek(path -> numParsed.increment())
                .forEach(path -> {
                    if (numParsed.get() < arguments.number)
                        System.out.println(path);
                    else
                        throw new StreamClosed();
                });
        } catch (StreamClosed e) {}
    }

    private static boolean sourceAndResultNotEqual(final Path path) {
        try {
            final String source = Files.readString(path);
            final String result = Files.readString(sourceToResult(path.toFile()).toPath());
            return !source.equals(result);
        } catch (IOException e) {
            return false;
        }
    }

}
