import java.io.IOException;
import java.util.ArrayList;

/**
 * Vinicius Ferreira : 14868388
 * Shane Birdsall : 14870204
 *
 * WebCrawlerCui implements a main class for the Cui version of the WebCrawler.
 * This class has been hard coded to facilitate testing. The current set up will search a keyWord from different seeds.
 * Searching from different seeds are not available in the Gui version.
 * Menu for user input has been commented out due to Gui implementation providing better UI.
 */
public class WebCrawlerCui {

    public static void main(String[] args) {
        Spider spider = new Spider();

        // Set the Web pages here
        String aut = "http://aut.ac.nz";
        String jSoup = "https://jsoup.org";
        String stackoverflow = "http://stackoverflow.com/";
        String fB = "https://www.facebook.com/";

        //Please set the keyword here
        String KeyWord = "Java";

        // Creates a list of seed Links
        ArrayList<String> links = new ArrayList<>();
        links.add(aut);
        links.add(jSoup);
        links.add(stackoverflow);
        links.add(fB);

        // Test multiple seeds, perform search and print result
        try {
            spider.searchInternet(links, KeyWord);
            System.out.println(spider.printFromAdjList());
            System.out.println("\nPrinting Web pages based on page rank values");
            System.out.println(spider.printFromOrderedList());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Menu created for user input, but commented out due to GUI implementation
//        Scanner scan = new Scanner(System.in);
//        boolean incorrectForm = true;
//        String inputUrl = "";
//        String Keyword = "";
//        System.out.println("Search Engine. Please answer the Followings:");
//        while (incorrectForm) {
//
//            System.out.print("1.Please inform a seed URL where the search will start from?\n>");
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
//        System.out.print("2.What are we searching?(String or single word)\n>");
//        Keyword = scan.nextLine();
//        System.out.println("Please wait while search is performed");
//        spider.searchInternet(inputUrl,Keyword);
//        spider.printFromAdjList();
//        System.out.println("Printing Webpages based on its page rank value");
//        System.out.println(spider.printFromOrderedList());
    }
}