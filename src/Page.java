import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author Shane Birdsall, Vinicius Ferreira.
 *         Created on 27/09/2016.
 */
class Page implements Comparable<Page> {

    private URL url;
    private Double pageRank;
    private int searchLevel;
   // private String title, description;

    Page(URL url) {
        this(url, 0);
    }

    Page(URL url, int searchLevel) {
        this.url = url;
        this.searchLevel = searchLevel;
    }

    URL getUrl() {
        return url;
    }

    Double getPageRank() {
        return pageRank;
    }

    int getSearchLevel() {
        return searchLevel;
    }

    void setPageRank(Double pageRank) {
        this.pageRank = pageRank;
    }

    public String toString() {
        return url.toString();
    }

    public JLabel getLabel() { // 70, 40, 15
        JLabel result = new JLabel("<html>&#8195;<a style=\"font-size: large;\" href=\""+url.toString()+"\">Test Title</a>" +
                "<p style=\"color: green; padding: 0 0 0 20px;\">" + url.toString() + "</p>" +
                "<p style=\"color: gray; padding: 0 0 0 20px;\">" + "Test Description..." + "</p></html>");
        return result;
    }

    @Override
    public int compareTo(Page o) {
        return pageRank.compareTo(o.getPageRank());
    }
}