package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class HtmlParseTest {

    @Test
    public void jsoup_test1() throws Exception {
        System.out.println("=====================================");
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        // System.out.println(doc.title());
        System.out.println("=====================================");
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(headline.attr("title"));
            System.out.println(headline.absUrl("href"));
        }
    }

    @Test
    public void jsoup_test2() throws Exception {
        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
        Document doc = Jsoup.parse(html);
        System.out.println(doc);
        Elements els = doc.select("img");
        // System.out.println(els);

        // // 아래를 update, insert 할 때 각 1번 해주면 됨
        // if (els.size() == 0) {
        // // 임시사진 제공
        // // DB thumnail -> /images/profile.jfif
        // } else {
        // Element el = els.get(0);
        // String img = el.attr("src");
        // // 아래 값을 DB에 넣는 것임
        // System.out.println(img);
        // // 디비 thumbnail -> img
        // }
    }

    @Test
    public void parse_test2() {
        // given

        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
        String result = null;
        try {
            result = parse(html, "img", "src");
        } catch (Exception e) {
            System.out.println("파싱 실패 : " + e.getMessage());
        }
        System.out.println(result);

    }

    public String parse(String html, String tag, String attribute) {
        // when
        String result = "";
        // 23
        int begin = html.indexOf("img");

        // img src="data:image/png;base64,iVBORw0KG"></p>
        String s1 = html.substring(begin);

        int b2 = s1.indexOf("src");
        // src="data:image/png;base64,iVBORw0KG"></p>
        String s2 = s1.substring(b2);

        int b3 = s2.indexOf("\"");
        int b4 = s2.lastIndexOf("\"");

        // data:image/png;base64,iVBORw0KG
        // 찾고 모듈화 (메서드화)
        result = s2.substring(b3 + 1, b4);
        return result;

        // then
    }
}
