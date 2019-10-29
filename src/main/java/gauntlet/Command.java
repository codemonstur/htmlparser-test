package gauntlet;

import gauntlet.actions.*;
import gauntlet.model.CliArguments;

import java.io.File;
import java.io.IOException;

// Here I need a @CliUnderscoreToDash
public enum Command {
    clean_broken(Clean::cleanBroken),
    clean_downloaded(Clean::cleanDownloaded),
    collect_stats(Stats::collectStats),
    download_new(Download::addNewHostnames),
    download_broken(Download::downloadBrokenHostnames),
    find_not_parsable(Find::findNotParsable),
    list_failed(List::listFailed),
    parse_all(Parse::parseAllRepoFiles),
    parse_unparsed(Parse::parseUnparsedRepoFiles),
    print_stats(Stats::printStats),
    snippets(Snippets::runSnippets),
    dummy(Dummy::printMissingAction);

    private final Action action;
    Command(final Action action) {
        this.action = action;
    }

    public static Command toCommand(final CliArguments arguments, final Command defaultValue) {
        try {
            return Command.valueOf(arguments.action.replaceAll("-", "_"));
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public void execute(final CliArguments arguments, File repoDir) throws IOException {
        action.run(arguments, repoDir);
    }
}
