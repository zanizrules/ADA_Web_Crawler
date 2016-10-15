import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

/**
 * Vini :
 * Shane Birdsall : 14870204
 *
 * GUI Class created for displaying the search results for a given seed url and keyword.
 */
class SearchResultsGui extends JPanel implements ActionListener {
    private final static int PANEL_WIDTH = 600; // Width of panel
    private final static int PANEL_HEIGHT = 600; // Height of panel
    private JFrame frame; // Search results frame
    private JMenuItem backBtn;
    private WebCrawlerGui searchMenu; // Reference to main screen
    private Queue<Page> searchResults; // List of results

    SearchResultsGui(WebCrawlerGui menu) {
        super(new BorderLayout());
        searchMenu = menu; // reference back to menu for the back button.
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // set panel size

        // Menu bar which stores a back button
        JMenuBar menuBar = new JMenuBar();
        backBtn = new JMenuItem("<html><font size = \"5\">&#8592;</font> <font size = \"4\"> Go Back</font></html>");
        backBtn.addActionListener(this);
        menuBar.add(backBtn);

        // Get search results.
        try {
            Spider spider = new Spider();
            spider.searchInternet(searchMenu.getUrlText(), searchMenu.getKeywordText());
            searchResults = spider.orderPagesByRank();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create List Model for displaying the results in a Jlist
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Page p : searchResults) {
            model.addElement(p.toString()); // Add results to model
        }
        JList<String> results = new JList<>(model); // Initialise JList using the model which contains the results.
        JScrollPane scrollPane = new JScrollPane(results); // Make list scrollable
        add(menuBar, BorderLayout.NORTH);
        add(scrollPane);
        initialiseFrame(); // set up frame
    }

    /**
     * This method sets up the frame by creating it and assigning values and conditions as desired.
     */
    void initialiseFrame() {
        if (frame == null) { // Only initialise if not already initialised.
            frame = new JFrame("Search Results");
        }

        // Set values.
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Add panel to frame
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
        frame.setVisible(true); // Show frame
    }

    /**
     * Return to the main screen/search screen.
     */
    private void goBack() {
        frame.setVisible(false);
        searchMenu.makeVisible();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source.equals(backBtn)) { // Back button pressed.
            goBack(); // Return to previous screen.
        }
    }
}
