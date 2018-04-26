package org.incubating.mockmvc.html;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jsoup.nodes.Element;

@SuppressWarnings("rawtypes")
public abstract class AbstractElementAssertions<T extends AbstractElementAssertions> implements DocumentMatcher {

    private List<Matcher<? super Element>> matchers = new ArrayList<>();

    private final String css;

    protected AbstractElementAssertions(String css) {
        this.css = css;
    }

    protected List<Matcher<? super Element>> getMatchers() {
        return matchers;
    }

    protected String getCss() {
        return css;
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }


    public T hasValue(String value) {
        matchers.add(ElementMatchers.hasValue(value));
        return self();
    }

    public T hasValue(Matcher<? super String> valueMatcher) {
        matchers.add(ElementMatchers.hasValue(valueMatcher));
        return self();
    }

    public T hasAttribute(String attribute, String value) {
        matchers.add(ElementMatchers.hasAttribute(attribute, equalTo(value)));
        return self();
    }

    public T hasAttribute(String attribute, Matcher<? super String> valueMatcher) {
        matchers.add(ElementMatchers.hasAttribute(attribute, valueMatcher));
        return self();
    }

    public T hasCssClass(String cssClass) {
        matchers.add(ElementMatchers.hasCssClass(cssClass));
        return self();
    }

    public T hasCss(Matcher<? super Set<String>> matcher) {
        matchers.add(ElementMatchers.hasCss(matcher));
        return self();
    }

    public T hasText(String text) {
        matchers.add(ElementMatchers.hasText(equalTo(text)));
        return self();
    }

    public T hasText(Matcher<? super String> textMatcher) {
        matchers.add(ElementMatchers.hasText(textMatcher));
        return self();
    }

    public T hasOwnText(String text) {
        matchers.add(ElementMatchers.hasOwnText(equalTo(text)));
        return self();
    }

    public T hasOwnText(Matcher<? super String> textMatcher) {
        matchers.add(ElementMatchers.hasOwnText(textMatcher));
        return self();
    }

    public T hasData(String data) {
        matchers.add(ElementMatchers.hasData(equalTo(data)));
        return self();
    }

    public T hasData(Matcher<? super String> dataMatcher) {
        matchers.add(ElementMatchers.hasData(dataMatcher));
        return self();
    }

    public T predicate(Predicate<Element> predicate,
            Consumer<Description> description,
            BiConsumer<Element, Description> mismatchDescription) {
        matchers.add(ElementMatchers.predicate(predicate, description, mismatchDescription));
        return self();
    }

    public T matches(Matcher<? super Element> matcher) {
        matchers.add(matcher);
        return self();
    }
}
