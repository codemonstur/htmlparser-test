import gauntlet.Repository;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TestRepository {

    @Test
    public void testSourceToParsed() {
        final File source = new File("data/bla/dontcare/something.source");
        final File output = new File("data/bla/dontcare/something.result");
        final File parsed = Repository.sourceToResult(source);

        assertEquals("Target filenames are not equal", output.getAbsolutePath(), parsed.getAbsolutePath());
    }

    @Test
    public void testSourceToDomain() {
        final File source = new File("data/bla/dontcare/2019-10-20-www.example.com.source");
        final String output = Repository.sourceToDomain(source.toPath());

        assertEquals("Target domain is wrong", "www.example.com", output);
    }

    @Test
    public void testSourceToResult() {
        final File source = new File("data/bla/dontcare/2019-10-20-www.example.com.source");
        final File expected = new File("data/bla/dontcare/2019-10-20-www.example.com.result");
        final File output = Repository.sourceToResult(source);

        assertEquals("Target domain is wrong", expected.getAbsolutePath(), output.getAbsolutePath());
    }

}
