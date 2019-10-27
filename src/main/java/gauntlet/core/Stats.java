package gauntlet.core;

import java.io.IOException;

import static gauntlet.util.Constants.*;
import static gauntlet.util.IO.toLines;
import static java.lang.String.format;

public enum Stats {;

    public static void main(final String... args) throws IOException {
        final int numAll = countDomains(DOMAINS_ALL);
        final int numBroken = countDomains(DOMAINS_BROKEN);
        final int numFailure = countDomains(DOMAINS_FAILURE);
        final int numSuccess = countDomains(DOMAINS_SUCCESS);

        System.out.println("Number of broken domains  : " + numBroken);
        System.out.println("Number of failure domains : " + numFailure);
        System.out.println("Number of success domains : " + numSuccess);
        System.out.println("Total number of domains   : " + numAll);

        final double percentage = numSuccess * 100 / (double)(numSuccess+numFailure);
        System.out.println(format("\nSuccess percentage: %.2f", percentage));
    }

    private static int countDomains(final String filename) throws IOException {
        return toLines("/"+filename).size();
    }
}
