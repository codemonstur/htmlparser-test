package gauntlet.model;

import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;

@CliCommand(name = "gauntlet", description =
    "A tool for testing the htmlparser using a repository of HTML files.\n" +
    "Available actions are:\n" +
    "- download-new     : Download new hostnames and add the HTML to the repository\n" +
    "- download-broken  : Re-download the known broken hostnames\n" +
    "- collect-stats    : Process all files in the repo and print statistics\n" +
    "- print-stats      : Print the statistics currently known\n" +
    "- snippets         : Run the HtmlParser against known difficult problems\n" +
    "- parse-all        : Parse all HTML in the repository and write the results\n" +
    "- parse-unparsed   : Parse only the unparsed HTML in the repository, and write the results")
public final class CliArguments {

    @CliPositional(index = 0, defaultValue = "")
    public String action;

    @CliOption(name = 'd', longName = "repo-dir", defaultValue = "data", description = "The location of the repository, default is 'data'")
    public String repoDir;

    @CliOption(name = 'l', longName = "list", defaultValue = "", description = "A list of hostnames to add, if that command was given")
    public String hostnameList;

    @CliOption(name = 'h', longName = "help", isHelp = true, description = "This help")
    public boolean help;

}
