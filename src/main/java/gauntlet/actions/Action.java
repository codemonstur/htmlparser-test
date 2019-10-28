package gauntlet.actions;

import gauntlet.model.CliArguments;

import java.io.File;
import java.io.IOException;

// Here I need a @CliUnderscoreToDash
public enum Action {
    clean_broken(Clean::cleanBroken),
    clean_downloaded(Clean::cleanDownloaded),
    collect_stats(Stats::collectStats),
    download_new(Download::addNewHostnames),
    download_broken(Download::downloadBrokenHostnames),
    find_not_parsable(Find::findNotParsable),
    parse_all(Parse::parseAllRepoFiles),
    parse_unparsed(Parse::parseUnparsedRepoFiles),
    print_stats(Stats::printStats),
    snippets(Snippets::runSnippets),
    dummy(Dummy::printMissingAction);

    private final Task task;
    Action(final Task task) {
        this.task = task;
    }

    public static Action toAction(final CliArguments arguments, final Action defaultValue) {
        try {
            return Action.valueOf(arguments.action.replaceAll("-", "_"));
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public interface Task {
        void run(CliArguments arguments, File repoDir) throws IOException;
    }

    public void execute(final CliArguments arguments, File repoDir) throws IOException {
        task.run(arguments, repoDir);
    }
}
