package gauntlet.util;

import gauntlet.Main;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public enum IO {;

    public static List<String> toLines(final String resource) throws IOException {
        final List<String> lines = new ArrayList<>();

        try (final BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(resource)))) {
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    public static boolean isRealFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static File newFileWithParents(final String dir, final String name) {
        final File file = new File(dir, name);
        file.getParentFile().mkdirs();
        return file;
    }

    public static String readString(final File file) throws IOException {
        return Files.readString(file.toPath());
    }
    public static String writeString(final String content, final File file) throws IOException {
        Files.writeString(file.toPath(), content, CREATE, TRUNCATE_EXISTING);
        return content;
    }

    public static String resourceAsString(final String resource) throws IOException {
        try (final InputStream in = IO.class.getResourceAsStream(resource)) {
            if (in == null) throw new IOException("Resource " + resource + " does not exist.");
            return streamAsString(in, UTF_8);
        }
    }

    public static String streamAsString(final InputStream in, final Charset charset) throws IOException {
        try (final ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[1024]; int read;
            while ((read = in.read(buffer)) != -1) {
                result.write(buffer, 0, read);
            }
            return result.toString(charset.name());
        }
    }

}
