package gauntlet.actions;

import gauntlet.model.CliArguments;

import java.io.File;

import static jcli.CliHelp.printHelp;

public enum Dummy {;

    public static void printMissingAction(final CliArguments arguments, final File repoDir) {
        System.out.println("No such action '" + arguments.action + "', no action taken\n");
        printHelp("gauntlet", CliArguments.class);
    }

}
