package org.incubating.spring.mockmvchtmlmatcher;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.incubating.mockmvc.html.DocumentAssertions.elements;
import static org.incubating.mockmvc.html.DocumentMatchers.hasTitle;
import static org.incubating.mockmvc.html.DocumentAssertions.anElement;
import static org.incubating.mockmvc.html.DocumentAssertions.eachElement;
import static org.incubating.mockmvc.html.ElementMatchers.hasCssClass;
import static org.incubating.mockmvc.html.ElementMatchers.predicate;
import static org.incubating.mockmvc.html.HtmlContentMatcher.html;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MatcherIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void form_contains_a_csrf_token_hidden_field() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(content().string(containsString("_csrf")));
    }

    @Test
    public void form_contains_a_csrf_token_hidden_field_jsoup() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html()
                        .anElement("form input[name=_csrf]", notNullValue(Element.class))
                        .document(anElement("form input[name=_csrf]").exists()));
    }

    @Test
    public void form_has_a_submit_button() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html()
                        .elements("form input[type=submit], form button[type=submit]", hasSize(1))
                        .document(elements("form input[type=submit], form button[type=submit]").hasCount(1))
                );
    }

    @Test
    public void form_every_non_hidden_input_has_the_correct_class() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html()
                        .eachElement("form input:not([type=hidden])", hasCssClass("form-control"))
                        .document(eachElement("form input:not([type=hidden])").hasCssClass("form-control")));
    }


    @Test
    public void form_every_non_hidden_input_has_the_correct_class_alternative() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html()
                        .document(elements("form input:not([type=hidden])").hasCount(3).each().hasCssClass("form-control")));
    }

    @Test
    public void form_every_non_hidden_input_has_corresponding_label() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html().eachElement("form input:not([type=hidden])", predicate(
                        element -> StringUtils.hasText(element.id())
                                && element.parent().selectFirst("label[for=" + element.id() + "]") != null,
                        description -> description.appendText("an input with corresponding label"),
                        (element, description) -> description.appendText("element ").appendValue(element.cssSelector())
                                .appendText(" does not have a corresponding label.")
                )));
    }

    @Test
    public void document_title() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html().document(hasTitle("Demo")));
    }

    @Test
    public void multiple_assertions_combined() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html()
                        .document(hasTitle("Demo"))
                        .anElement("form input[name=_csrf]", notNullValue(Element.class))
                        .elements("form input[type=submit], form button[type=submit]", hasSize(1))
                        .eachElement("form input:not([type=hidden])", hasCssClass("form-control"))
                );
    }
}
