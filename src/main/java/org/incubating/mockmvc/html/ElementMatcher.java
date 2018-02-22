package org.incubating.mockmvc.html;

import org.hamcrest.Matcher;
import org.jsoup.nodes.Element;

abstract class ElementMatcher extends CssMatcher<Element> {
    private final Matcher<? super Element> matcher;

    protected ElementMatcher(String css, Matcher<? super Element> matcher) {
        super(css);
        this.matcher = matcher;
    }

    Matcher<? super Element> getMatcher() {
        return matcher;
    }
}
