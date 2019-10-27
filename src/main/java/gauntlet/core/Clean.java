package gauntlet.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static gauntlet.util.Constants.*;
import static gauntlet.util.IO.*;
import static java.lang.String.join;

public enum Clean {;

    public static void main(final String... args) throws IOException {
        cleanList(DOMAINS_ALL);
        cleanList(DOMAINS_BROKEN);
        cleanList(DOMAINS_FAILURE);
        cleanList(DOMAINS_SUCCESS);
    }

    private static void cleanList(final String filename) throws IOException {
        final Set<String> domains = new HashSet<>(toLines("/"+ filename));
        writeString(join(NEW_LINE, domains), newFileWithParents(DIR_LOGS, filename));
    }
}
