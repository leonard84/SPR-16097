package org.incubating.mockmvc.html;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class EachElementMatcher extends TypeSafeDiagnosingMatcher<Elements> {

    private final Matcher<? super Element> matcher;

    public EachElementMatcher(Matcher<? super Element> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Elements item, Description mismatchDescription) {
        boolean result = true;
        for (Element element : item) {
            if(!matcher.matches(element)) {
                if (!result) {
                    mismatchDescription.appendText("\n");
                }
                mismatchDescription.appendText("element ").appendValue(element.cssSelector()).appendText(" did not match ");
                matcher.describeMismatch(element, mismatchDescription);
                result = false;
            }
        }
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(matcher);
    }
}
