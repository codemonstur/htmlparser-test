package gauntlet.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public enum HTTP {;

    private static OkHttpClient HTTP = new OkHttpClient();
    public static String downloadHtml(final String domain) throws IOException {
        final Request request = new Request.Builder().url("http://"+domain).get().build();

        try (final Response response = HTTP.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("not a 200 OK: " + response.code());
            final String header = response.header("Content-Type");
            if (header == null || !header.startsWith("text/html")) throw new IOException("unknown content-type: " + header);
            final ResponseBody body = response.body();
            if (body == null) throw new IOException("no body");

            return body.string();
        } catch (IOException e) {
            throw new IOException("[HTTP] " + domain + " " + e.getMessage(), e);
        }
    }

}
