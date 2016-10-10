import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Shane Birdsall on 7/10/2016.
 * GUI Class created for displaying the search results for a given seed url and keyword.
 */
class SearchResultsGui extends JPanel implements ActionListener {
    private final static int PANEL_WIDTH = 600; // Width of panel
    private final static int PANEL_HEIGHT = 600; // Height of panel
    private static JFrame frame;
    private JMenuItem backBtn;
    private WebCrawlerGui searchMenu; // Reference to main screen
    private SearchResults searchResults; // List of results

    SearchResultsGui(WebCrawlerGui menu) {
        super(new BorderLayout());
        searchMenu = menu;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JMenuBar menuBar = new JMenuBar();
        backBtn = new JMenuItem("<html><font size = \"5\">&#8592;</font> <font size = \"4\"> Go Back</font></html>");
        backBtn.addActionListener(this);
        menuBar.add(backBtn);

        try {
            Spider spider = new Spider();
            spider.searchInternet(searchMenu.getUrlText(),searchMenu.getKeywordText());
            searchResults = new SearchResults(spider.orderPagesByRank());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JList results = new JList<>(searchResults.getListModel());
        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH - 200, PANEL_HEIGHT));
        add(menuBar, BorderLayout.NORTH);
        add(scrollPane);
        initialiseFrame();
    }

    void initialiseFrame() {
        if (frame == null) {
            frame = new JFrame("Search Results");
        }

        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);

        // gets the dimensions for the screen width and height to calculate the screen center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); // resize frame appropriately for its content
        // positions frame in the center of the screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }

    private void goBack() {
        frame.setVisible(false);
        searchMenu.makeVisible();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source.equals(backBtn)) {
            goBack();
        }
    }
}
