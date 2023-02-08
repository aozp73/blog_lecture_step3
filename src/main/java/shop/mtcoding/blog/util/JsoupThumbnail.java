package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupThumbnail {
    public static String thumbnail(String html, String tag, String attribute) {
        Document doc = Jsoup.parse(html);
        Elements els = doc.select(tag);
        String img = "";

        if (els.size() == 0) {
            img = "/images/dora.png";
        } else {

            Element el = els.get(0);
            img = el.attr(attribute);
        }
        return img;
    }
}
