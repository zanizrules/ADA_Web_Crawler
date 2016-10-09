import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * GUI Class created for Web Crawler. This class is the main screen of the application and allows the user to input
 * a seed URL, and a keyword and then search for a list of relating web pages.
 */
public class WebCrawlerGui extends JPanel implements ActionListener {
    private final static int PANEL_WIDTH = 450; // size of panel
    private final static int PANEL_HEIGHT = 450;
    private final static Random rand = new Random();

    private static JFrame frame; // Main screen frame
    // Labels
    private static final JLabel titleLabel = new JLabel("Vini & Shane Web Crawler"),
            inputUrlLabel = new JLabel("Please enter a URL below:"),
            inputKeywordLabel = new JLabel("Please enter a search term below:");
    // Text Areas
    private static JTextArea urlText, keywordText;
    // Buttons
    private JButton searchButton, randomInputButton;
    // reference to results screen
    private SearchResultsGui searchResultsScreen;

    /**
     * Returns the text present in the urlText Text Area.
     */
    String getUrlText() {
        return urlText.getText();
    }

    /**
     * Returns the text present in the keywordText Text Area.
     */
    String getKeywordText() {
        return keywordText.getText();
    }

    private WebCrawlerGui() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); // ser panel size

        // Set up text areas
        urlText = new JTextArea();
        urlText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        urlText.setBorder(BorderFactory.createEtchedBorder(1));
        keywordText = new JTextArea();
        keywordText.setPreferredSize(new Dimension(PANEL_WIDTH - 30, 22));
        keywordText.setBorder(BorderFactory.createEtchedBorder(1));

        // Set up Title Label
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getName(), Font.BOLD, 28));

        // Add image
        try {
            BufferedImage img = ImageIO.read(new File("src/webImg.png"));
            JLabel picLabel = new JLabel(new ImageIcon(img));
            add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up buttons
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        randomInputButton = new JButton("Generate Random Search");
        randomInputButton.addActionListener(this);

        add(titleLabel, BorderLayout.NORTH);

        // Set up buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 170));
        buttonPanel.add(randomInputButton);
        buttonPanel.add(inputUrlLabel);
        buttonPanel.add(urlText);
        buttonPanel.add(inputKeywordLabel);
        buttonPanel.add(keywordText);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the frame.
     */
    private static void initialiseFrame(WebCrawlerGui i) {
        if (frame == null) { // initialise frame only if not already initialised.
            frame = new JFrame("Web Crawler Gui");
            frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
            frame.setFocusable(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.getContentPane().add(i);
        }
    }

    void makeVisible() {
        frame.setVisible(true); // Show frame
    }

    public static void main(String[] args) {
        WebCrawlerGui gui = new WebCrawlerGui();
        initialiseFrame(gui); // Start application

        // gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack(); // resize frame appropriately for its content
        // positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source.equals(searchButton)) { // User has clicked search button.
            if (keywordText != null && urlText != null) {
                if (urlText.getText().trim().isEmpty()) { // No url
                    // Tell user they have not given a url
                    JOptionPane.showMessageDialog(this, "Please enter a URL", "Error Occurred", JOptionPane.WARNING_MESSAGE);
                } else if (keywordText.getText().trim().isEmpty()) { // no search term (keyword)
                    // Tell user they have not given a search term
                    JOptionPane.showMessageDialog(this, "Please enter a Search Term", "Error Occurred", JOptionPane.WARNING_MESSAGE);
                } else { // Search term and url provided
                    // Show wait message
                    JOptionPane.showMessageDialog(this, "Please wait while your search is processed", "Searching...", JOptionPane.INFORMATION_MESSAGE);

                    if (searchResultsScreen == null) { // initialise search screen
                        searchResultsScreen = new SearchResultsGui(this);
                    }
                    searchResultsScreen.initialiseFrame(); // initialise then show search screen frame
                    frame.setVisible(false);
                }
            }
        } else if (source.equals(randomInputButton)) { // Set url and keyword to one of three test cases
            int i = rand.nextInt(100);
            if (i < 33) {
                urlText.setText("http://aut.ac.nz");
                keywordText.setText("Students");
            } else if (i < 66) {
                urlText.setText("https://www.facebook.com/");
                keywordText.setText("Like");
            } else {
                urlText.setText("http://stackoverflow.com/");
                keywordText.setText("Java");
            }
        }
    }
}