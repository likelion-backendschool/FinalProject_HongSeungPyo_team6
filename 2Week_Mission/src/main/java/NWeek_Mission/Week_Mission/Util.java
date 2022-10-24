package NWeek_Mission.Week_Mission;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public static String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
    public static class str {
        public static boolean empty(String str) {
            if (str == null) return true;
            if (str.trim().length() == 0) return true;

            return false;
        }
    }

}
