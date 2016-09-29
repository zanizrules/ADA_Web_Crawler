import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Everybody's
 */
public class SpiderLegStatic {

    static Elements getMeta(String url) throws IOException{
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.getElementsByTag("meta");
    }
    
    public static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.title();
    }

    public static String getDescriptionFromPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        Elements descriptionElement = doc.select("meta[name=description]");
        String description = "";
        for (Element link : descriptionElement) {
            description = link.attr("content");

        }
        return description;
    }

    public static String getKeywordsfromPage(String url) throws IOException {

        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();

        Elements keyElements = doc.select("meta[name=keywords]");
        String keyWords = "";
        for (Element link : keyElements) {
            keyWords += link.attr("content");

        }
        return keyWords;

    }

    static ArrayList<URL> getHyperlink(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .timeout(5000).get();

        Elements links = doc.select("a[href]"); // a with href
        ArrayList<URL> absoluteLinks = new ArrayList<>();

        for (Element link : links) {
            String absHref = link.attr("abs:href");
            absoluteLinks.add(new URL(absHref));
        }
        return absoluteLinks;
    }

    public static Elements getImages(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.select("img");
    }

    private static void print(List<URL> list){
        list.forEach(System.out::println);
    }
    
    public static void main(String[] args) throws IOException {

       // String aut = "http://aut.ac.nz";
        //String jsoup = "https://jsoup.org";
        String test ="http://french.stackexchange.com/questions/22094/genders-in-french-when-not-sure-always-go-with-masculine";
        //URL url = new URL(aut);
        SpiderLegStatic.print(SpiderLegStatic.getHyperlink(test));
        
    }

}

