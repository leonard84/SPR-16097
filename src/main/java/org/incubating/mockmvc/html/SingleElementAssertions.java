package org.incubating.mockmvc.html;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SingleElementAssertions extends AbstractElementAssertions<SingleElementAssertions> {
    SingleElementAssertions(String css) {
        super(css);
    }

    @Override
    public void match(Document document) {
        Elements select = document.select(getCss());
        if (select.size() > 1) {
            throw new AssertionError(
                    String.format("Single or null Element expected for \"%s\", but found <%d>:\n%s",
                            getCss(),
                            select.size(),
                            select.stream().map(Element::cssSelector).collect(Collectors.joining("\n"))));
        }
//        getMatchers().forEach(m -> assertThat(select.first(), m));
        assertThat(select.first(), new AllOfMatcher<>(description ->
                description.appendText("a single element selected by ")
                        .appendValue(getCss()).appendText(" matching "),
                getMatchers()));
    }

    public SingleElementAssertions exists() {
        getMatchers().add(CoreMatchers.notNullValue(Element.class));
        return this;
    }
}
