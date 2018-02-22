package org.incubating.mockmvc.html;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ElementsMatcher extends CssMatcher<Elements> {
    private final Matcher<? super Elements> matcher;

    public ElementsMatcher(String css, Matcher<? super Elements> matcher) {
        super(css);
        this.matcher = matcher;
    }

    @Override
    public void match(Document document) {
        Elements select = document.select(getCss());
        assertThat(select, this);

    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        boolean matches = matcher.matches(item);
        if (!matches) {
            matcher.describeMismatch(item, mismatchDescription);
        }
        return matches;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("elements selected by ").appendValue(getCss())
                .appendText(" matching ").appendDescriptionOf(matcher);
    }
}
