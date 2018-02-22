package org.incubating.mockmvc.html;

import org.jsoup.nodes.Document;

public interface DocumentMatcher {

    void match(Document document);
}
