package gauntlet;

import gauntlet.model.CliArguments;

import static gauntlet.Repository.validateRepositoryDirectory;
import static gauntlet.Command.dummy;
import static gauntlet.Command.toCommand;
import static gauntlet.util.App.runApp;
import static jcli.CliParserBuilder.newCliParser;

public enum Main {;

    public static void main(final String... args) {
        final CliArguments arguments = newCliParser(CliArguments::new)
            .onErrorPrintHelpAndExit()
            .onHelpPrintHelpAndExit()
            .parseSuppressErrors(args);

        runApp(() -> toCommand(arguments, dummy).execute(arguments, validateRepositoryDirectory(arguments.repoDir)));
    }

}
