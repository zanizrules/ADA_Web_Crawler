import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
    private static Spider spider = new Spider();

    // Hard coded test which can be used to save time
    private void testData() {
        // Set the Web pages here
        String aut = "http://aut.ac.nz";
        String jSoup = "https://jsoup.org";
        String stackoverflow = "http://stackoverflow.com/";
        String fB = "https://www.facebook.com/";

        //Set the keyword here
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
    }

    public static void main(String[] args) {

        // Menu created for user input
        Scanner scan = new Scanner(System.in);
        boolean incorrectForm = true, incorrectNum = true;
        String inputUrl = "", Keyword;
        ArrayList<String> seeds = new ArrayList<>();
        System.out.println("Search Engine. Please answer the Following:");

        int amountOfUrl = 0;
        while (incorrectNum) {
            System.out.print("Select the amount of seed URL's you wish to use: (1-10)\n>");
            try {
                amountOfUrl = scan.nextInt();
                if (amountOfUrl < 0 || amountOfUrl > 10) {
                    throw new InputMismatchException();
                } else {
                    incorrectNum = false;
                }
            } catch (InputMismatchException e) {
                System.err.println("Please enter a number between 1 and 10");
            } finally {
                scan.nextLine();
            }
        }

        System.out.println("1.Please provide " + amountOfUrl + " seed URL's where the search will start from");

        for (int i = 0; i < amountOfUrl; i++) {
            while (incorrectForm) {
                System.out.print(">");
                inputUrl = scan.nextLine();
                try {
                    URL url = new URL(inputUrl);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    incorrectForm = false;
                } catch (MalformedURLException | IllegalArgumentException e) {
                    System.out.println("Please enter a valid URL. Example:");
                    System.out.println("http://aut.ac.nz , https://jsoup.org , http://stackoverflow.com/");
                } catch (IOException e) {
                    System.out.println("Please enter a valid URL. Example: http://aut.ac.nz");
                }
            }
            incorrectForm = true;
            seeds.add(inputUrl);
            System.out.println();
        }
        System.out.print("2.What are you searching for? (Keyword: String or single word)\n>");
        Keyword = scan.nextLine();
        System.out.println("Please wait while search is performed " +
                "(This may take some time, and timeout exceptions may be thrown)");
        try {
            spider.searchInternet(seeds, Keyword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        spider.printFromAdjList();
        System.out.println("Printing Web pages based on page rank values:");
        System.out.println(spider.printFromOrderedList());
    }
}