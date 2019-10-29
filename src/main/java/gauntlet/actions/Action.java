package gauntlet.actions;

import gauntlet.model.CliArguments;

import java.io.File;
import java.io.IOException;

public interface Action {
    void run(CliArguments arguments, File repoDir) throws IOException;
}
