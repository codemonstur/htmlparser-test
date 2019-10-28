package gauntlet.model;

import java.util.HashSet;
import java.util.Set;

public final class Hostnames {

    public final Set<String> broken;
    public Set<String> downloaded;

    public Hostnames() {
        this(new HashSet<>(), new HashSet<>());
    }
    public Hostnames(final Set<String> broken, final Set<String> downloaded) {
        this.broken = broken;
        this.downloaded = downloaded;
    }

}
