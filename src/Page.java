import java.net.URL;

/**
 * Created by Everybody's on 27/09/2016.
 */
public class Page implements Comparable<Page> {

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

    @Override
    public int compareTo(Page o) {
       if(pageRank > o.pageRank){
           return 1;
       }else if (pageRank < o.pageRank){
           return -1;
       }else{
           return 0;
       }
    }
}
