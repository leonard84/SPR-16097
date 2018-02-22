package org.incubating.spring.mockmvchtmlmatcher;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.incubating.mockmvc.html.ElementMatchers.hasCssClass;
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
                .andExpect(html().anElement( "form input[name=_csrf]", notNullValue(Element.class)));
    }

    @Test
    public void form_has_a_submit_button() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html().elements("form input[type=submit], form button[type=submit]", hasSize(1)));
    }


    @Test
    public void form_every_input_has_the_correct_class() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(html().eachElement("form input", hasCssClass("form-control")));
    }
}
