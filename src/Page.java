import java.net.URL;

/**
 * Created by Everybody's on 27/09/2016.
 */
public class Page {

    private URL url;
    private Double pageRank;
    private int searchLevel;

    public Page(URL url) {
        this.url = url;
    }

    public Page(URL url, int searchLevel) {
        this.url = url;
        this.searchLevel = searchLevel;
    }

    public URL getUrl() {
        return url;
    }

    public Double getPageRank() {
        return pageRank;
    }

    public int getSearchLevel() {
        return searchLevel;
    }

    public void setPageRank(Double pageRank) {
        this.pageRank = pageRank;
    }
}
