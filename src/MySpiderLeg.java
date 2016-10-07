
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * TODO: See if this can be removed from the working GitHub code.
 * @author Everybody's
 */
public class MySpiderLeg {

    private Document doc;
    private URL url;
    private int levelSearchFound;
    private String title;
    private String description;
    private String keyWords;
    private List<Element> meta;
    private List<URL> Hyperlinks;
    private List<Element> Images;

    private MySpiderLeg(URL url, int levelSearchFound) throws IOException {

        doc = Jsoup.connect(url.toString()).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .timeout(10 * 1000).get();
        this.url = url;
        this.levelSearchFound = levelSearchFound;
        this.title = this.getTitle();
        this.description = this.getDescriptionFromPage();
        this.keyWords = this.getKeywordsfromPage();
        this.meta = this.getMeta();
        this.Hyperlinks = this.getHyperlink();
        this.Images = this.getImages();

    }

    private Elements getMeta() throws IOException {
        return doc.getElementsByTag("meta");
    }

    private String getTitle() throws IOException {
        return doc.title();
    }

    private String getDescriptionFromPage() throws IOException {
        Elements descriptionElement = doc.select("meta[name=description]");
        String description = "";
        for (Element link : descriptionElement) {
            description = link.attr("content");

        }
        return description;
    }

    private String getKeywordsfromPage() throws IOException {

        Elements keyElements = doc.select("meta[name=keywords]");
        String keyWords = "";
        for (Element link : keyElements) {
            keyWords += link.attr("content");
        }
        return keyWords;

    }

    private ArrayList<URL> getHyperlink() throws IOException {

        Elements links = doc.select("a[href]"); // a with href
        ArrayList<URL> absoluteLinks = new ArrayList<>();

        for (Element link : links) {
            String absHref = link.attr("abs:href");
            absoluteLinks.add(new URL(absHref));
        }
        return absoluteLinks;
    }

    private Elements getImages() throws IOException {
        return doc.select("img");
    }

    public static void print(List list) {
        list.forEach(System.out::println);
    }

    private boolean findKeyWord(String keyword) {

        if (title.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        } else if (description.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        } else if (keyWords.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        }
        ArrayList<Object> searchAll = new ArrayList<>();
        searchAll.addAll(meta);
        searchAll.addAll(Hyperlinks);
        searchAll.addAll(Images);
        for (Object aSearchAll : searchAll) {
            String link = aSearchAll.toString();
//            System.out.println(link);
            if (link.toLowerCase().contains(keyword.toLowerCase())) {
//                System.out.println("KeyWord Found");
                return true;
            }

        }
    
        return false;
    }

    public URL getUrl() {
        return url;
    }

    public int getLevelSearchFound() {
        return levelSearchFound;
    }

    public List<URL> getHyperlinks() {
        return Hyperlinks;
    }
    
    public static void main(String[] args) throws IOException {

        String aut = "http://aut.ac.nz";
        //String jsoup = "https://jsoup.org";
        URL url = new URL(aut);
        MySpiderLeg mine = new MySpiderLeg(url,0);
        System.out.println(mine.findKeyWord("aut"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MySpiderLeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(mine.findKeyWord("Vini"));
        

    }
}
