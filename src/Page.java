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
        String result = "Test Website Title\n" + url.toString()
                + "\nThis is a description used for testing purposes. " +
                "This will show a description relating to the website";
        return result;
    }

    public JLabel getLabel() { // 70, 40, 15
        JLabel result = new JLabel("<html><a href=\"url\">Test Website Title</a> <br>" +
                "&#8195;" + url.toString() + "<br>" +
                "&#8194;" +"Test Description..." + "</html>");


//        result.setFont(new Font(result.getName(), Font.BOLD, 20));
//        result.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 30));
//
//        JLabel link = new JLabel(url.toString());
//        link.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
//
//        JLabel description = new JLabel("Test Description...");
//
//        link.add(description);
//        result.add(link);
//
//        double height = link.getPreferredSize().getHeight()
//                + description.getPreferredSize().getHeight();
//
//        link.setPreferredSize(new Dimension(SearchResultsGui.PANEL_WIDTH, (int) height));
//        height += result.getPreferredSize().getHeight();
//        result.setPreferredSize(new Dimension(SearchResultsGui.PANEL_WIDTH, (int) height));
        return result;
    }

    @Override
    public int compareTo(Page o) {
        return pageRank.compareTo(o.getPageRank());
    }
}