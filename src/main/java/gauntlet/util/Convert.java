package gauntlet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ofPattern;

public enum Convert {;

    private static final char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(final byte[] bytes) {
        if (bytes == null) return null;

        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static final DateTimeFormatter YYYY_MM_DD = ofPattern("yyyy-MM-dd")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault());
    public static String now(final DateTimeFormatter format) {
        return format.format(Instant.now());
    }

    public static byte[] md5(final String data, final byte[] defaultValue) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            return digest.digest(data.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return defaultValue;
        }
    }

    public static <T> int count(final List<T> results, final T value) {
        int total = 0;
        for (final T result : results) {
            if (Objects.equals(value, result)) total++;
        }
        return total;
    }

}
