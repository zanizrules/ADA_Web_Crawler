
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;

/**
 *
 * @author Everybody's
 */
public class Spider {

    private static AdjList webGraph;
    private static int numSearchLevel = 0;
    private static final int MAX_LEVEL_SEARCH = 5;
    private URL startUrl;

    public Spider(String url) throws MalformedURLException {
        startUrl = new URL(url);
        webGraph = new AdjList();
    }

    public void searchInternet(String KeyWord) throws IOException {
        breadthFirstSearch(startUrl, KeyWord);
    }

    public void breadthFirstSearch(URL startUrl, String KeyWord) throws IOException {
        numSearchLevel = 0;
        // create list to hold vertices as they are encountered
        List<URL> visitedLinks = new ArrayList();
        //create queue to keep track of vertices not yet fully processed
        ArrayDeque<Page> processingQueue = new ArrayDeque();

        Page firstPage = new Page(startUrl,numSearchLevel);

        visitedLinks.add(startUrl);// handle the starting vertex
        processingQueue.addLast(firstPage);// handle the starting vertex
        webGraph.addVertex(firstPage);
        URL nextLink = startUrl; // USED IN THE LOOP, SUPPORTS EXCEPTIONS
       
        // repeatedly find adjacent vertices and visit them
        while (!processingQueue.isEmpty()
                && (numSearchLevel <= MAX_LEVEL_SEARCH)) {
            //remove front element
            Page frontLink = processingQueue.pollFirst();
            // find all the adjacent vertices that have not been visited and enqueue them
            try {
                List hyperlinks = SpiderLegStatic.getHyperlink(frontLink.getUrl().toString());
                Iterator<URL> iterator = hyperlinks.iterator();
                int counter = 0;
                while (iterator.hasNext() && counter < 10) {
                    counter ++; // testing purpose
                    nextLink = iterator.next();
                    boolean visited = visitedLinks.contains(nextLink);
                    if (!visited ) {
                        Page child = new Page(nextLink,numSearchLevel+1);
                        visitedLinks.add(nextLink);
                        processingQueue.addLast(child);
                        webGraph.addVertex(child);
                        webGraph.addEdge(frontLink, child);
//                        System.out.println("Level of search is "+(numSearchLevel+1)+" :"+ nextLink);
                    }
                    if (visited) {
                        Page page = findPage(nextLink);
                        if(page != null) {
                            webGraph.addEdge(frontLink, page);
                        }
                    }
                }
            } catch (SSLHandshakeException e) {
                System.err.println("Exception cought for link. Access denied. " + nextLink);
            } catch (MalformedURLException e) {
                System.out.println("Protocol not valid for " + nextLink);
            } catch (IOException e) {
                System.out.println("I/O Exception in " + nextLink);
            }
            numSearchLevel++;
        }
    }

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

    public boolean findKeyWord(URL url,String keyword) throws IOException {

        Elements meta = SpiderLegStatic.getMeta(url.toString());
        for (Element aMeta : meta) {
            String link = aMeta.toString();
            if (link.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

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

    public void createAM() {
        MatrixMain.printMatrix(webGraph.createAM(), true);
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

        String aut = "http://aut.ac.nz";
        String jsoup = "https://jsoup.org";
        Spider spider = new Spider(aut);
        spider.searchInternet("aut");
        spider.printFromAdjList();
        spider.createAM();

//        System.out.println(SpiderLegStatic.getMeta(aut));
//
//        System.out.println(spider.findKeyWord(new URL(aut),"aut"));

        System.out.println();

    }

}
