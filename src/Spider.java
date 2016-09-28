
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.net.ssl.SSLHandshakeException;

/**
 *
 * @author Everybody's
 */
public class Spider {

    private static AdjList webGraph;
    private static int numSearchLevel = 0;
    private static final int MAX_LEVEL_SEARCH = 20; // Sets how far Deep algorithm will seach
    private static int numOfPages = 0;
    private static final int MAX_PAGE_PER_SEARCH = 100;//Limits the number of pages per level
    private URL seedURL;

    public Spider(String url) throws MalformedURLException {
        seedURL = new URL(url); //stores seed Url
        webGraph = new AdjList(); // DTS
    }

    public void searchInternet(String KeyWord) throws IOException {
        breadthFirstSearch(seedURL, KeyWord); // calls method BFS using seed URL. Only by preference purpose
    }

    /**
     * BFS - searchs the internet for a keyword storing the relevant webPages. Edges are also stored.
     * Accepts  a startURL where it starts the search from. Accepts a keyWord which will be the condition
     * to add websites to the graph.
     * @param startUrl
     * @param KeyWord
     * @throws IOException
     */
    private void breadthFirstSearch(URL startUrl, String KeyWord) throws IOException {

         Page firstPage = new Page(startUrl,numSearchLevel);//first vertex stored as Page object
        // create list to hold vertices as they are encountered
        List<URL> visitedLinks = new LinkedList();
        visitedLinks.add(firstPage.getUrl());// handle the starting vertex
        //create queue to keep track of vertices not yet fully processed
        LinkedList<Page> processingQueue = new LinkedList();
        processingQueue.addLast(firstPage);// handle the starting vertex

        webGraph.addVertex(firstPage); // add first vertex to the graph
        URL currentLink = firstPage.getUrl();

        numSearchLevel = 0;// ensure this variable is 0
        // repeatedly find adjacent vertices and visit them. Stops when reachs MAX_LEVEL_SEARCH.
        while (!processingQueue.isEmpty()
                && (numSearchLevel <= MAX_LEVEL_SEARCH)) {

            Page frontLink = processingQueue.poll();//remove front element

            try {
                //get all hyperlinks that frontLink contains
                List<URL> hyperlinks = SpiderLegStatic.getHyperlink(frontLink.getUrl().toString());
                Iterator<URL> iterator = hyperlinks.iterator(); // gets iterator
                numOfPages = 0;//ensure this variable is 0
                while (iterator.hasNext() && numOfPages < MAX_PAGE_PER_SEARCH) {
                    URL nextLink = iterator.next(); // gets first element
                    currentLink = nextLink;//set current link in case of exception is thrown
                    //checj if link has been visited. if not set as visited
                    if (!visitedLinks.contains(nextLink)) {
                        visitedLinks.add(nextLink);//addd to visited
                        //seach keyword has been found
                        if(findKeyWord(nextLink,KeyWord)) {
                            Page child = new Page(nextLink,numSearchLevel+1); //creates Page object for new link
                            processingQueue.addLast(child);//add new page to the queue to be processed
                            webGraph.addVertex(child);//add new Page as a vertex
                            webGraph.addEdge(frontLink, child);//set edge, frontLink to new Page
                        }
                    }else{
                        //If link has been visit try to find it in the graph. Note: only vertex which has the
                        //keyWord is added
                        Page page = findPage(nextLink);
                        if(page != null){
                            //set link, ensure there is a link betwwen visited and already processed Page
                            webGraph.addEdge(frontLink, page);
                         }
                    }
                    numOfPages ++;//increase number of pages per level.
                }
            } catch (SSLHandshakeException e) {
                System.err.println("Exception caught for link. Access denied. " + currentLink);
            } catch (MalformedURLException e) {
                System.err.println("Protocol not valid for " + currentLink);
            } catch (IOException e) {
                System.err.println("I/O Exception in " + currentLink);
            }
            numSearchLevel++;//increase level of search
        }
    }

    /**
     * Return a existentt Page from the graph
     * @param url
     * @return
     */
    private Page findPage(URL url){
            Iterator<Page> itr = webGraph.iterator();
            while(itr.hasNext()){
                Page page = itr.next();
                if(page.getUrl() == url){
                    return page;
                }
            }
            return null;
        }

    /**
     * Find the keyWord in the metadata of given url
     * @param url
     * @param keyword
     * @return
     */
    private  boolean findKeyWord(URL url,String keyword) {
        boolean found = false;
        try{
            Elements meta = SpiderLegStatic.getMeta(url.toString());
            String link  = meta.toString();
            found =link.toLowerCase().contains(keyword.toLowerCase());
        } catch (IOException e) {
            System.err.println("I/O Exception in findKeyWord method. Timed Out. Unable to get metadata for: "+url);
        }

        return found;
    }

    /**
     * Print vertex(Pages) and thier edges
     */
    public void printFromAdjList() {
        if (webGraph.getSize() <= 1) {
            System.out.println("The keyword provided was not found in the seed Webpage!");
        } else {
            List<Page> list = webGraph.getOrderedList();
            for (Page key : list) {
                System.out.println("Vertex: " + key.getUrl());
                List<Page> listEdge = webGraph.getEdge(key);
                for (Page element : listEdge) {
                    System.out.println("    Edges: " + element.getUrl());
                }
            }
        }

    }

    /**
     * Create a adjancy Matrix from the graph
     */
    private Double[][] createAM() {
        return webGraph.createAM();
    }

    public static void main(String[] args) throws IOException {

//        Scanner scan = new Scanner(System.in);
//        boolean incorrectForm = true;
//        String inputUrl = "";
//        String Keyword = "";
//        System.out.println("Search Engine. Please asnwer the Followings:");
//        while (incorrectForm) {
//
//            System.out.print("1.Please inform a seed URL where the seach will statr from?\n>");
//            inputUrl = scan.nextLine();
//            try {
//                URL url = new URL(inputUrl);
//                URLConnection conn = url.openConnection();
//                conn.connect();
//                incorrectForm = false;
//            } catch (MalformedURLException e) {
//                System.out.println("Please enter a valid URL. Example:");
//                System.out.println("http://aut.ac.nz , https://jsoup.org , http://stackoverflow.com/");
//            } catch (IOException e) {
//                System.out.println("Please enter a valid URL. Example: http://aut.ac.nz");
//            }
//        }
//        System.out.print("2.What are we seaching?(String or single word)\n>");
//        Keyword = scan.nextLine();
//        System.out.println("Please wait while search is performed");
//        Spider spider = new Spider(inputUrl);
//        spider.searchInternet(Keyword);
//        spider.printFromAdjList();

//        Properties props = new Properties(System.getProperties());
//        props.put("http.proxySet", "true"); // true if using proxy
//        props.put("http.proxyHost", "cache.aut.ac.nz"); // AUT specific
//        props.put("http.proxyPort", "3128"); // AUT specific
//        System.setProperties(props);

        String aut = "http://aut.ac.nz";
        String jsoup = "https://jsoup.org";
        Spider spider = new Spider(aut);
        spider.searchInternet("aut");
        spider.printFromAdjList();


    }

}
