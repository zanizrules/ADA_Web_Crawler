import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *   SpiderLeg class is responsible to get a information fof HTML files from  given URLs.
 *   Such information are Meta date; Title; Description; KeyWords; Hyperlinks; ImageLinks
 *   Also have private printing methods for testing purpose only
 *   There is a main method which will test all methods, it was created to satisfy question one printing purpose only.
 */
public class SpiderLeg {

    /**
     * Return a List of Elements ( Type Elements provided by Jsoup class) found in the metaData parameter
     * of the HTML filefrom  given URL.
     * @param  url String
     * @return Elements
     * @throws IOException Exception
     */
    static Elements getMeta(String url) throws IOException{
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.getElementsByTag("meta");
    }

    /**
     * Return a string found in the Title parameter
     * of the HTML filefrom  given URL.
     * @param url String
     * @return String
     * @throws IOException Exception
     */
    public static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.title();
    }

    /**
     ** Return a string found in the Description parameter of the HTML file from  given URL.
     * @param url String
     * @return String
     * @throws IOException Exception
     */
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

    /**
     * Return a string found in the key words parameter of the HTML file from  given URL.
     * @param url String
     * @return String
     * @throws IOException Exception
     */
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

    /**
     * Return a List of absoluteLinks ( Type Elements provided by Jsoup class) found in the a[href] parameter
     * of the HTML filefrom  given URL.
     * @param url String
     * @return ArrayList<URL>
     * @throws IOException Exception
     */
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

    /**
     * Return a List of the images' Links ( Type Elements provided by Jsoup class) found in the img parameter
     * of the HTML filefrom  given URL.
     * @param url String
     * @return Elements
     * @throws IOException Exception
     */
    public static Elements getImages(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
        return doc.select("img");
    }

    //Print a list
    private static void printList(List list){
        list.forEach(System.out::println);
    }
    //print a string
    private static void printString(String str){
        System.out.println(str);
    }
    //Print a string of colour red
    private static void printClass(String str){
        System.out.println("\u001B[31m"+str+"\u001B[30m");
    }
    //Print all information of a URL aquired from the get mothods above
    private static void printAll(String url)throws IOException{
        Elements meta = getMeta(url);
        String title = getTitle(url);
        String description = getDescriptionFromPage(url);
        String keyWords = getKeywordsfromPage(url);
        List<URL> Hyperlinks = getHyperlink(url);
        List<Element> Images = getImages(url);

        printClass("Printing info from: "+url);
        System.out.println();
        printClass("Meta data: ");
        printList(meta);
        System.out.println();

        printClass("Title:");
        printString(title);
        System.out.println();

        printClass("Description: ");
        printString(description);
        System.out.println();

        printClass("Key Words: ");
        printString(keyWords);
        System.out.println();

        printClass("Hyperlinks");
        printList(Hyperlinks);
        System.out.println();

        printClass("Images links: ");
        printList(Images);
        System.out.println();

    }

    //Example for question 1, prints information retrived by the methods above
    public static void main(String[] args)  {

        String aut = "http://url.ac.nz";//String
        String jsoup = "https://jsoup.org";
        String fb = "https://www.facebook.com/";
        try {
            printAll(jsoup);
            printAll(fb);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}