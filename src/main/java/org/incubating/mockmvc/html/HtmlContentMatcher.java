package org.incubating.mockmvc.html;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

public class HtmlContentMatcher implements ResultMatcher {

    public static HtmlContentMatcher html() {
        return new HtmlContentMatcher();
    }

    private List<DocumentMatcher> documentMatchers = new ArrayList<>();

    @Override
    public void match(MvcResult mvcResult) throws Exception {
        new JsoupMatcher(documentMatchers).match(mvcResult.getResponse().getContentAsString());
    }

    public HtmlContentMatcher document(Matcher<Document> documentMatcher) {
        documentMatchers.add(document -> assertThat(document, documentMatcher));
        return this;
    }

    public HtmlContentMatcher document(DocumentMatcher documentMatcher) {
        documentMatchers.add(documentMatcher);
        return this;
    }

    public HtmlContentMatcher anElement(String cssSelector, Matcher<? super Element> elementMatcher) {
        documentMatchers.add(new SingleElementMatcher(cssSelector, elementMatcher));
        return this;
    }

    public HtmlContentMatcher eachElement(String cssSelector, Matcher<? super Element> eachElementMatcher) {
        documentMatchers.add(new ElementsMatcher(cssSelector, new EachElementMatcher(eachElementMatcher)));
        return this;
    }

    public HtmlContentMatcher elements(String cssSelector, Matcher<? super Elements> elementsMatcher) {
        documentMatchers.add(new ElementsMatcher(cssSelector, elementsMatcher));
        return this;
    }
}
