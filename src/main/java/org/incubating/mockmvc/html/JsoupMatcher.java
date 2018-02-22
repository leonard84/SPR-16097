package org.incubating.mockmvc.html;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupMatcher {

    private final List<DocumentMatcher> documentMatchers;

    public JsoupMatcher(List<DocumentMatcher> documentMatchers) {
        this.documentMatchers = new ArrayList<>(documentMatchers);
    }

    public void match(String html) {
        Document document = Jsoup.parse(html);
        documentMatchers.forEach(documentMatcher -> documentMatcher.match(document));
    }
}
