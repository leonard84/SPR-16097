package org.incubating.mockmvc.html;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

public class ElementMatchers {

    public static Matcher<Element> hasValue(String value) {
        return hasValue(equalTo(value));
    }

    public static Matcher<Element> hasValue(Matcher<? super String> valueMatcher) {
        return valueMatcher(valueMatcher);
    }

    public static Matcher<Element> hasAttribute(String attribute) {
        return attributesMatcher(new PredicateMatcher<>(
                item -> item.hasKey(attribute),
                description -> description.appendText("has attribute ").appendValue(attribute).appendText(" defined"),
                (item, mismatch)->mismatch.appendText("attribute was not preset")));
    }

    public static Matcher<Element> hasAttribute(String attribute, String value) {
        return hasAttribute(attribute, equalTo(value));
    }

    public static Matcher<Element> hasAttribute(String attribute, Matcher<? super String> valueMatcher) {
        return attributesMatcher(new PredicateMatcher<>(
                item -> valueMatcher.matches(item.get(attribute)),
                description -> description.appendText("has attribute ").appendValue(attribute)
                        .appendText(" with value ").appendDescriptionOf(valueMatcher),
                (item, mismatch)->mismatch.appendText("attribute value was ").appendValue(item.get(attribute))));
    }

    public static Matcher<Element> hasCssClass(String cssClass) {
        return hasCss(hasItem(cssClass));
    }

    public static Matcher<Element> hasCss(Matcher<? super Set<String>> matcher) {
        return cssClassMatcher(matcher);
    }

    private static Matcher<Element> attributesMatcher(Matcher<? super Attributes> matcher) {
        return new ElementPropertyMatcher<>(Element::attributes, matcher,
                description -> description.appendText("element attributes ").appendDescriptionOf(matcher));
    }


    private static Matcher<Element> cssClassMatcher(Matcher<? super Set<String>> matcher) {
        return new ElementPropertyMatcher<>(Element::classNames, matcher,
                description -> description.appendText("element css classes ").appendDescriptionOf(matcher));
    }

    private static Matcher<Element> valueMatcher(Matcher<? super String> matcher) {
        return new ElementPropertyMatcher<>(Element::val, matcher,
                description -> description.appendText("element value ").appendDescriptionOf(matcher));
    }

    private static class ElementPropertyMatcher<T> extends PropertyMatcher<Element, T> {
        ElementPropertyMatcher(Function<Element, T> extractor, Matcher<T> matcher, Consumer<Description> description) {
            super(description, extractor, matcher);
        }
    }
}
