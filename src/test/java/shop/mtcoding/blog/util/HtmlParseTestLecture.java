package shop.mtcoding.blog.util;

import org.junit.jupiter.api.Test;

public class HtmlParseTestLecture {

    @Test
    public void parse_test1() {
        // given

        String html = "<p>1</p><p><br></p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
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
