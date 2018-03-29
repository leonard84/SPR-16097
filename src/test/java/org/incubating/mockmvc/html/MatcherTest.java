package org.incubating.mockmvc.html;

import static org.assertj.core.api.Assertions.assertThat;
import static org.incubating.mockmvc.html.DocumentMatchers.hasTitle;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StreamUtils;

public class MatcherTest {

    protected static final String FORM_INPUT_NAME_CSRF = "form input[name=_csrf]";

    private static String html;

    @BeforeClass
    public static void setupClass() throws IOException {
        html = StreamUtils.copyToString(MatcherTest.class.getResourceAsStream("/templates/index.html"),
                Charset.forName("UTF-8"));
    }

    @Test
    public void single_element_matcher_does_fail_when_more_than_one_elements_are_found() {
        String error = singleElement("input", ElementMatchers.hasValue("irrelevant"));
        assertThat(error).isEqualTo("Single or null Element expected for \"input\", but found <4>:\n"
                + "html > body > div.content > form > input\n"
                + "#exampleInputName\n"
                + "#exampleInputEmail\n"
                + "#exampleInputPassword");
    }

    @Test
    public void single_element_value_string_matcher() {
        String error = singleElement(FORM_INPUT_NAME_CSRF, ElementMatchers.hasValue("5ee91155"));
        assertThat(error).isEqualTo("\n"
                + "Expected: a single element selected by \"form input[name=_csrf]\" matching element value \"5ee91155\"\n"
                + "     but: was \"5ee91155-9809-4630-81a5-47d478eccd11\"");
    }

    @Test
    public void single_element_value_matcher() {
        String error = singleElement(FORM_INPUT_NAME_CSRF, ElementMatchers.hasValue(CoreMatchers.endsWith("5ee91155")));
        assertThat(error).isEqualTo("\n"
                + "Expected: a single element selected by \"form input[name=_csrf]\" matching element value a string ending "
                + "with \"5ee91155\"\n"
                + "     but: was \"5ee91155-9809-4630-81a5-47d478eccd11\"");
    }

    @Test
    public void single_element_attribute_matcher() {
        String error = singleElement(FORM_INPUT_NAME_CSRF, ElementMatchers.hasAttribute("type", "text"));
        assertThat(error).isEqualTo("\n"
                + "Expected: a single element selected by \"form input[name=_csrf]\" matching element attributes has attribute "
                + "\"type\" with value \"text\"\n"
                + "     but: attribute value was \"hidden\"");
    }

    @Test
    public void single_element_text_matcher() {
        String error = singleElement("h1", ElementMatchers.hasText("Demo"));
        assertThat(error).isEqualTo("\n"
                + "Expected: a single element selected by \"h1\" matching text \"Demo\"\n"
                + "     but: was \"This is a Demo site\"");
    }

    @Test
    public void single_element_data_matcher() {
        String error = singleElement("script", ElementMatchers.hasData(CoreMatchers.containsString("csrf_token")));
        assertThat(error).isEqualTo("\n"
                + "Expected: a single element selected by \"script\" matching data a string containing \"csrf_token\"\n"
                + "     but: was \"\n"
                + "        var xsrf_token = \"5ee91155-9809-4630-81a5-47d478eccd11\";\n"
                + "    \"");
    }

    @Test
    public void document_title_matcher() {
        String error = document(hasTitle("foobar"));
        assertThat(error).isEqualTo("\n"
                + "Expected: document has title \"foobar\"\n"
                + "     but: was \"Demo\"");
    }

    private String singleElement(String cssSelector, Matcher<Element> elementMatcher) {
        DocumentMatcher matcher = new SingleElementMatcher(cssSelector, elementMatcher);
        return assertMessage(matcher);
    }

    private String document(Matcher<Document> matcher) {
        return assertMessage(document -> MatcherAssert.assertThat(document, matcher));
    }

    private String assertMessage(DocumentMatcher matcher) {
        try {
            new JsoupMatcher(Collections.singletonList(matcher)).match(html);
        } catch (AssertionError e) {
            return e.getMessage();
        }
        Assert.fail("Matcher did not fail as expected");
        return null;
    }
}
