package gauntlet.model;

import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;

@CliCommand(name = "gauntlet", description =
    "A tool for testing the htmlparser using a repository of HTML files.\n\n" +
    "Available actions are:\n" +
    "- clean-broken     : Remove all hostnames from the broken list that are in the downloaded list\n" +
    "- clean-downloaded : Rebuild the downloaded list from what is in the repository\n" +
    "- collect-stats    : Process all files in the repo and print statistics\n" +
    "- download-new     : Download new hostnames and add the HTML to the repository\n" +
    "- download-broken  : Re-download the known broken hostnames\n" +
    "- list-failed      : List a number of source HTML files that failed to parse\n" +
    "- parse-all        : Parse all HTML in the repository and write the results\n" +
    "- parse-unparsed   : Parse only the unparsed HTML in the repository, and write the results\n" +
    "- print-stats      : Print the statistics currently known\n" +
    "- snippets         : Run the HtmlParser against known difficult problems")
public final class CliArguments {

    @CliPositional(index = 0, defaultValue = "")
    public String action;

    @CliOption(name = 'd', longName = "repo-dir", defaultValue = "data", description = "The location of the repository, default is 'data'")
    public String repoDir;

    @CliOption(name = 'l', longName = "list", defaultValue = "", description = "A list of hostnames to add, if that command was given")
    public String hostnameList;

    @CliOption(name = 'h', longName = "help", isHelp = true, description = "This help")
    public boolean help;

    @CliOption(name = 'n', longName = "number", defaultValue = ""+Integer.MAX_VALUE, description = "The number of items to process, actual use depends on the action given")
    public int number;

}
