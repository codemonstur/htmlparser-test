package gauntlet.core;

import htmlparser.HtmlParser;

import java.io.IOException;
import java.util.List;

import static gauntlet.util.IO.resourceAsString;
import static htmlparser.HtmlParser.newHtmlParser;
import static java.util.Arrays.asList;

public class Snippets {

    public static void main(final String... args) throws IOException {

        final List<String> snippets = asList
            ( resourceAsString("/snippets/conditional_comments.html")
            , resourceAsString("/snippets/doctype.html")
            , resourceAsString("/snippets/mixed_quotes.html")
            , resourceAsString("/snippets/single_quotes.html")
            , resourceAsString("/snippets/tags_inside_tags.html")
            , resourceAsString("/snippets/unbalanced_tags.html")
            );

        final HtmlParser parser = newHtmlParser().shouldPrettyPrint(false).build();

        for (final String snippet : snippets) {
            String s = parser.toHtml(parser.fromHtml(snippet));
            if (snippet.equals(s)) {
                System.out.println("Snippet succeeded: " + snippet);
            } else {
                System.out.println("Snippet failed");
                System.out.println(snippet);
                System.out.println(s);
            }
        }
    }
}
