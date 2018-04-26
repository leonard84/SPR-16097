package org.incubating.mockmvc.html;

import static org.hamcrest.MatcherAssert.assertThat;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EachElementAssertions extends AbstractElementAssertions<EachElementAssertions> {
    private final DocumentMatcher parent;

    EachElementAssertions(String css) {
        super(css);
        parent = null;
    }

    public EachElementAssertions(String css, DocumentMatcher parent) {
        super(css);
        this.parent = parent;
    }

    @Override
    public void match(Document document) {
        if (parent != null) {
            parent.match(document);
        }
        Elements select = document.select(getCss());
        assertThat(select, new EachElementMatcher(new AllOfMatcher<>(description ->
                description.appendText("elements selected by ").appendValue(getCss())
                        .appendText(" matching "), getMatchers())));
    }
}
