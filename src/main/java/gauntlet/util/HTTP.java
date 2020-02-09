package gauntlet.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

public enum HTTP {;

    private static HttpClient HTTP = HttpClient.newBuilder().build();
    public static String downloadHtml(final String domain) throws IOException {
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://"+domain)).GET().build();

        try {
            final var response = HTTP.send(request, BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() > 299)
                throw new IOException("not a 200 OK: " + response.statusCode());

            final Optional<String> header = response.headers().firstValue("Content-Type");
            if (header.isEmpty()) throw new IOException("missing content-type header");
            if (!header.get().startsWith("text/html")) throw new IOException("unknown content-type: " + header);

            return response.body();
        } catch (InterruptedException e) {
            throw new IOException("Interrupted during downloading " + domain);
        }
    }

}
