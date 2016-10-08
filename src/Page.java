import java.net.URL;

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
        return ("<html>&#8195;<a style=\"font-size: large;\" href=\""+url.toString()+"\">"+ url.getHost() +"</a>" +
                "<p style=\"color: green; padding: 0 0 0 20px;\">" + url.toString() + "</p>" +
                "<p style=\"color: gray; padding: 0 0 0 20px;\">" + "test description" + "</p></html>");
    }

    @Override
    public int compareTo(Page o) {
        return pageRank.compareTo(o.getPageRank());
    }
}