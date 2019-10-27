package gauntlet.model;

import java.util.Set;

public final class Hostnames {

    public final Set<String> broken;
    public final Set<String> downloaded;

    public Hostnames(final Set<String> broken, final Set<String> downloaded) {
        this.broken = broken;
        this.downloaded = downloaded;
    }

}
