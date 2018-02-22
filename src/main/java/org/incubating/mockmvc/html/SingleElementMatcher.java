package org.incubating.mockmvc.html;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SingleElementMatcher extends ElementMatcher {
    public SingleElementMatcher(String css, Matcher<? super Element> matcher) {
        super(css, matcher);
    }

    @Override
    public void match(Document document) {
        Elements select = document.select(getCss());
        assertThat("Single or null Element expected for \"" + getCss() + '"', select.size(), is(lessThan(2)));
        assertThat(select.first(), this);
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        boolean matches = getMatcher().matches(item);
        if (!matches) {
            getMatcher().describeMismatch(item, mismatchDescription);
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a single element selected by ")
                .appendValue(getCss()).appendText(" matching ").appendDescriptionOf(getMatcher());
    }
}
