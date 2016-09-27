/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

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
 *
 * @author Everybody's
 */
public class MySpiderLeg {

    private Document doc;
    private URL url;
    private int levelSearchFound;
    private String title;
    private String description;
    private String keyWords;
    private List meta;
    private List<URL> Hyperlinks;
    private List Images;

    public MySpiderLeg(URL url, int levelSearchFound) throws IOException {

        doc = Jsoup.connect(url.toString()).
                userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                .timeout(10 * 1000).get();
        this.url = url;
        this.levelSearchFound = levelSearchFound;
        this.title = this.getTitle(url.toString());
        this.description = this.getDescriptionFromPage(url.toString());
        this.keyWords = this.getKeywordsfromPage(url.toString());
        this.meta = this.getMeta(url.toString());
        this.Hyperlinks = this.getHyperlink(url.toString());
        this.Images = this.getImages(url.toString());

    }

    private Elements getMeta(String url) throws IOException {
        Elements meta = doc.getElementsByTag("meta");
        return meta;
    }

    private String getTitle(String url) throws IOException {
        String title;
        title = doc.title();
        return title;
    }

    private String getDescriptionFromPage(String url) throws IOException {
        Elements descriptionElement = doc.select("meta[name=description]");
        String description = "";
        Iterator<Element> itr = descriptionElement.iterator();
        while (itr.hasNext()) {
            Element link = itr.next();
            description = link.attr("content");

        }
        return description;
    }

    private String getKeywordsfromPage(String url) throws IOException {

        Elements keyElements = doc.select("meta[name=keywords]");
        String keyWords = "";
        Iterator<Element> itr = keyElements.iterator();
        while (itr.hasNext()) {
            Element link = itr.next();
            keyWords += link.attr("content");

        }
        return keyWords;

    }

    private ArrayList<URL> getHyperlink(String url) throws MalformedURLException, IOException {

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

    private Elements getImages(String url) throws IOException {
        Elements pngs = doc.select("img");
        return pngs;
    }

    public static void print(List list) {
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public boolean findKeyWord(String keyword) {

        if (title.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        } else if (description.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        } else if (keyWords.toLowerCase().contains(keyword.toLowerCase())) {
            return true;
        }
        ArrayList searchAll = new ArrayList();
        searchAll.addAll(meta);
        searchAll.addAll(Hyperlinks);
        searchAll.addAll(Images);
        Iterator<Object> itr = searchAll.iterator();     
        while (itr.hasNext()) {
            String link = itr.next().toString();
//            System.out.println(link);
            if(link.toLowerCase().contains(keyword.toLowerCase())){
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
        String jsoup = "https://jsoup.org";
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
