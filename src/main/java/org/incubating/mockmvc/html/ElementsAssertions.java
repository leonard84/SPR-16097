package org.incubating.mockmvc.html;

import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ElementsAssertions implements DocumentMatcher {

    private List<Matcher<? super Elements>> matchers = new ArrayList<>();

    private final String css;

    public ElementsAssertions(String css) {
        this.css = css;
    }

    protected String getCss() {
        return css;
    }

    @Override
    public void match(Document document) {
        Elements elements = document.select(getCss());
        MatcherAssert.assertThat(elements, new AllOfMatcher<>(
                description -> description.appendText("elements selected by ").appendValue(getCss())
                        .appendText(" matching "), matchers));
    }


    public EachElementAssertions each() {
        return new EachElementAssertions(css, this);
    }

    public ElementsAssertions hasCount(int count) {
        matchers.add(hasSize(count));
        return this;
    }
}
