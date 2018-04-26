package org.incubating.mockmvc.html;

public class DocumentAssertions {
    private DocumentAssertions(){}

    public static SingleElementAssertions anElement(String css) {
        return new SingleElementAssertions(css);
    }

    public static EachElementAssertions eachElement(String css) {
        return new EachElementAssertions(css);
    }

    public static ElementsAssertions elements(String css) {
        return new ElementsAssertions(css);
    }
}
