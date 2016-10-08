import java.io.IOException;
import java.net.URL;

class Page implements Comparable<Page> {

    private URL url;//stores page's URL
    private Double pageRank; // store page rank values
    private int searchLevel;//Store which level page was found
   // private String title, description;

    Page(URL url) {
        this(url, 0);
    }

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


    public String toString() {
        try {
            return ("<html>&#8195;<a style=\"font-size: large;\" href=\""+url.toString()+"\">"+ SpiderLeg.getTitle(url.toString()) +"</a>" +
                    "<p style=\"color: green; padding: 0 0 0 20px;\">" + url.toString() + "</p>" +
                    "<p style=\"color: gray; padding: 0 0 0 20px;\">" + SpiderLeg.getDescriptionFromPage(url.toString()) + "</p></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int compareTo(Page o) {
        return pageRank.compareTo(o.getPageRank());
    }
}