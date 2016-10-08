
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.net.ssl.SSLHandshakeException;

/**
 * TODO: Refactoring, commenting -> Will get Vini to explain, and I translate anything not obvious into comments.
 * @author Everybody's
 */
public class Spider {

    private static Graph<Page> webGraph;
    private static final int MAX_LEVEL_SEARCH = 10; // Sets how far Deep algorithm will search
    private static final int MAX_PAGE_PER_SEARCH = 50;//Limits the number of pages per level

    Spider(){
        webGraph = new Graph<>(); // DTS
    }

    /**
     * Search each seed link for the keyword
     * @param list
     * @param KeyWord
     * @throws IOException
     */
    public void searchInternet(Collection<String> list,String KeyWord) throws IOException{
        for (String str : list) {
           URL seedURL = new URL(str);

//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        webCrawler(seedURL, KeyWord); // calls method BFS using seed URL.
//                    } catch (IOException e) {
//                        System.err.println("IOException when creating Thread for link: "+seedURL.toString());
//                    }
//                }
//            });
            webCrawler(seedURL, KeyWord); // calls method BFS using seed URL.

        }

    }

    /**
     * Search the seed link for a key word
     * @param seed
     * @param KeyWord
     * @throws IOException
     */
    public void searchInternet(String seed, String KeyWord) throws IOException {
       URL  seedURL = new URL(seed);
       webCrawler(seedURL, KeyWord); // calls method BFS using seed URL.
    }

    /**
     * BFS - searchs the internet for a keyword storing the relevant webPages. Edges are also stored.
     * Accepts  a startURL where it starts the search from. Accepts a keyWord which will be the condition
     * to add websites to the graph.
     * @param startUrl starting URL
     * @param KeyWord keyword to search
     * @throws IOException Input/output exception.
     */
    private void webCrawler(URL startUrl, String KeyWord) throws IOException {

        int numSearchLevel = 0;// ensure this variable is 0

         Page firstPage = new Page(startUrl,numSearchLevel);//first vertex stored as Page object
        // create list to hold vertices as they are encountered
        List<URL> visitedLinks = new LinkedList<>();
        visitedLinks.add(firstPage.getUrl());// handle the starting vertex
        //create queue to keep track of vertices not yet fully processed
        LinkedList<Page> processingQueue = new LinkedList<>();
        processingQueue.addLast(firstPage);// handle the starting vertex

        webGraph.addVertex(firstPage); // add first vertex to the graph
        URL currentLink = firstPage.getUrl();

        // repeatedly find adjacent vertices and visit them. Stops when reachs MAX_LEVEL_SEARCH.
        while (!processingQueue.isEmpty()
                && (numSearchLevel <= MAX_LEVEL_SEARCH)) {

            Page frontLink = processingQueue.poll();//remove front element

            try {
                //get all hyperlinks that frontLink contains
                List<URL> hyperlinks = SpiderLeg.getHyperlink(frontLink.getUrl().toString());
                Iterator<URL> iterator = hyperlinks.iterator(); // gets iterator
                int numOfPages = 0;
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
                    numOfPages++;//increase number of pages per level.
                }
            } catch (SSLHandshakeException e) {
                System.err.println("Exception caught at webCrawler for link. Access denied. " + currentLink);
            } catch (MalformedURLException e) {
                System.err.println("At webCrawler caught Protocol not valid for " + currentLink);
            } catch (IOException e) {
                System.err.println("At webCrawler caught I/O Exception in " + currentLink);
            }
            numSearchLevel++;//increase level of search
        }
        //Save list to a file
        printToFile(webGraph.getOrderedList(),"unOrderedListOfLinks");
    }

    /**
     * Return a existent Page from the graph
     * @param url Page URL
     * @return Page
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
     */
    private  boolean findKeyWord(URL url,String keyword) {
        boolean found = false;
        try{
            Elements meta = SpiderLeg.getMeta(url.toString());
            String link  = meta.toString();
            found =link.toLowerCase().contains(keyword.toLowerCase());
        } catch (IOException e) {
            System.err.println("I/O Exception in findKeyWord method. Client connection timed Out. Unable to get metadata for: "+url);
        }

        return found;
    }

    /**
     * Class will print a list of Pages' URL to a file of certain given name
     * @param list
     * @param filename
     */
    private void printToFile( Collection<Page>list,String filename){
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileOutputStream(filename+".txt"));
            for (Page page : list ) {
                outputStream.println(page.getUrl().toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("printToFile method failed. Unable to create File");
        }
        outputStream.close();
    }

    /**
     * Create a adjancy Matrix from the graph. Calculates the Pagerank of each page.
     * Set the PageRank for the correct page. Sort Pages by page rank by adding into a PriorityQueue.
     */
    public Queue<Page> orderPagesByRank() {
        Double[][] pageRank = PageRank.pageRank(webGraph.createAdjacencyMatrix(),0.15);
        List<Page> list = webGraph.getOrderedList();
        Comparator<Page> comparator = (o1, o2) -> {
            if(o1.getPageRank() > o2.getPageRank()){
                return -1;
            }
            if (o1.getPageRank() < o2.getPageRank()){
                return 1;
            }
            return 0;

        };
        PriorityQueue<Page> orderedQueue = new PriorityQueue<>(list.size(),comparator);
        int counter = 0;
        for (Page page: list ) {
            page.setPageRank(pageRank[counter][0]);
            orderedQueue.add(page);
            counter++;
        }
        //Save list to a file
        printToFile(orderedQueue,"orderedList");
        return orderedQueue;
    }

    /**
     * Print vertex(Pages) and their edges. Return string
     * Not use for the GUI only CUI
     */
    public String printFromAdjList() {
        String str = "";
        if (webGraph.getSize() <= 1) {
            return "The keyword provided was not found in the seed Webpage!";
        } else {
            List<Page> list = webGraph.getOrderedList();
            for (Page key : list) {
                str +="Vertex found at level "+key.getSearchLevel()+": " + key.getUrl()+"\n";
                List<Page> listEdge = webGraph.getEdge(key);
                for (Page element : listEdge) {
                    str +="    Edges found at level "+key.getSearchLevel()+": " + element.getUrl()+"\n";
                }
            }
        }
        return str;
    }

    /**
     *  Print Pages from the orderQueue by page Rank factor
     *  Not use for the GUI only CUI
     */
    public String printFromOrderedList(){
        Queue<Page> pages = this.orderPagesByRank();
        String str = "";

        while(!pages.isEmpty()){
            Page page = pages.poll();
            str += page.getUrl()+"\nPageRank value: "+page.getPageRank()+"\n";
        }
        return str;
    }

}
