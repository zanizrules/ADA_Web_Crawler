import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The SpiderLeg class is responsible for getting information from a HTML file using a given URL.
 * Such information includes Meta data, Title, Description, KeyWords, Hyperlinks, and ImageLinks.
 * Printing methods are used for testing purposes only.
 * There is a main method in this class which tests all methods, it was created to satisfy Q1.
 */
public class SpiderLeg {
    private final static int timeoutLimit = 5000;

    private final static String connection = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";

    /**
     * Return a List of Elements (Elements Type provided by JSoup) found in the metaData parameter of the HTML file.
     */
    static Elements getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(connection).get();
        return doc.getElementsByTag("meta");
    }

    /**
     * Returns a string found in the Title parameter of the HTML file for the given URL.
     */
    static String getTitle(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(connection).get();
        return doc.title();
    }

    /**
     * Returns a string found in the Description parameter of the HTML file for the given URL.
     */
    static String getDescriptionFromPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(connection).get();
        Elements descriptionElement = doc.select("meta[name=description]");
        String description = "";
        for (Element link : descriptionElement) {
            description = link.attr("content");
        }
        return description;
    }

    /**
     * Return a string found in the key words parameter of the HTML file for the given URL.
     */
    private static String getKeywordsFromPage(String url) throws IOException {

        Document doc = Jsoup.connect(url).
                userAgent(connection).get();
        Elements keyElements = doc.select("meta[name=keywords]");
        String keyWords = "";
        for (Element link : keyElements) {
            keyWords += link.attr("content");
        }
        return keyWords;
    }

    /**
     * Returns a List of absoluteLinks (Elements Type provided by JSoup class) found in the a[href] parameter of the
     * HTML file for the given URL.
     */
    static ArrayList<URL> getHyperlink(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(connection).timeout(timeoutLimit).get();
        Elements links = doc.select("a[href]"); // a with href
        ArrayList<URL> absoluteLinks = new ArrayList<>();

        for (Element link : links) {
            String absHref = link.attr("abs:href");
            absoluteLinks.add(new URL(absHref));
        }
        return absoluteLinks;
    }

    /**
     * Return a List of the images' Links (Elements Type provided by JSoup class) found in the img parameter of the
     * HTML file for the given URL.
     */
    private static Elements getImages(String url) throws IOException {
        Document doc = Jsoup.connect(url).
                userAgent(connection).get();
        return doc.select("img");
    }

    // Print a list
    private static void printList(List list) {
        list.forEach(System.out::println);
    }

    // Print a string
    private static void printString(String str) {
        System.out.println(str);
    }

    // Print a string of colour red
    private static void printClass(String str) {
        System.out.println("\u001B[31m" + str + "\u001B[30m");
    }

    // Print all information of a URL acquired from the get methods above
    private static void printAll(String url) throws IOException {
        Elements meta = getMeta(url);
        String title = getTitle(url);
        String description = getDescriptionFromPage(url);
        String keyWords = getKeywordsFromPage(url);
        List<URL> Hyperlinks = getHyperlink(url);
        List<Element> Images = getImages(url);

        printClass("Printing info from: " + url);
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

    // Example for question 1, prints information retrieved by the methods above
    public static void main(String[] args) {
        String jSoup = "https://jsoup.org";
        String fb = "https://www.facebook.com/";
        try {
            printAll(jSoup);
            printAll(fb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}