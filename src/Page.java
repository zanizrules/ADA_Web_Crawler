import java.io.IOException;
import java.net.URL;
import java.util.Comparator;

/**
 * The Page Class stores the URL, page rank value, and search level of a web-page.
 * Search level relates to where it was found when web crawler was performed.
 * Page implements Comparable to compare Page Rank value.
 */
class Page {
    private URL url; // Stores page's URL
    private Double pageRank; // Store page rank values
    private int searchLevel; // Store which level page was found

    Page(URL url, int searchLevel) {
        this.url = url;
        this.searchLevel = searchLevel;
        pageRank = 0d;
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

    /**
        toString method which returns a representation of a web-page to be shown in the GUI.
        String is edited with html to get a visually appealing output.
     */
    public String toString() {
        try {
            return ("<html>&#8195;<a style=\"font-size: large;\" href=\"" + url.toString() + "\">" + SpiderLeg.getTitle(url.toString()) + "</a>" +
                    "<p style=\"color: green; padding: 0 0 0 20px;\">" + url.toString() + "</p>" +
                    "<p style=\"color: gray; padding: 0 0 0 20px;\">" + SpiderLeg.getDescriptionFromPage(url.toString()) + "</p></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates and return a comparator for Page class ordering.
     * @return Comparator<Page>
     */
    static Comparator<Page> getComparator() {
        return (o1, o2) -> {
            if (o1.getPageRank() > o2.getPageRank()) {
                return -1;
            } else if (o1.getPageRank() < o2.getPageRank()) {
                return 1;
            } else return 0;
        };
    }
}