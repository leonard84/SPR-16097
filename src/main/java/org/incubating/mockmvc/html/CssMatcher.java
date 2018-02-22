package org.incubating.mockmvc.html;

import org.hamcrest.DiagnosingMatcher;

public abstract class CssMatcher<T> extends DiagnosingMatcher<T> implements DocumentMatcher {
    private final String css;

    public CssMatcher(String css) {
        this.css = css;
    }

    String getCss() {
        return css;
    }
}
