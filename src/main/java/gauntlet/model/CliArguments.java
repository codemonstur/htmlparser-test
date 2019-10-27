package gauntlet.model;

import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;

public class CliArguments {

    @CliPositional(index = 0, defaultValue = "")
    public String action;

    @CliOption(name = 'd', longName = "repo-dir", defaultValue = "data")
    public String repoDir;

    @CliOption(name = 'h', longName = "help", isHelp = true)
    public boolean help;

}
