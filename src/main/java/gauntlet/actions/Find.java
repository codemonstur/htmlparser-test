package gauntlet.actions;

import gauntlet.model.CliArguments;
import gauntlet.util.Counter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public enum Find {;

    public static void findNotParsable(final CliArguments arguments, final File repoDir) throws IOException {
        final Counter numParsed = new Counter();
        Files
            .walk(repoDir.toPath())
            .filter(path -> path.toFile().getName().endsWith(".result"))
            .filter(path -> path.toFile().length() == 0)
            .peek(path -> numParsed.increment())
            .forEach(System.out::println);
    }

}
