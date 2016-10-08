import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Shane Birdsall on 7/10/2016.
 */
public class SearchResultsGui extends JPanel implements ActionListener {
    public final static int PANEL_WIDTH = 600; // size of panel
    public final static int PANEL_HEIGHT = 600;

    private static JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem backBtn;
    private JScrollPane scrollPane;
    private JList results;
    private WebCrawlerGui searchMenu;
    private SearchResults searchResults;
    private Spider spider;

    public SearchResultsGui(WebCrawlerGui menu) {
        super(new BorderLayout());
        searchMenu = menu;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        menuBar = new JMenuBar();
        backBtn = new JMenuItem("<html>&#8592;</html>");
        backBtn.addActionListener(this);
        menuBar.add(backBtn);


        try {

            spider = new Spider(searchMenu.getUrlText());
            spider.searchInternet(searchMenu.getKeywordText());

            URL a = new URL("https://www.youtube.com/watch?v=Q0oIoR9mLwc");
            URL a2 = new URL("https://www.cool.com");
            Page b = new Page(a);
            Page b2 = new Page(a2);

//            LinkedList<Page> test = new LinkedList<>();
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);
//            test.add(b);
//            test.add(b2);


            searchResults = new SearchResults(spider.orderPagesByRank());
            // results.addListSelectionListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        results = new JList(searchResults.getListModel());
        scrollPane = new JScrollPane(results);
        scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH - 200, PANEL_HEIGHT));
        add(menuBar, BorderLayout.NORTH);
        add(scrollPane);

        initialiseFrame();
    }

    public void initialiseFrame() {
        if (frame == null) {
            frame = new JFrame("Search Results");
        }

        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);

        // gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); // resize frame appropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }

    public void goBack() {
        frame.setVisible(false);
        searchMenu.setVisible(true);
    }

//    public static void main(String[] args) {
//       // SearchResultsGui gui = new SearchResultsGui();
//        initialiseFrame(gui);
//
//        //gets the dimensions for screen width and height to calculate center
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension dimension = toolkit.getScreenSize();
//        int screenHeight = dimension.height;
//        int screenWidth = dimension.width;
//        frame.pack(); // resize frame appropriately for its content
//        //positions frame in center of screen
//        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
//                (screenHeight / 2) - (frame.getHeight() / 2)));
//        frame.setVisible(true);
//    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source.equals(backBtn)) {
            goBack();
        }
    }
}
