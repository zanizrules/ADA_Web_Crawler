import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Attr;

/**
 *
 * @author Everybody's
 */
public class SpiderLegStatic {

    public static Elements getMeta(String url) throws IOException{
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        Elements meta = doc.getElementsByTag("meta");
        return meta;
    }
    
    public static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        String title;
        title = doc.title();
        return title;
    }

    public static String getDescriptionFromPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        Elements descriptionElement = doc.select("meta[name=description]");
        String description = "";
        Iterator<Element> itr = descriptionElement.iterator();
        while (itr.hasNext()) {
            Element link = itr.next();
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
        Iterator<Element> itr = keyElements.iterator();
        while (itr.hasNext()) {
            Element link = itr.next();
            keyWords += link.attr("content");

        }
        return keyWords;

    }

    public static ArrayList<URL> getHyperlink(String url) throws MalformedURLException, IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .timeout(10*1000).get();

        Elements links = doc.select("a[href]"); // a with href
        ArrayList<URL> absoluteLinks = new ArrayList();

        Iterator<Element> itr = links.iterator();
        while (itr.hasNext()) {
            Element link = itr.next();
            String absHref = link.attr("abs:href");
            absoluteLinks.add(new URL(absHref));
        }
        return absoluteLinks;
    }

    public static Elements getImages(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        Elements pngs = doc.select("img");
        return pngs;
    }

    public static void print(List list){
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
    
    public static void main(String[] args) throws IOException {

        String aut = "http://aut.ac.nz";
        String jsoup = "https://jsoup.org";
        String test ="http://french.stackexchange.com/questions/22094/genders-in-french-when-not-sure-always-go-with-masculine";
        URL url = new URL(aut);
        SpiderLegStatic.print(SpiderLegStatic.getHyperlink(test));
        
    }

}

